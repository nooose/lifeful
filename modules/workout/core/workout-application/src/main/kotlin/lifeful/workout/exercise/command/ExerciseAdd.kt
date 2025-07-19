package lifeful.workout.exercise.command

import lifeful.workout.exercise.Exercise

/**
 * 운동 종목 추가 유즈케이스
 */
interface ExerciseAdd {
    fun add(command: ExerciseCreateCommand): Exercise
}