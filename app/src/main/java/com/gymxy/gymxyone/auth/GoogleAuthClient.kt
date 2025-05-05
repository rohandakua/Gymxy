package com.gymxy.gymxyone.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.provider.Settings
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.NoCredentialException
import com.dotlottie.dlplayer.Event
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.gymxy.gymxyone.Secrets.WEB_CLIENT_ID
import com.gymxy.gymxyone.data.offline.SharedPreferenceDataHandler
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.gymxy.gymxyone.domain.models.Result
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class GoogleAuthClient @Inject constructor(
    private var context: Context,
    private var googleIdOption: GetGoogleIdOption,
    private var sharedPreferenceDataHandler: SharedPreferenceDataHandler
) {
    private val TAG = "GoogleAuthClient"
    private var auth = Firebase.auth
    val credentialManager = androidx.credentials.CredentialManager.create(context)

    suspend fun signIn(activityContext: Context,activity: Activity) : Result{
        return withContext(Dispatchers.Main){
            Log.d(TAG,"reached in signIn ")
            if (sharedPreferenceDataHandler.getUid() == null) {
                val request : GetCredentialRequest = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption).build()
                try {
                    Log.d(TAG,"reached in signIn try block")
                        val result = credentialManager.getCredential(request = request, context = activity)

                    return@withContext handleSignIn(result)

                } catch (e: NoCredentialException) {
                    // use the getIntent() method to create the intent and launch it
                    Log.d(TAG,"reached in signIn NoCredentialException")
                    activityContext.startActivity(getIntent())
                    Result.FAILURE
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    Log.e(TAG,e.message.toString())
                    Result.FAILURE
                }

            } else if (auth.currentUser?.uid.toString() == sharedPreferenceDataHandler.getUid()
            ) {
                Result.SUCCESS
            } else {
                Result.FAILURE
            }
        }
    }

    private fun getIntent(): Intent {
        return Intent(Settings.ACTION_ADD_ACCOUNT).apply {
            putExtra(Settings.EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
        }
    }

    fun isSignedIn(): Boolean {
        return (sharedPreferenceDataHandler.getUid()!=null)
    }
    fun getUid(): String? {
        return sharedPreferenceDataHandler.getUid()
    }

    private suspend fun handleSignIn(result: GetCredentialResponse): Result {
        return withContext(Dispatchers.Main){
            // Handle the successfully returned credential.
            when (val credential = result.credential) {
                // GoogleIdToken credential
                is CustomCredential -> {
                    Log.d(TAG,credential.toString())
                    if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        try {
                            val googleIdTokenCredential = GoogleIdTokenCredential
                                .createFrom(credential.data)
                            val authCredential =
                                GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
                            val user = Firebase.auth.signInWithCredential(authCredential).await().user
                            user?.let {
                                if (it.isAnonymous.not()) {
                                    sharedPreferenceDataHandler.saveUid(it.uid)
                                    sharedPreferenceDataHandler.saveName(it.displayName.toString())
                                    sharedPreferenceDataHandler.savePhoto(it.photoUrl.toString())
                                    Result.SUCCESS
                                }
                            }
                        } catch (e: GoogleIdTokenParsingException) {
                            Log.e(TAG, "Received an invalid google id token response", e)
                        }

                    }
                }

            }
            Result.FAILURE
        }
    }


    suspend fun signOut() {
        withContext(Dispatchers.Default) {
            credentialManager.clearCredentialState(request = ClearCredentialStateRequest())
            auth.signOut()
            sharedPreferenceDataHandler.clearData()
        }
    }
}