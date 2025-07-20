package lifeful.workout.exercise.query

import lifeful.shared.id.ExerciseId
import lifeful.workout.exercise.Exercise

/**
 * 운동 종목 검색 유즈케이스
 */
interface ExerciseFinder {
    fun get(id: ExerciseId): Exercise

    fun get(ids: List<ExerciseId>): List<Exercise>

    fun all(): List<Exercise>
}
