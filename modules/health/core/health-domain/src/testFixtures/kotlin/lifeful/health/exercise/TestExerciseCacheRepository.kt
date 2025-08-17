package lifeful.health.exercise

class TestExerciseCacheRepository : ExerciseCacheRepository {
    private val exercises = mutableListOf<Exercise>()

    override fun putAll(exercises: List<Exercise>) {
        this.exercises.addAll(exercises)
    }

    override fun getAll(): List<Exercise> {
        return exercises.toList()
    }

    override fun deleteAll() {
        exercises.clear()
    }
}
