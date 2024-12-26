package com.gymxy.gymxyone.data.online

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import com.gymxy.gymxyone.data.offline.SharedPreferenceDataHandler
import com.gymxy.gymxyone.domain.models.EachExercisePerformedDetails
import com.gymxy.gymxyone.domain.models.Height
import com.gymxy.gymxyone.domain.models.PerformedDays
import com.gymxy.gymxyone.domain.models.Result
import com.gymxy.gymxyone.domain.models.SplitDetails
import com.gymxy.gymxyone.domain.models.User
import com.gymxy.gymxyone.domain.models.Weight
import com.gymxy.gymxyone.domain.repositoryInterface.FirestoreDataHandlingInterface
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirestoreDataHandler @Inject constructor(
    private val db: FirebaseFirestore,
    private val sharedPreferenceDataHandler: SharedPreferenceDataHandler
) : FirestoreDataHandlingInterface {
    private val TAG = "FirestoreDataHandler"

    override suspend fun addPerformedDays(
        performedDays: PerformedDays,
        exerciseDetails: Map<Int, EachExercisePerformedDetails>
    ): Result {
        return withContext(Dispatchers.IO) {
            try {
                val uid = sharedPreferenceDataHandler.getUid()
                if (uid == null) {
                    Log.e(TAG, "User ID is null")
                    Result.FAILURE
                }
                performedDays.exerciseDetails = exerciseDetails
                db.collection(RepositoryCollectionsName.FIREBASE_COLLECTION_BASE_ADDRESS)
                    .document(uid!!)
                    .collection(RepositoryCollectionsName.FIREBASE_COLLECTION_PERFORMED_DAYS)
                    .document(performedDays.startTime.toString())
                    .set(performedDays)
                    .await()
                Result.SUCCESS
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
                Result.FAILURE
            }
        }
    }


    /**
     * deletePerformedDays is used to set toShowThisInFeed data field in the performedDays collection to false
     * this is used to make the performedDays data not show in the feed , we are not deleting the data as it is costly
     */
    override suspend fun deletePerformedDays(performedDays: PerformedDays): Result {
        return withContext(Dispatchers.IO){
            try{
                val uid = sharedPreferenceDataHandler.getUid()
                if (uid == null) {
                    Log.e(TAG, "User ID is null")
                    Result.FAILURE
                }
                db.collection(RepositoryCollectionsName.FIREBASE_COLLECTION_BASE_ADDRESS)
                    .document(uid!!)
                    .collection(RepositoryCollectionsName.FIREBASE_COLLECTION_PERFORMED_DAYS)
                    .document(performedDays.startTime.toString())
                    .update("toShowThisInFeed",false)
                    .await()
                Result.SUCCESS
            }catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
                Result.FAILURE
            }
        }
    }

    override suspend fun addWeight(weightDetails: Weight): Result {
        return withContext(Dispatchers.IO){
            try{
                val uid = sharedPreferenceDataHandler.getUid()
                if (uid == null) {
                    Log.e(TAG, "User ID is null")
                    Result.FAILURE
                }
                db.collection(RepositoryCollectionsName.FIREBASE_COLLECTION_BASE_ADDRESS)
                    .document(uid!!)
                    .collection(RepositoryCollectionsName.FIREBASE_COLLECTION_WEIGHT_DETAILS)
                    .document(weightDetails.dateInEpoch.toString())
                    .set(weightDetails)
                    .await()
                Result.SUCCESS
            }catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
                Result.FAILURE
            }
        }
    }

    override suspend fun addHeight(heightDetails: Height): Result {
        return withContext(Dispatchers.IO){
            try{
                val uid = sharedPreferenceDataHandler.getUid()
                if (uid == null) {
                    Log.e(TAG, "User ID is null")
                    Result.FAILURE
                }
                db.collection(RepositoryCollectionsName.FIREBASE_COLLECTION_BASE_ADDRESS)
                    .document(uid!!)
                    .collection(RepositoryCollectionsName.FIREBASE_COLLECTION_HEIGHT_DETAILS)
                    .document(heightDetails.dateInEpoch.toString())
                    .set(heightDetails)
                    .await()
                Result.SUCCESS
            }catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
                Result.FAILURE
            }
        }
    }

    /**
     * @fun addSplit
     * it is used to add a split in the splitDetails collection
     * it first gets the noOfCustomSplit from the user details from firestore
     * then it sets the noOfCustomSplit in firestore
     * if it is ^ failure then failure will return
     */
    override suspend fun addSplit(splitDetails: SplitDetails): Result {
        return withContext(Dispatchers.IO){
            try{
                val uid = sharedPreferenceDataHandler.getUid()
                if (uid == null) {
                    Log.e(TAG, "User ID is null")
                    Result.FAILURE
                }
                val noOfCustomSplit = getNoOfCustomSplit()
                if(setNoOfCustomSplit(noOfCustomSplit!!+1)==Result.FAILURE){
                    Result.FAILURE
                }
                splitDetails.splitId = uid.toString() + noOfCustomSplit.toString()
                db.collection(RepositoryCollectionsName.FIREBASE_COLLECTION_BASE_ADDRESS)
                    .document(uid!!)
                    .collection(RepositoryCollectionsName.FIREBASE_COLLECTION_SPLIT_DETAILS)
                    .document(splitDetails.splitId)
                    .set(splitDetails)
                    .await()
                Result.SUCCESS
            }catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
                Result.FAILURE
            }
        }
    }

    /**
     * @fun getNoOfCustomSplit
     * this is getting realtime updates , if the device is offline then also this will work
     */
    override suspend fun getNoOfCustomSplit(): Int? {
        return withContext(Dispatchers.IO){
            try{
                val uid = sharedPreferenceDataHandler.getUid()
                if (uid == null) {
                    Log.e(TAG, "User ID is null")
                    throw Exception("User ID is null")
                }
                val docRef = db.collection(RepositoryCollectionsName.FIREBASE_COLLECTION_BASE_ADDRESS)
                    .document(uid)
                docRef.addSnapshotListener{ snapshot ,e ->
                    if(e != null) {
                        Log.w(TAG, "listen failed", e)
                    }
                    if(snapshot != null && snapshot.exists()){
                        val tempObj = snapshot.toObject<User>()
                        tempObj!!.noOfCustomSplit
                    }
                }
            }catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
                throw e
            }
            null
        }
    }

    override suspend fun setNoOfCustomSplit(valueToSet: Int): Result {
        return withContext(Dispatchers.IO){
            try{
                val uid = sharedPreferenceDataHandler.getUid()
                if (uid == null) {
                    Log.e(TAG, "User ID is null")
                    Result.FAILURE
                }
                db.collection(RepositoryCollectionsName.FIREBASE_COLLECTION_BASE_ADDRESS)
                    .document(uid!!)
                    .update("noOfCustomSplit",valueToSet)
                    .await()
                Result.SUCCESS
            }catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
                Result.FAILURE
            }
        }
    }

    override suspend fun getWeightDetails(): List<Weight>? {
        return withContext(Dispatchers.IO){
            try{
                val uid = sharedPreferenceDataHandler.getUid()
                if (uid == null) {
                    Log.e(TAG, "User ID is null")
                    throw Exception("User ID is null")
                }
                val docRef = db.collection(RepositoryCollectionsName.FIREBASE_COLLECTION_BASE_ADDRESS)
                    .document(uid).collection(RepositoryCollectionsName.FIREBASE_COLLECTION_WEIGHT_DETAILS)
                .orderBy("dateInEpoch", Query.Direction.DESCENDING)
                val documents = docRef.get().await()
                val list = mutableListOf<Weight>()
                for(document in documents){
                    list.add(document.toObject<Weight>())
                }
                list.toList()
            }catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
                throw e
            }
            null
        }
    }

    override suspend fun getHeightDetails(): List<Height>? {
        return withContext(Dispatchers.IO){
            try{
                val uid = sharedPreferenceDataHandler.getUid()
                if (uid == null) {
                    Log.e(TAG, "User ID is null")
                    throw Exception("User ID is null")
                }
                val docRef = db.collection(RepositoryCollectionsName.FIREBASE_COLLECTION_BASE_ADDRESS)
                    .document(uid).collection(RepositoryCollectionsName.FIREBASE_COLLECTION_HEIGHT_DETAILS)
                    .orderBy("dateInEpoch", Query.Direction.DESCENDING)
                val documents = docRef.get().await()
                val list = mutableListOf<Height>()
                for(document in documents){
                    list.add(document.toObject<Height>())
                }
                list.toList()
            }catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
                throw e
            }
            null
        }
    }

    override suspend fun getSplitDetails(): List<SplitDetails>? {
        return withContext(Dispatchers.IO){
            try{
                val uid = sharedPreferenceDataHandler.getUid()
                if (uid == null) {
                    Log.e(TAG, "User ID is null")
                    throw Exception("User ID is null")
                }
                val docRef = db.collection(RepositoryCollectionsName.FIREBASE_COLLECTION_BASE_ADDRESS)
                    .document(uid).collection(RepositoryCollectionsName.FIREBASE_COLLECTION_SPLIT_DETAILS)
                val documents = docRef.get().await()
                val list = mutableListOf<SplitDetails>()
                for(document in documents){
                    list.add(document.toObject<SplitDetails>())
                }
                list.toList()
            }catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
                throw e
            }
            null
        }
    }

    override suspend fun getPerformedDays(): List<PerformedDays>? {
        return withContext(Dispatchers.IO){
            try{
                val uid = sharedPreferenceDataHandler.getUid()
                if (uid == null) {
                    Log.e(TAG, "User ID is null")
                    throw Exception("User ID is null")
                }
                val docRef = db.collection(RepositoryCollectionsName.FIREBASE_COLLECTION_BASE_ADDRESS)
                    .document(uid).collection(RepositoryCollectionsName.FIREBASE_COLLECTION_PERFORMED_DAYS)
                    .orderBy("startTime", Query.Direction.DESCENDING)
                val documents = docRef.get().await()
                val list = mutableListOf<PerformedDays>()
                for(document in documents){
                    list.add(document.toObject<PerformedDays>())
                }
                list.toList()
            }catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
                throw e
            }
            null
        }
    }

    override suspend fun getSplitById(splitId: String): SplitDetails? {
        return withContext(Dispatchers.IO){
            try{
                val uid = sharedPreferenceDataHandler.getUid()
                if (uid == null) {
                    Log.e(TAG, "User ID is null")
                    throw Exception("User ID is null")
                }
                val docRef = db.collection(RepositoryCollectionsName.FIREBASE_COLLECTION_BASE_ADDRESS)
                    .document(uid).collection(RepositoryCollectionsName.FIREBASE_COLLECTION_SPLIT_DETAILS)
                    .document(splitId)
                val documents = docRef.get().await()
                documents.toObject<SplitDetails>()
            }catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
                throw e
            }
            null
        }
    }



}