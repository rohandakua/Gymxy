package com.gymxy.gymxyone.data.online

import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.gymxy.gymxyone.Secrets
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirestoreDataHandler @Inject constructor(
    private val db: FirebaseFirestore,
    private val sharedPreferenceDataHandler: SharedPreferenceDataHandler
) : FirestoreDataHandlingInterface {
    val TAG = "FirestoreDataHandler"

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
        TODO("Not yet implemented")
    }

    override suspend fun addHeight(heightDetails: Height): Result {
        TODO("Not yet implemented")
    }

    override suspend fun addSplit(splitDetails: SplitDetails): Result {
        TODO("Not yet implemented")
    }

    override suspend fun getNoOfCustomSplit(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun setNoOfCustomSplit(valueToSet: Int): Result {
        TODO("Not yet implemented")
    }

    override suspend fun getWeightDetails(): List<Weight> {
        TODO("Not yet implemented")
    }

    override suspend fun getHeightDetails(): List<Height> {
        TODO("Not yet implemented")
    }

    override suspend fun getSplitDetails(): List<SplitDetails> {
        TODO("Not yet implemented")
    }

    override suspend fun getPerformedDays(): List<PerformedDays> {
        TODO("Not yet implemented")
    }

    override suspend fun getPerformedDaysExercise(): List<EachExercisePerformedDetails> {
        TODO("Not yet implemented")
    }


}