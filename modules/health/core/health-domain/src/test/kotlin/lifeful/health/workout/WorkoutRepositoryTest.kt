package lifeful.health.workout

import io.kotest.core.spec.style.StringSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import jakarta.persistence.EntityManager
import lifeful.shared.id.ExerciseId
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class WorkoutRepositoryTest(
    private val workoutRepository: WorkoutRepository,
    private val entityManager: EntityManager,
) : StringSpec({
    extensions(SpringExtension)

    "운동 애그리거트를 저장할 수 있다" {
        val workout = WorkoutFixtures.workout()
        workoutRepository.save(workout)

        workout.id shouldNotBe 0L

        workout.addItem(ExerciseId(1L))
        workout.addItem(ExerciseId(2L))
        workoutRepository.save(workout)
        entityManager.flush()
        entityManager.clear()

        val findWorkout1 = workoutRepository.findById(workout.id)!!
        findWorkout1.items.shouldHaveSize(2)

        workout.items[0].addSet(10.0, 10)
        workout.items[0].addSet(30.0, 10)
        workout.items[0].addSet(40.0, 15)

        workoutRepository.save(workout)
        entityManager.flush()
        entityManager.clear()

        val findWorkout2 = workoutRepository.findById(workout.id)!!

        findWorkout2.items[0].sets.shouldHaveSize(3)
    }

    "존재하지 않는 ID로 조회하면 null을 반환한다." {
        val nonExistentId = Long.MAX_VALUE

        val foundWorkout = workoutRepository.findById(nonExistentId)

        foundWorkout shouldBe null
    }
})
