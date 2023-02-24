package uniks.cc.myfitnessapp.di

import android.app.Application
import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uniks.cc.myfitnessapp.core.data.database.MyFitnessDatabase
import uniks.cc.myfitnessapp.core.data.network.OpenWeatherApiService
import uniks.cc.myfitnessapp.core.data.repository.CoreRepositoryImpl
import uniks.cc.myfitnessapp.core.data.repository.SensorRepositoryImpl
import uniks.cc.myfitnessapp.core.domain.model.sensors.AndroidSensors
import uniks.cc.myfitnessapp.core.domain.model.sensors.GyroscopeSensor
import uniks.cc.myfitnessapp.core.domain.model.sensors.StepCounterSensor
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.domain.repository.SensorRepository
import uniks.cc.myfitnessapp.feature_active_workout.data.repository.WorkoutRepositoryImpl
import uniks.cc.myfitnessapp.feature_active_workout.domain.repository.WorkoutRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMyFitnessDatabase(app: Application) : MyFitnessDatabase {
        return Room.databaseBuilder(
            app,
            MyFitnessDatabase::class.java,
            "MyFitness_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideApiService(): OpenWeatherApiService {
        return OpenWeatherApiService()
    }

    @Provides
    @Singleton
    fun provideCoreRepository(
        apiService: OpenWeatherApiService
    ): CoreRepository {
        return CoreRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideWorkoutRepository(db : MyFitnessDatabase): WorkoutRepository {
        return WorkoutRepositoryImpl(db.workoutDao)
    }

    @Provides
    @Singleton
    fun provideSensorRepository(
        app: Application
    ): SensorRepository {
        return SensorRepositoryImpl(app)
    }

    @Provides
    @Singleton
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    fun provideStepCounterSensor(app: Application): AndroidSensors {
        return StepCounterSensor(app)
    }

    @Provides
    @Singleton
    fun provideGyroscopeSensor(app: Application): AndroidSensors {
        return GyroscopeSensor(app)
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