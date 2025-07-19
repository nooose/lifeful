package lifeful.workout.exercise.command

import lifeful.shared.id.ExerciseId
import lifeful.workout.exercise.Exercise

/**
 * 운동 종목 수정 유즈케이스
 */
interface ExerciseModify {
    fun modify(
        id: ExerciseId,
        command: ExerciseModifyCommand,
    ): Exercise
}