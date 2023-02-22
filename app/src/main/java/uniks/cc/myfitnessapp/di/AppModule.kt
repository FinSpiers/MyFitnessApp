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
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNavigationDestinations() : List<String> {
        return listOf("dashboard", "settings", "activity_detail_A", "activity_detail_B, activity_A, activity_B")
    }
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
        return CoreRepositoryImpl(db.SportActivityDao, apiService)
    }

}