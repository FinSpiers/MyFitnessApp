package uniks.cc.myfitnessapp.feature_workout.data.repository

import kotlinx.coroutines.runBlocking
import uniks.cc.myfitnessapp.core.data.database.SportActivitiesDao
import uniks.cc.myfitnessapp.core.domain.model.sport_activities.SportActivity
import uniks.cc.myfitnessapp.feature_workout.domain.repository.WorkoutRepository

class WorkoutRepositoryImpl(private val sportActivitiesDao: SportActivitiesDao) : WorkoutRepository {
    override var currentWorkout: SportActivity? = null
    override lateinit var workouts: MutableList<SportActivity>

    init {
        runBlocking {
            val sportActivities = mutableListOf<SportActivity>().apply {
                this.addAll(sportActivitiesDao.getAllWalkingActivities())
                this.addAll(sportActivitiesDao.getAllRunningActivities())
                this.addAll(sportActivitiesDao.getAllBicycleActivities())
                this.addAll(sportActivitiesDao.getAllPushUpsActivities())
                this.addAll(sportActivitiesDao.getAllSitUpActivities())
                this.addAll(sportActivitiesDao.getAllSquatsActivities())
            }
            workouts = sportActivities
            sortWorkouts()
        }
    }
    override suspend fun getAllSportActivitiesFromDatabase(): List<SportActivity> {
        return workouts
    }

    override suspend fun addSportActivityToDatabase(sportActivity: SportActivity) {
        when(sportActivity.workoutId) {
            0 -> sportActivitiesDao.addWalkingActivity(sportActivity as SportActivity.WalkingHiking)
            1 -> sportActivitiesDao.addRunningActivity(sportActivity as SportActivity.Running)
            2 -> sportActivitiesDao.addBicyclingActivity(sportActivity as SportActivity.BicycleRiding)
            3 -> sportActivitiesDao.addPushUpActivity(sportActivity as SportActivity.PushUp)
            4 -> sportActivitiesDao.addSitUpActivity(sportActivity as SportActivity.SitUp)
            5 -> sportActivitiesDao.addSquatsActivity(sportActivity as SportActivity.Squat)
        }
        workouts.add(sportActivity)
        sortWorkouts()
    }

    override suspend fun getSportActivityById(id: Int): SportActivity? {
        workouts.forEach {
            if (it.workoutId == id) {
                return it
            }
        }
        return null
    }

    private fun sortWorkouts(){
        workouts = workouts.sortedBy { it.timeStamp }.toMutableList()
    }

}