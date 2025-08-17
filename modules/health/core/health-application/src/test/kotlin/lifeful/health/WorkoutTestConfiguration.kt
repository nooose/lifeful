package lifeful.health

import lifeful.health.exercise.ExerciseCacheRepository
import lifeful.health.exercise.TestExerciseCacheRepository
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class WorkoutTestConfiguration {

    @Bean
    fun workoutCacheRepository(): ExerciseCacheRepository {
        return TestExerciseCacheRepository()
    }
}
