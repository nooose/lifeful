package lifeful.health.exercise

enum class ExerciseCategory(
    private val description: String,
) {
    STRENGTH("근력 운동"),
    CARDIO("유산소 운동"),
    MOBILITY("스트레칭"),
    BODYWEIGHT("맨몸 운동"),
    OLYMPIC("기술 기반 중량 운동"),
    REHAB("재활/교정 목적의 운동"),
    OTHER("기타 분류"),
}
