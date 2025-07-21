package lifeful.health.workout

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import lifeful.health.workout.WorkoutFixtures.workout
import lifeful.health.workout.WorkoutFixtures.workoutItemRecord
import lifeful.shared.id.ExerciseId

class WorkoutTest : StringSpec({
    "운동에 기록을 추가할 수 있다." {
        val workout = workout()

        workout.addItem(ExerciseId(1L))
        workout.addItem(ExerciseId(2L))

        workout.items.shouldHaveSize(2)
    }

    "아이템 기록에 세트를 추가할 수 있다." {
        val itemRecord = workoutItemRecord()

        itemRecord.addSet(60.0, 10)
        itemRecord.addSet(70.0, 8)

        itemRecord.sets.shouldHaveSize(2)
    }

    "세트의 무게가 0 이하이면 예외를 던진다." {
        shouldThrow<IllegalArgumentException> {
            SetRecord(weight = 0.0, repetitions = 10, order = 1)
        }
    }

    "세트의 반복 수가 0 이하이면 예외를 던진다." {
        shouldThrow<IllegalArgumentException> {
            SetRecord(weight = 60.0, repetitions = 0, order = 1)
        }
    }
})
