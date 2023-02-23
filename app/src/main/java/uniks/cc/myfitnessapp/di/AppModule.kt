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
        db: MyFitnessDatabase,
        apiService: OpenWeatherApiService
    ): CoreRepository {
        return CoreRepositoryImpl(db.sportActivitiesDao, apiService)
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
}