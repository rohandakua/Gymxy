package com.gymxy.gymxyone.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.provider.Settings
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.gymxy.gymxyone.data.offline.SharedPreferenceDataHandler
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.gymxy.gymxyone.domain.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class GoogleAuthClient @Inject constructor(
    private var context: Context,
    private var googleIdOption: GetGoogleIdOption,
    private var sharedPreferenceDataHandler: SharedPreferenceDataHandler
) {
    private val TAG = "GoogleAuthClient"
    private var auth = Firebase.auth
    val credentialManager = androidx.credentials.CredentialManager.create(context)

    suspend fun signIn(coroutineScope: CoroutineScope) : Result{
        return withContext(Dispatchers.IO){
            if (sharedPreferenceDataHandler.getUid() == null) {
                val request = androidx.credentials.GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption).build()
                try {
                    val result =
                        credentialManager.getCredential(request = request, context = context)
                    handleSignIn(result)
                } catch (e: NoCredentialException) {
                    // use the getIntent() method to create the intent and launch it
                    context.startService(getIntent())
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

    private suspend fun handleSignIn(result: GetCredentialResponse): Result {
        return withContext(Dispatchers.IO){
            // Handle the successfully returned credential.
            when (val credential = result.credential) {
                // GoogleIdToken credential
                is CustomCredential -> {
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
                                    // call LoginUtil.login()
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