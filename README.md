# Lifeful

기록을 통해 스스로를 돌아보고, 반복적인 활동 속 인사이트를 발견할 수 있도록 돕습니다.

## 커밋 메시지 포맷
```
<type>(optional scope): <subject>
<BLANK LINE>
[optional body]
```

| type     | 설명                     | 예시                             |
|----------|------------------------|--------------------------------|
| feat     | 새로운 기능 추가              | feat(auth): 로그인 기능 구현          |
| fix      | 버그 수정                  | fix(book): 무한스크롤 중 중복 요청 오류 수정 |
| refactor | 코드 리팩토링 (기능 변경 없음)     | refactor(club): 도메인 구조 리팩토링    |
| style    | 코드 스타일 수정 (공백, 들여쓰기 등) | style: 포맷팅 적용                  |
| test     | 테스트 코드 추가 또는 수정        | test(step): 독서 진행 API 테스트 추가   |
| chore    | 빌드, 설정 등 기타 작업         | chore: CI/CD 스크립트 수정           |
| docs     | 문서 수정                  | docs: README에 커밋 규칙 추가         |

## 실행 방법

### 요구사항
- Java 21 / Kotlin
- Docker

### 실행
    ```bash
    ./gradlew :modules:boot-api:bootRun
    ```
-   **API 서버 포트**: `8080`
-   **Swagger API 문서**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 모니터링 및 부하 테스트

애플리케이션의 성능을 관찰하고 부하 테스트를 수행할 수 있습니다.

### k6
  ```bash
  # macOS (Homebrew)
  brew install k6
  
  k6 run load-tests/script.js
  ```

### Grafana
- **URL**: [http://localhost:3000](http://localhost:3000)
- **계정**: `admin` / `admin`
- Prometheus 데이터 소스(http://promethus:9090) 적용 

## 문서
- [도메인 설계](docs/domain.md)
- [개발 가이드](GEMINI.md)
