package lifeful.workout.exercise

enum class MuscleGroup(
    private val description: String,
) {
    CHEST("가슴"),
    BACK("등"),
    SHOULDERS("어깨"),
    BICEPS("이두근"),
    TRICEPS("삼두근"),
    FOREARMS("전완근"),
    CORE("코어 근육"),
    GLUTES("둔근"),
    QUADS("대퇴사두근"),
    HAMSTRINGS("햄스트링"),
    CALVES("종아리"),
    FULL_BODY("전신 운동"),
    MOBILITY("가동성 중심 운동"),
}
