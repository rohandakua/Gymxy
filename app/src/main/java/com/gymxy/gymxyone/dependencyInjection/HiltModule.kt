package com.gymxy.gymxyone.dependencyInjection

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.gymxy.gymxyone.Secrets.WEB_CLIENT_ID
import com.gymxy.gymxyone.data.offline.SettingDataHandler
import com.gymxy.gymxyone.data.offline.SharedPreferenceCollectionName
import com.gymxy.gymxyone.data.offline.SharedPreferenceDataHandler
import com.gymxy.gymxyone.data.online.FirestoreDataHandler
import com.gymxy.gymxyone.domain.helperFunctions.SortingHelper
import com.gymxy.gymxyone.domain.repositoryInterface.AlarmSchedulerInterface
import com.gymxy.gymxyone.domain.repositoryInterface.SettingScreenDataInterface
import com.gymxy.gymxyone.domain.repositoryInterface.StopwatchRepository
import com.gymxy.gymxyone.domain.useCases.alarmUsecases.CancelAlarm
import com.gymxy.gymxyone.domain.useCases.alarmUsecases.ScheduleAlarm
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.DeletePerformedDay
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.GetHeightDetails
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.GetPerformedDays
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.GetSplitById
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.GetSplitDetails
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.GetWeightDetails
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.SaveHeight
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.SavePerformedDay
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.SaveSplit
import com.gymxy.gymxyone.domain.useCases.firestoreUsecases.SaveWeight
import com.gymxy.gymxyone.domain.useCases.settingUsecases.GetBMI
import com.gymxy.gymxyone.domain.useCases.settingUsecases.GetHeightUnit
import com.gymxy.gymxyone.domain.useCases.settingUsecases.GetNotificationPermission
import com.gymxy.gymxyone.domain.useCases.settingUsecases.GetNotificationTime
import com.gymxy.gymxyone.domain.useCases.settingUsecases.GetRaingPermission
import com.gymxy.gymxyone.domain.useCases.settingUsecases.GetTrainingSplit
import com.gymxy.gymxyone.domain.useCases.settingUsecases.GetWeightUnit
import com.gymxy.gymxyone.domain.useCases.settingUsecases.SaveHeightSetting
import com.gymxy.gymxyone.domain.useCases.settingUsecases.SaveHeightUnit
import com.gymxy.gymxyone.domain.useCases.settingUsecases.SaveNotificationPermission
import com.gymxy.gymxyone.domain.useCases.settingUsecases.SaveNotificationTime
import com.gymxy.gymxyone.domain.useCases.settingUsecases.SaveRatingPermission
import com.gymxy.gymxyone.domain.useCases.settingUsecases.SaveWeightSetting
import com.gymxy.gymxyone.domain.useCases.settingUsecases.SaveWeightUnit
import com.gymxy.gymxyone.domain.useCases.settingUsecases.SetTrainingSplit
import com.gymxy.gymxyone.domain.useCases.stopwatchUsecases.GetFormattedTime
import com.gymxy.gymxyone.domain.useCases.stopwatchUsecases.PauseStopwatch
import com.gymxy.gymxyone.domain.useCases.stopwatchUsecases.ResetStopwatch
import com.gymxy.gymxyone.domain.useCases.stopwatchUsecases.StartStopwatch
import com.gymxy.gymxyone.systemPackages.AlarmSchedulerInterfaceImplementation
import com.gymxy.gymxyone.systemPackages.MediaPlayerManager
import com.gymxy.gymxyone.systemPackages.StopwatchRepositoryImplementation
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
        return context.getSharedPreferences(SharedPreferenceCollectionName.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
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
    @Provides
    @Singleton
    fun provideGetSplitById(
        firestoreDataHandlingInterface: FirestoreDataHandlingInterface
    ): GetSplitById{
        return GetSplitById(firestoreDataHandlingInterface)
    }

    @Provides
    @Singleton
    fun provideSettingDataHandler(
        sharedPreferenceDataHandler: SharedPreferenceDataHandler,
        saveHeight: SaveHeight,
        saveWeight: SaveWeight,
        getSplitById: GetSplitById,
        getWeightDetails: GetWeightDetails,
        getHeightDetails: GetHeightDetails
    ): SettingScreenDataInterface{
        return SettingDataHandler(
            sharedPreferenceDataHandler,
            saveHeight,
            saveWeight,
            getSplitById,
            getWeightDetails,
            getHeightDetails
        )
    }

    @Provides
    @Singleton
    fun provideGetBMI(
        settingScreenDataInterface: SettingScreenDataInterface
    ): GetBMI {
        return GetBMI(settingScreenDataInterface)
    }
    @Provides
    @Singleton
    fun provideHeightUnit(
        settingScreenDataInterface: SettingScreenDataInterface
    ): GetHeightUnit {
        return GetHeightUnit(settingScreenDataInterface)
    }
    @Provides
    @Singleton
    fun provideGetNotficationPermission(
        settingScreenDataInterface: SettingScreenDataInterface
    ): GetNotificationPermission {
        return GetNotificationPermission(settingScreenDataInterface)
    }
    @Provides
    @Singleton
    fun provideGetNotificationTime(
        settingScreenDataInterface: SettingScreenDataInterface
    ): GetNotificationTime {
        return GetNotificationTime(settingScreenDataInterface)
    }
    @Provides
    @Singleton
    fun provideGetRatingPermission(
        settingScreenDataInterface: SettingScreenDataInterface
    ): GetRaingPermission {
        return GetRaingPermission(settingScreenDataInterface)
    }
    @Provides
    @Singleton
    fun provideGetTrainingSplit(
        settingScreenDataInterface: SettingScreenDataInterface
    ): GetTrainingSplit {
        return GetTrainingSplit(settingScreenDataInterface)
    }
    @Provides
    @Singleton
    fun provideGetWeightUnit(
        settingScreenDataInterface: SettingScreenDataInterface
    ): GetWeightUnit {
        return GetWeightUnit(settingScreenDataInterface)
    }
    @Provides
    @Singleton
    fun provideSaveHeightSetting(
        settingScreenDataInterface: SettingScreenDataInterface
    ): SaveHeightSetting {
        return SaveHeightSetting(settingScreenDataInterface)
    }
    @Provides
    @Singleton
    fun provideSaveHeightUnit(
        settingScreenDataInterface: SettingScreenDataInterface
    ): SaveHeightUnit {
        return SaveHeightUnit(settingScreenDataInterface)
    }
    @Provides
    @Singleton
    fun provideSaveNotificationPermission(
        settingScreenDataInterface: SettingScreenDataInterface
    ): SaveNotificationPermission {
        return SaveNotificationPermission(settingScreenDataInterface)
    }
    @Provides
    @Singleton
    fun provideSaveNotificationTime(
        settingScreenDataInterface: SettingScreenDataInterface
    ): SaveNotificationTime {
        return SaveNotificationTime(settingScreenDataInterface)
    }
    @Provides
    @Singleton
    fun provideSaveRatingPermission(
        settingScreenDataInterface: SettingScreenDataInterface
    ): SaveRatingPermission {
        return SaveRatingPermission(settingScreenDataInterface)
    }
    @Provides
    @Singleton
    fun provideSaveWeightSetting(
        settingScreenDataInterface: SettingScreenDataInterface
    ): SaveWeightSetting {
        return SaveWeightSetting(settingScreenDataInterface)
    }
    @Provides
    @Singleton
    fun provideSaveWeightUnit(
        settingScreenDataInterface: SettingScreenDataInterface
    ): SaveWeightUnit {
        return SaveWeightUnit(settingScreenDataInterface)
    }
    @Provides
    @Singleton
    fun provideSetTrainingSplit(
        settingScreenDataInterface: SettingScreenDataInterface
    ): SetTrainingSplit {
        return SetTrainingSplit(settingScreenDataInterface)
    }

    @Provides
    @Singleton
    fun provideMediaPlayerManager(
        @ApplicationContext context:Context
    ): MediaPlayerManager {
        return MediaPlayerManager(context)
    }

    @Provides
    fun providesNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManager{
        return try {
            val notificationManager: NotificationManager =
                context.getSystemService(NotificationManager::class.java) as NotificationManager
            notificationManager
        }catch (e : Exception){
            throw RuntimeException("Error in creating notification manager")
        }
    }
    @Provides
    fun providesAlarmSchedulerInterface(
        @ApplicationContext context: Context,
        alarmManager: AlarmManager,
    ): AlarmSchedulerInterface {
        return AlarmSchedulerInterfaceImplementation(context, alarmManager)
    }

    @Provides
    @Singleton
    fun provideAlarmManager(@ApplicationContext context : Context):AlarmManager{
        return context.getSystemService(AlarmManager::class.java)

    }
    @Provides
    fun provideCancelAlarm (
        alarmSchedulerInterface: AlarmSchedulerInterface,
        getNotificationTime: GetNotificationTime
    ): CancelAlarm {
        return CancelAlarm(alarmSchedulerInterface,getNotificationTime)
    }
    @Provides
    fun provideScheduleAlarm (
        alarmSchedulerInterface: AlarmSchedulerInterface,
        getNotificationTime: GetNotificationTime
    ): ScheduleAlarm {
        return ScheduleAlarm(alarmSchedulerInterface,getNotificationTime)
    }

    @Provides
    @Singleton
    fun providesStopwatchRepository (): StopwatchRepository {
        return StopwatchRepositoryImplementation()

    }
    @Provides
    fun providesGetFormattedTimeUsecase(
        stopwatchRepository: StopwatchRepository
    ): GetFormattedTime {
        return GetFormattedTime(stopwatchRepository)
    }
    @Provides
    fun providesPauseStopwatch(
        stopwatchRepository: StopwatchRepository
    ):PauseStopwatch{
        return PauseStopwatch(stopwatchRepository)
    }
    @Provides
    fun providesResetStopwatch(stopwatchRepository: StopwatchRepository):ResetStopwatch{
        return ResetStopwatch(stopwatchRepository)
    }
    @Provides
    fun providesStartStopwatch(stopwatchRepository: StopwatchRepository):StartStopwatch{
        return StartStopwatch(stopwatchRepository)
    }
    @Provides
    @Singleton
    fun provideSortingHelper(
        getTrainingSplit: GetTrainingSplit
    ): SortingHelper {
        return SortingHelper(getTrainingSplit)
    }



}