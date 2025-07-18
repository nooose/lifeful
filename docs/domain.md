이 서비스는 사용자가 루틴 기반 또는 자유롭게 운동을 기록하고, 반복된 데이터를 기반으로 인사이트를 얻을 수 있도록 한다.
서버는 고정된 운동 종목 목록을 제공하며, 사용자는 이를 기반으로 세트를 기록한다.

# 핵심 개념
| 한국어 용어   | 영어 모델명              | 설명                                    |
| -------- | ------------------- |---------------------------------------|
| 사용자      | `Member`            | 운동을 기록하고 인사이트를 받는 주체                  |
| 운동 종목    | `Exercise`          | 서버에서 정의한 고정 운동 종목. 사용자는 이 중에서만 선택 가능  |
| 루틴       | `Routine`           | 운동 종목들로 구성된 반복 가능한 운동 계획 템플릿          |
| 운동 기록    | `Workout`           | 실제 수행된 운동 기록. 루틴 기반일 수도, 자유 운동일 수도 있음 |
| 운동 항목 기록 | `WorkoutItemRecord` | 운동 기록 내 각 운동 항목의 기록                   |
| 세트 기록    | `SetRecord`         | 한 운동 항목 내 각 세트별 수행 정보                 |
| 인사이트     | `Insight`           | 누적된 운동 기록을 기반으로 한 통계 및 분석 결과          |

# 도메인 설계 방향
- 운동 종목은 서버가 정의한 고정 목록으로만 관리하며, 자유로운 텍스트 입력은 허용하지 않음.
- 사용자는 루틴 기반 또는 루틴 없이 자유롭게 운동 기록 가능.
- 운동 기록은 루틴 ID를 가질 수도, 생략할 수도 있다.
- 세트 단위로 무게, 반복 횟수, 회복 지표 등 다양한 값을 기록 가능.
- 기록된 데이터를 기반으로 최고기록, 운동 빈도, 루틴 수행률 등의 인사이트 제공.

# 기능 요구사항 목록
- 루틴을 생성하고 운동 항목을 추가한다
- 루틴을 수정 또는 삭제한다
- 루틴을 선택하여 운동을 시작한다
- 루틴 없이 운동을 시작한다
- 자유롭게 운동 종목을 선택하고 세트를 기록한다
- 자유 운동 기록을 기반으로 루틴을 생성한다
- 운동 도중 세트를 추가/수정한다
- 운동 항목을 추가하거나 순서를 변경한다
- 운동을 마무리하고 회고를 작성한다
- 과거 운동 기록을 조회하고 분석한다
- 주간/월간 루틴 수행률을 확인한다
- 종목별 최고 중량 변화 추이를 본다
- 특정 운동의 총 수행 볼륨/빈도 등을 확인한다
- 전체 운동 목록을 조회하고 필터링한다
- 자주 사용하는 운동을 즐겨찾기한다
- 최근 사용한 운동을 빠르게 선택한다
