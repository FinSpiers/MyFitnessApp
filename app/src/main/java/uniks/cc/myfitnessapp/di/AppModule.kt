package uniks.cc.myfitnessapp.di

import android.app.Application
import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import androidx.room.Room
import androidx.work.WorkManager
import com.google.android.gms.location.ActivityRecognition
import com.google.android.gms.location.ActivityRecognitionClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.GlobalScope
import uniks.cc.myfitnessapp.core.data.database.MyFitnessDatabase
import uniks.cc.myfitnessapp.feature_dashboard.data.network.OpenWeatherApiService
import uniks.cc.myfitnessapp.core.data.repository.CoreRepositoryImpl
import uniks.cc.myfitnessapp.core.data.repository.SensorRepositoryImpl
import uniks.cc.myfitnessapp.core.domain.model.sensors.AccelerometerSensor
import uniks.cc.myfitnessapp.core.domain.model.sensors.StepCounterSensor
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.domain.repository.SensorRepository
import uniks.cc.myfitnessapp.feature_dashboard.data.DashboardDao
import uniks.cc.myfitnessapp.feature_dashboard.data.repository.DashBoardRepositoryImpl
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.DashBoardRepository
import uniks.cc.myfitnessapp.feature_workout.data.repository.WorkoutRepositoryImpl
import uniks.cc.myfitnessapp.feature_workout.domain.repository.WorkoutRepository
import uniks.cc.myfitnessapp.feature_settings.data.repository.SettingsRepositoryImpl
import uniks.cc.myfitnessapp.feature_settings.domain.repository.SettingsRepository
import uniks.cc.myfitnessapp.feature_workout.domain.current_workout.util.stopwatch.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWorkoutNames() : List<String> {
        return listOf("Walking", "Running", "Bicycling", "PushUps", "SitUps", "Squats")
    }


    @Provides
    @Singleton
    fun provideActivityRecognitionClient(app: Application) : ActivityRecognitionClient {
        return ActivityRecognition.getClient(app.applicationContext)
    }

    @Provides
    @Singleton
    fun provideMyFitnessDatabase(app: Application): MyFitnessDatabase {
        return Room.databaseBuilder(
            app,
            MyFitnessDatabase::class.java,
            "MyFitness_db"
        ).build()
    }


    @Provides
    @Singleton
    fun provideStopwatchManager() : StopwatchManager {
        val timestampProvider = TimestampProviderImpl()
        val elapsedTimeCalculator = ElapsedTimeCalculator(timestampProvider)
        val stopwatchStateCalculator = StopwatchStateCalculator(timestampProvider, elapsedTimeCalculator)
        val stopwatchStateHolder = StopwatchStateHolder(stopwatchStateCalculator, elapsedTimeCalculator, TimestampMillisecondsFormatter())
        return StopwatchManager(stopwatchStateHolder, GlobalScope)
    }

    @Provides
    @Singleton
    fun provideApiService(): OpenWeatherApiService {
        return OpenWeatherApiService()
    }

    @Provides
    @Singleton
    fun provideWorkManager(app: Application) : WorkManager {
        return WorkManager.getInstance(app)
    }

    @Provides
    @Singleton
    fun provideCoreRepository(app: Application) : CoreRepository {
        return CoreRepositoryImpl(app)
    }

    @Provides
    @Singleton
    fun provideDashBoardRepository(
        apiService: OpenWeatherApiService,
        db : MyFitnessDatabase
    ): DashBoardRepository {
        return DashBoardRepositoryImpl(apiService, db.dashboardDao)
    }

    @Provides
    @Singleton
    fun provideWorkoutRepository(db : MyFitnessDatabase) : WorkoutRepository {
        return WorkoutRepositoryImpl(db.workoutDao)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(db : MyFitnessDatabase) : SettingsRepository {
        return SettingsRepositoryImpl(db.settingsDao)
    }


    @Provides
    @Singleton
    fun provideSensorRepository(
        stepCounterSensor: StepCounterSensor,
        accelerometerSensor: AccelerometerSensor
    ): SensorRepository {
        return SensorRepositoryImpl(stepCounterSensor, accelerometerSensor)
    }

    @Provides
    @Singleton
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    fun provideStepCounterSensor(app: Application): StepCounterSensor {
        return StepCounterSensor(app)
    }

    @Provides
    @Singleton
    fun provideAccelerometerSensor(app: Application): AccelerometerSensor {
        return AccelerometerSensor(app)
    }

    @Provides
    @Singleton
    fun provideLocationManager(app: Application): LocationManager {
        return app.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(app: Application): ConnectivityManager {
        return app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun provideFusedLocationManager(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app.applicationContext)
    }
}