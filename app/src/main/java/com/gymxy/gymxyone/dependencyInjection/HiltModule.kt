package com.gymxy.gymxyone.dependencyInjection

import android.content.Context
import android.content.SharedPreferences
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.gymxy.gymxyone.Secrets.WEB_CLIENT_ID
import com.gymxy.gymxyone.data.offline.SharedPreferenceDataHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    fun provideContext () : ApplicationContext {
        return ApplicationContext()
    }

    @Provides
    fun providesGoogleIdOption (): GetGoogleIdOption {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(WEB_CLIENT_ID)
            .setAutoSelectEnabled(true)
            .build()
        return googleIdOption
    }

    @Provides
    fun providesSignInWithGoogleOption (): GetSignInWithGoogleOption {
        val signInWithGoogleOption = GetSignInWithGoogleOption.Builder(serverClientId = WEB_CLIENT_ID).build()
        return signInWithGoogleOption
    }

    @Provides
    @Singleton
    fun provideFirebase() : FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ) : SharedPreferences {
        return context.getSharedPreferences("User",Context.MODE_PRIVATE)
    }
    @Provides
    @Singleton
    fun provideSharedPrefenceDataHandler (
        sharedPreferences: SharedPreferences
    ): SharedPreferenceDataHandler {
        return SharedPreferenceDataHandler(sharedPreferences)
    }

}