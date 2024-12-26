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
import com.gymxy.gymxyone.data.online.FirestoreDataHandler
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.DeletePerformedDay
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.GetHeightDetails
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.GetPerformedDays
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.GetSplitDetails
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.GetWeightDetails
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.SaveHeight
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.SavePerformedDay
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.SaveSplit
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.SaveWeight
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.gymxy.gymxyone.domain.repositoryInterface.FirestoreDataHandlingInterface as FirestoreDataHandlingInterface

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    fun provideContext(): ApplicationContext {
        return ApplicationContext()
    }

    @Provides
    fun providesGoogleIdOption(): GetGoogleIdOption {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(WEB_CLIENT_ID)
            .setAutoSelectEnabled(true)
            .build()
        return googleIdOption
    }

    @Provides
    fun providesSignInWithGoogleOption(): GetSignInWithGoogleOption {
        val signInWithGoogleOption =
            GetSignInWithGoogleOption.Builder(serverClientId = WEB_CLIENT_ID).build()
        return signInWithGoogleOption
    }

    @Provides
    @Singleton
    fun provideFirebase(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences("User", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSharedPrefenceDataHandler(
        sharedPreferences: SharedPreferences
    ): SharedPreferenceDataHandler {
        return SharedPreferenceDataHandler(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideFireBaseDataHandler(
        db: FirebaseFirestore,
        sharedPreferenceDataHandler: SharedPreferenceDataHandler
    ): FirestoreDataHandlingInterface {
        return FirestoreDataHandler(
            db, sharedPreferenceDataHandler
        )
    }

    @Provides
    @Singleton
    fun provideSaveHeight(
        firestoreDataHandlingInterface: FirestoreDataHandlingInterface
    ) : SaveHeight {
        return SaveHeight(firestoreDataHandlingInterface)
    }
    @Provides
    @Singleton
    fun provideSavePerformedDay(
        firestoreDataHandlingInterface: FirestoreDataHandlingInterface
    ) : SavePerformedDay {
        return SavePerformedDay(firestoreDataHandlingInterface)
    }
    @Provides
    @Singleton
    fun provideSaveSplit(
        firestoreDataHandlingInterface: FirestoreDataHandlingInterface
    ) : SaveSplit {
        return SaveSplit(firestoreDataHandlingInterface)
    }
    @Provides
    @Singleton
    fun provideSaveWeight(
        firestoreDataHandlingInterface: FirestoreDataHandlingInterface
    ) : SaveWeight {
        return SaveWeight(firestoreDataHandlingInterface)
    }
    @Provides
    @Singleton
    fun provideGetHeightDetails(
        firestoreDataHandlingInterface: FirestoreDataHandlingInterface
    ) : GetHeightDetails {
        return GetHeightDetails(firestoreDataHandlingInterface)
    }
    @Provides
    @Singleton
    fun provideGetWeightDetails(
        firestoreDataHandlingInterface: FirestoreDataHandlingInterface
    ) : GetWeightDetails {
        return GetWeightDetails(firestoreDataHandlingInterface)
    }
    @Provides
    @Singleton
    fun provideGetSplitDetails(
        firestoreDataHandlingInterface: FirestoreDataHandlingInterface
    ) : GetSplitDetails {
        return GetSplitDetails(firestoreDataHandlingInterface)
    }
    @Provides
    @Singleton
    fun provideGetPerformedDays(
        firestoreDataHandlingInterface: FirestoreDataHandlingInterface
    ) : GetPerformedDays {
        return GetPerformedDays(firestoreDataHandlingInterface)
    }
    @Provides
    @Singleton
    fun provideDeletePerformedDays(
        firestoreDataHandlingInterface: FirestoreDataHandlingInterface
    ) : DeletePerformedDay {
        return DeletePerformedDay(firestoreDataHandlingInterface)
    }

}