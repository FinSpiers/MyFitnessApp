package uniks.cc.myfitnessapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
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
import uniks.cc.myfitnessapp.feature_settings.data.repository.SettingsRepositoryImpl
import uniks.cc.myfitnessapp.feature_settings.domain.repository.SettingsRepository
import uniks.cc.myfitnessapp.feature_workout.data.repository.WorkoutRepositoryImpl
import uniks.cc.myfitnessapp.feature_workout.domain.repository.WorkoutRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
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
    fun provideWorkoutRepository(db: MyFitnessDatabase): WorkoutRepository {
        return WorkoutRepositoryImpl(db.sportActivitiesDao)
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
    fun provideSettingsRepository(db: MyFitnessDatabase): SettingsRepository {
        return SettingsRepositoryImpl(db.settingsDao)
    }

}