# Readful 프로젝트 개발 가이드

이 문서는 Readful 프로젝트의 아키텍처, 개발 컨벤션, 기술 스택 등을 설명하여 개발자들이 프로젝트의 구조를 이해하고 일관된 스타일로 개발에 참여하는 것을 돕기 위해 작성되었습니다.

## 1. 아키텍처

Readful 프로젝트는 헥사고날 아키텍처(Hexagonal Architecture)와 도메인 주도 설계(Domain-Driven Design, DDD)의 원칙을 따르는 현대적인 **모노레포 멀티 모듈 아키텍처**를 채택하고 있습니다.

### 1.1. 헥사고날 아키텍처

헥사고날 아키텍처는 핵심 로직(Domain)을 외부 기술(Adapter)로부터 분리하여 유연하고 테스트하기 쉬운 구조를 만드는 것을 목표로 합니다.

- **Domain Layer (Core)**: 순수한 도메인 로직을 담고 있으며, 외부 기술(예: 데이터베이스, 외부 API)에 대한 의존성이 없는 것을 원칙으로 합니다. 다만, 실용적인 관점에서 JPA 어노테이션(@Entity, @Id 등)을 사용하여 도메인 객체와 데이터베이스 테이블 매핑을 함께 정의하고 있습니다. 이는 도메인 모델의 영속성을 위한 최소한의 기술적 결합으로 간주합니다.
  - `*-domain` 모듈 (예: `member-domain`, `health-domain`)
  - `Aggregate`, `Entity`, `Repository Interface`, `Domain Event` 등이 위치합니다.
  - **Value Object**: 값 객체는 고유 식별자가 없으며, 속성들의 값을 통해 동등성을 판단합니다. 불변(immutable)이어야 하며, 특정 도메인 개념을 표현하고 해당 값에 대한 유효성 검사 로직을 포함합니다. 예를 들어, `Email` 값 객체는 이메일 주소의 유효성을 자체적으로 검증합니다.
- **Application Layer (Core)**: Domain Layer를 사용하여 실제 사용 사례(Use Case)를 구현하며, **CQRS(Command Query Responsibility Segregation)** 패턴을 적용합니다.
  - `*-application` 모듈 (예: `member-application`, `health-application`)
  - **Command**: 시스템의 상태를 변경하는 작업을 담당합니다. (예: `MemberRegisterCommand`, `ExerciseCreateCommand`)
  - **Query**: 시스템의 상태를 조회하는 작업을 담당합니다. (예: `MemberFinder`, `ExerciseQueryService`)
  - `Service`는 도메인 객체들을 사용하여 비즈니스 로직을 오케스트레이션하고, 트랜잭션 관리 등 애플리케이션 로직을 처리합니다. **이때, `Service`는 외부 모듈에 노출되는 인터페이스를 통해 기능을 제공하고, 실제 구현체는 `internal` 키워드를 사용하여 모듈 내부로 가시성을 제한해야 합니다.**
- **Adapter Layer**: 외부 세계와의 상호작용을 담당합니다. 이 계층은 외부 기술에 의존하며, 비즈니스 로직의 변경 없이 외부 기술을 교체할 수 있도록 설계되었습니다.
  - **Inbound Adapter**: 외부의 요청을 내부로 전달합니다.
    - `*-api` 모듈 (예: `member-api`, `health-api`): REST API를 제공합니다. 현재는 REST API를 사용하지만, gRPC 등 다른 프로토콜로 변경하더라도 `Application` 및 `Domain` 레이어에 영향을 주지 않고 `api` 모듈만 수정하여 대응할 수 있습니다.
  - **Outbound Adapter**: 내부의 요청을 외부로 전달합니다.
    - `*-integration` 모듈 (예: `member-integration`): 다른 도메인 모듈에 데이터를 전파할 수 있는 **OHS**(Open Host Service)입니다.
      - 다른 모듈에게 제공되는 객체는 `XxxPublicModel` 네이밍을 따릅니다.
      - 예를 들어, `member-integration` 모듈에서 `MemberPublicModel`을 정의하여 다른 모듈에서 회원 정보를 사용할 수 있도록 합니다.
    - `*-infrastructure`, `*-client`: 상황에 따라 해당 모듈들이 존재할 수 있습니다.

### 1.2. 모듈 구조

프로젝트는 기능과 역할에 따라 여러 모듈로 나뉘어 있습니다.

```
readful/
├── modules/
│   ├── boot-batch            # Spring Boot Batch 서버 실행 모듈
│   ├── boot-api              # Spring Boot API 서버 실행 모듈
│   ├── base                  # 모든 모듈에서 공통으로 사용하는 기반 코드 (ID, Entity, Exception 등)
│   ├── security              # 인증/인가 등 보안 관련 모듈
│   ├── support/              # 프로젝트 지원 모듈
│   │   ├── api-common        # API 공통 응답, 예외 처리 등
│   │   └── api-doc           # SpringDoc (Swagger) 설정
│   ├── member/               # 회원 도메인
│   │   ├── core/
│   │   │   ├── member-domain       # 회원 도메인 핵심 로직
│   │   │   └── member-application  # 회원 관련 유즈케이스
│   │   ├── member-api          # 회원 관련 REST API
│   │   └── member-integration  # 회원 관련 외부 시스템 연동
│   └── health/               # 건강(운동) 도메인
│       ├── core/
│       │   ├── health-domain
│       │   └── health-application
│       └── health-api
...
```

- **`boot-api`, `boot-batch`**: 각각 API 서버와 배치 애플리케이션의 실행 진입점(main 함수) 역할을 하는 모듈입니다. 이처럼 여러 실행 가능한 모듈을 하나의 모노레포에서 관리함으로써, 별도의 Git 레포지토리로 분리하지 않고도 `member`, `health`와 같은 공통 도메인 및 비즈니스 로직을 다양한 애플리케이션에서 효율적으로 재사용할 수 있습니다.
- **`base`**: `BaseEntity`, 커스텀 `Exception`, 공통 ID 클래스 등 프로젝트 전반에서 사용되는 기반 코드를 포함합니다.
  - **주의**: '공통'이라는 이유로 무분별하게 코드를 추가하여 '공통 모듈 지옥(Common Module Hell)'에 빠지지 않도록 주의해야 합니다. 재사용 가능한 모듈은 명확한 목적과 이름을 가져야 합니다.
- **`security`**: 인증/인가 등 보안 관련 어댑터
- **`support`**: `api-common` (공통 응답 포맷, `GlobalExceptionHandler` 등), `api-doc` (API 문서 자동화) 등 프로젝트의 주요 관심사는 아니지만 개발에 필요한 부가 기능들을 제공합니다.
- **도메인 모듈 ( `member`, `health` 등)**: 각 비즈니스 도메인을 독립적인 모듈로 구성합니다. 각 도메인 모듈은 `core`(`domain`, `application`), `api`, `integration`, `storage`, `client`, `infrastructure` 등으로 세분화될 수 있습니다.

### 1.3. 모듈 간 의존성 및 가시성 제어

헥사고날 아키텍처의 핵심 원칙을 지키기 위해 모듈 간 의존성과 가시성을 엄격하게 제어합니다.

- **의존성 방향**: 의존성은 항상 외부(Adapter)에서 내부(Domain)로 향해야 합니다. 즉, `api` -> `application` -> `domain` 순서로 의존성이 흐릅니다. `domain` 모듈은 다른 어떤 모듈에도 의존하지 않습니다.
- **Gradle을 이용한 의존성 강제**: 각 모듈의 `build.gradle.kts` 파일에서 `api`, `implementation` 키워드를 사용하여 모듈 간의 의존성을 명확하게 정의합니다.

  ```kotlin
  // in member-application/build.gradle.kts
  dependencies {
      implementation(project(":modules:member:core:member-domain"))
      // ...
  }
  ```
- **포트(Port)와 어댑터(Adapter)를 통한 의존성 역전(DIP)**: 도메인 레이어는 외부 기술에 대한 의존성을 갖지 않기 위해 인터페이스(Port)를 정의하고, 외부 어댑터가 이 인터페이스를 구현하도록 합니다. 이를 통해 의존성의 방향이 외부에서 내부로 향하게 되어, 기술 교체에 유연한 구조를 가집니다.

  **예시: `PasswordEncoder`**
  1.  **Port**: `member-domain` 모듈에 `PasswordEncoder` 인터페이스를 정의합니다. 이는 도메인이 필요로 하는 암호화 기능의 명세(Port)입니다.
      ```kotlin
      // in member-domain
      interface PasswordEncoder {
          fun encode(rawPassword: String): String
          fun matches(rawPassword: String, encodedPassword: String): Boolean
      }
      ```
  2.  **Adapter**: `security` 모듈에서 `SecurePasswordEncoder` 클래스가 `PasswordEncoder` 인터페이스를 구현합니다. 이는 Spring Security의 `BCryptPasswordEncoder`를 사용하는 실제 구현체(Adapter)입니다.
      ```kotlin
      // in security
      @Component
      internal class SecurePasswordEncoder(...) : PasswordEncoder { ... }
      ```
  3.  **의존성 역전**: 이를 통해 `member-domain`은 `security` 모듈이나 `BCryptPasswordEncoder`에 대해 전혀 알지 못한 채, 오직 자신이 정의한 `PasswordEncoder` 포트에만 의존하게 됩니다. 이것이 바로 의존성 역전 원칙(DIP)입니다.

- **인터페이스와 구현체 분리**: 외부 모듈에 기능을 제공할 때는 인터페이스(Port)를 사용하고, 실제 구현체는 `internal` 키워드를 사용하여 모듈 내부로 가시성을 제한합니다. 이를 통해 외부 모듈은 구현체의 상세 내용에 의존하지 않고 인터페이스에만 의존하게 되어 결합도를 낮출 수 있습니다. 이는 모듈 간의 느슨한 결합을 유지하고, 특정 구현 기술에 대한 의존성을 줄이는 핵심적인 방법입니다.

  ```kotlin
  // member-application 모듈
  
  // 외부 모듈에 공개되는 인터페이스
  interface MemberRegister {
      fun register(command: MemberRegisterCommand): Member
  }
  
  // 모듈 내부에서만 접근 가능한 구현체
  @Service
  internal class MemberManager(...) : MemberRegister {
      override fun register(command: MemberRegisterCommand): Member {
          // ...
      }
  }
  ```

- **모듈 간 데이터 조회**: 다른 애그리거트의 데이터를 포함하여 조회해야 하는 경우, 데이터베이스 조인(DB Join)을 직접 수행하는 대신, 해당 애그리거트를 소유한 모듈에서 제공하는 OHS(Open Host Service)인 `*-integration` 모듈을 의존하여 메모리 조인(Memory Join)을 수행합니다. 이는 각 도메인 모듈의 독립성을 유지하고, 모듈 간의 강한 결합을 방지하여 유연한 확장을 가능하게 합니다.

- **`api` 모듈**: `application` 모듈의 인터페이스를 호출하여 사용자의 요청을 처리하고 응답을 반환합니다.

## 2. 개발 컨벤션

### 2.1. 네이밍/함수 컨벤션
- **Package**: 소문자와 `.`를 사용합니다. (예: `lifeful.member.domain`)
- **Function/Variable**: camelCase를 사용합니다. (예: `registerMember`, `memberId`)
- **함수 네이밍**:
  - `findXxx`: 결과가 존재하지 않을 수 있는 경우 (null 반환 가능성).
  - `getXxx`: 결과가 항상 존재한다고 가정하는 경우 (null이 아닌 값 반환, 없으면 예외 발생).
- **Map 변수명**: `keyToValue` 형식으로 사용합니다. (예: `idToMember`, `noToCompany`, `keyToNames`)
- **컬렉션 변수명**: 복수형을 사용합니다. (예: `members`, `companies`, `names`)
- **DTO (Data Transfer Object)**
  - **API 계층**: `XxxRequest`, `XxxResponse` 형식을 사용합니다. (예: `MemberRegisterRequest`, `MemberResponse`)
  - **애플리케이션 계층**: `XxxCommand`, `XxxQuery` 형식을 사용합니다. (예: `MemberRegisterCommand`, `MemberDetailQuery`)
  - **데이터 반환 규칙**:
    - `application` 모듈은 가능한 한 도메인 모델을 반환해야 합니다.
    - `api` 모듈은 `application` 모듈에서 반환된 도메인 모델을 `XxxResponse` DTO로 변환하는 역할을 담당합니다.
    - 만약 도메인 모델만으로 데이터 조회가 불충분한 경우, `application` 모듈 내에 조회를 위한 `XxxQuery` DTO를 정의할 수 있습니다. 이 경우에도 `api` 모듈에서 최종적으로 `XxxResponse`로 변환해야 합니다. 이 규칙은 `application` 계층이 외부 기술(API 응답 형식)에 의존하지 않도록 하여 계층 간의 결합도를 낮추고 유연성을 높이는 데 중요합니다.

### 2.2. API 개발 컨벤션

`api` 모듈은 다음과 같은 컨벤션을 따릅니다.

- **RESTful API 설계**: HTTP API는 RESTful 원칙을 따라야 합니다. 이는 자원(Resource) 중심의 URL 설계, HTTP 메서드(GET, POST, PUT, DELETE 등)의 적절한 사용, 상태 비저장(Stateless) 통신 등을 포함합니다.
  - **예시**:
    - 회원 목록 조회: `GET /api/v1/members`
    - 특정 회원 조회: `GET /api/v1/members/{memberId}`
    - 회원 등록: `POST /api/v1/members`
    - 회원 정보 수정: `PUT /api/v1/members/{memberId}`
    - 회원 삭제: `DELETE /api/v1/members/{memberId}`

- **인터페이스와 구현체 분리**: API 명세와 실제 구현을 분리하여 가독성과 유지보수성을 높입니다. `XxxApi` 인터페이스는 SpringDoc 애너테이션(`@Tag`, `@Operation` 등)을 사용하여 API 문서를 명세하며, 순수하게 문서화만을 담당합니다. 실제 로직은 `XxxRestController` 클래스에서 `XxxApi` 인터페이스를 구현하여 처리합니다.

  ```kotlin
  // MemberApi.kt - API 문서화 인터페이스
  @Tag(name = "회원 API")
  interface MemberApi {
      @Operation(summary = "회원 가입")
      fun register(request: MemberRegisterRequest)
  }

  // MemberRestController.kt - 실제 구현체
  @RestController
  class MemberRestController(...) : MemberApi {
      override fun register(request: MemberRegisterRequest) {
          // ... application service 호출
      }
  }
  ```

- **DTO 문서화**: `api` 모듈에서 사용되는 `XxxRequest`, `XxxResponse` DTO 규격에는 SpringDoc 애너테이션(`@Schema`, `@Parameter` 등)을 사용하여 문서화를 필수로 명시해야 합니다. 이는 API 명세를 명확하게 하고 클라이언트 개발자가 API를 쉽게 이해하고 사용할 수 있도록 돕습니다.

### 2.3. 코딩 스타일

- **Kotlin Style Guide**: [Kotlin 공식 스타일 가이드](https://kotlinlang.org/docs/coding-conventions.html)를 따릅니다.
- **Ktlint**: 코드 스타일을 일관성 있게 유지하기 위해 `ktlint`를 사용합니다.
  - **수동 검사**: `./gradlew ktlintCheck` 명령어로 코드 스타일을 검사합니다.
  - **자동 수정**: `./gradlew ktlintFormat` 명령어로 자동으로 코드 스타일을 수정할 수 있습니다.

### 2.4. 커밋 메시지

[Conventional Commits](https://www.conventionalcommits.org/ko/v1.0.0/) 명세를 따르는 것을 권장합니다.

- **형식**: `<type>(<scope>): <subject>`
- **예시**:
  - `feat(member): 회원 가입 API 추가`
  - `fix(health): 운동 기록 조회 시 발생하는 버그 수정`
  - `docs(readme): 프로젝트 실행 방법 업데이트`

### 2.5. 도메인 모델 컨벤션

#### 풍부한 도메인 모델 (Rich Domain Model)
우리는 데이터와 해당 데이터에 대한 비즈니스 로직을 모두 포함하는 **풍부한 도메인 모델(Rich Domain Model)**을 만드는 것을 목표로 합니다. 이는 도메인 객체가 단순히 getter와 setter를 가진 데이터 컨테이너에 불과하고 비즈니스 로직이 서비스 클래스에 배치되는 **빈약한 도메인 모델(Anemic Domain Model)**과는 대조됩니다.

이러한 접근 방식은 도메인 로직이 외부 의존성으로부터 분리되어 있어, 단위 테스트를 작성하기 매우 용이하게 만듭니다. 외부 의존성이 없으므로, 도메인 모델의 비즈니스 로직을 테스트할 때 별도의 Mocking이나 복잡한 환경 설정 없이 순수하게 단위 테스트를 수행할 수 있습니다.

예를 들어, `Member` 클래스는 회원에 대한 정보뿐만 아니라 비밀번호 변경 또는 계정 비활성화와 같이 회원이 수행할 수 있는 작업에 대한 로직도 포함해야 합니다.

**빈약한 도메인 모델 (피해야 할 것)**
```kotlin
// 빈약한 Member 클래스: 데이터만 가짐
class Member(val id: Long, var status: MemberStatus)

// 도메인 객체 외부에 있는 로직: 비즈니스 로직이 서비스에 분산됨
class MemberService {
  fun deactivate(member: Member) {
    // ... 비즈니스 로직
    member.status = MemberStatus.DEACTIVATED
  }
}
```

**풍부한 도메인 모델 (권장)**
```kotlin
// 풍부한 Member 클래스: 데이터와 비즈니스 로직을 함께 가짐
class Member(val id: Long, var status: MemberStatus) {
  fun deactivate() {
    // ... 검증 및 도메인 로직: 객체 스스로 상태 변경 로직을 가짐
    this.status = MemberStatus.DEACTIVATED
  }
}
```

#### Setter 사용 지양
도메인 객체의 무결성과 캡슐화를 유지하기 위해 public setter 사용을 지양해야 합니다. 객체의 상태를 외부에서 직접 수정하는 대신, 비즈니스 액션을 나타내는 함수를 사용해야 합니다.

예를 들어, `member.setStatus(DEACTIVATED)` 대신 `member.deactivate()`와 같은 함수를 사용해야 합니다. 이 접근 방식은 유효성 검사 및 기타 비즈니스 규칙을 도메인 객체 자체에 포함시켜 객체가 항상 일관된 상태를 유지하도록 보장합니다.

#### 유즈케이스 중심의 액션
애플리케이션 계층에서는 함수 이름을 해당 함수가 나타내는 비즈니스 유즈케이스에 따라 명명하도록 노력해야 합니다. 이렇게 하면 개발자와 도메인 전문가 모두에게 코드를 더 읽기 쉽고 이해하기 쉽게 만들 수 있습니다.

예를 들어, 일반적인 `updateMember` 함수 대신 `changeMemberPassword` 또는 `updateMemberProfile`과 같이 더 구체적인 함수를 가질 수 있습니다. 이 접근 방식은 코드의 의도를 명확히 하고 시스템의 동작을 더 쉽게 추론할 수 있도록 돕습니다.

유즈케이스가 여러 단계를 포함하는 경우, 이를 더 작고 `private`한 함수로 분할하는 것이 좋습니다. 이렇게 하면 가독성이 향상되고 복잡성을 관리하는 데 도움이 됩니다. 각 `private` 함수는 유즈케이스의 특정 단계에 해당해야 합니다. 이는 DDD에서 강조하는 '유비쿼터스 언어'를 코드에 반영하고, 비즈니스 의도를 명확히 드러내는 데 기여합니다.

#### 객체 생성 컨벤션

객체 생성 시에는 생성자의 역할을 명확히 하고, 다양한 생성 시나리오에 대응하기 위해 정적 팩토리 함수(Static Factory Method)를 적극적으로 활용합니다.

-   **주 생성자(Primary Constructor)**: 객체의 핵심 필드에 직접적으로 대응하는 생성자로 사용합니다. 이는 객체의 기본적인 상태를 초기화하는 데 집중합니다.
-   **정적 팩토리 함수 (`companion object`)**: 주 생성자 외에 다른 객체를 받거나, 여러 파라미터를 조합하여 객체를 생성해야 하는 경우, `companion object` 내부에 정적 팩토리 함수를 정의하여 사용합니다. 이를 통해 생성의 의도를 명확히 드러내고, 유연한 객체 생성을 가능하게 합니다.

- 일반적인 정적 팩토리 함수 네이밍
  - **단일 파라미터**: 다른 단일 객체로부터 변환하여 생성하는 경우, 일반적으로 `from` 접두사를 사용합니다.
      ```kotlin
      // 예시: Member 도메인 객체로부터 MemberResponse DTO 생성
      data class MemberResponse(...) {
          companion object {
              fun from(member: Member): MemberResponse {
                  return MemberResponse(
                      id = member.id,
                      email = member.email,
                      status = member.status,
                  )
              }
          }
      }
      ```
  - **다수 파라미터**: 여러 파라미터를 조합하여 객체를 생성하는 경우, 일반적으로 `of` 접두사를 사용합니다.
      ```kotlin
      // 예시: 여러 파라미터로부터 MemberRegisterCommand 생성
      data class MemberRegisterCommand(...) {
          companion object {
              fun of(email: String, password: String, name: String): MemberRegisterCommand {
                  return MemberRegisterCommand(
                      email = Email(email),
                      password = Password(password),
                      name = name,
                  )
              }
          }
      }
      ```

## 3. 실행 및 테스트

### 3.1. 실행 방법

1.  **요구사항**: Java 21, Docker
2.  **데이터베이스 실행**: `docker-compose up -d`
3.  **API 서버 실행**: `./gradlew :modules:boot-api:bootRun`
4.  **API 서버 빌드**: `./gradlew :modules:boot-api:bootJar`

### 3.2. 테스트 전략

- **전체 테스트 실행**: `./gradlew test`
- **전체 통합 테스트 실행**: `./gradlew integrationTest`
- **특정 모듈 테스트 실행**: `./gradlew :modules:member:core:member-domain:test`

본 프로젝트는 각 레이어의 특성에 맞는 테스트 전략을 사용합니다.

> - `integrationTest`: 통합 테스트를 위해 다른 모듈을 의존할 수 있는 테스트 영역
> - `test`: 해당 모듈만을 위한 단위 테스트 영역 

- **Domain Layer (`*-domain`)**: 외부 의존성이 없는 순수한 단위 테스트를 지향합니다. Kotest와 MockK를 사용하여 비즈니스 로직을 검증합니다.
- **Application Layer (`*-application`)**: Mockk를 사용하여 단위 테스트를 지향합니다. 필요하다면 `@SpringBootTest`를 사용하여 실제 Spring Context를 로드하고, Service 레이어의 통합 테스트를 진행합니다.
- **Adapter Layer (`*-api`)**
  - `test`: `@WebMvcTest`와 `MockMvcTester`를 사용하여 Controller 계층을 테스트합니다. 이를 통해 HTTP 요청과 응답을 검증합니다.
  - `integrationTest`: `@SpringBootTest`와 `MockMvcTester`를 사용하여 해당 모듈의 컨텍스트를 로드하고, 실제 HTTP 요청을 통해 API의 동작을 검증합니다.
- **`testFixtures` 활용**: 여러 테스트에서 공통으로 사용되는 테스트용 객체나 설정은 `src/testFixtures`에 정의하여 코드 중복을 최소화하고 테스트 코드의 재사용성을 높입니다. (예: `TestPasswordEncoder`, `TestTokenGenerator`)

## 4. 기술 스택

- **Language**: Kotlin 1.9.25, Java 21
- **Framework**: Spring Boot 3.5.3
- **Build Tool**: Gradle
- **Dev Tool**: Docker Compose, Spring Modulith
- **Database**: MySQL, H2 (for test)
- **ORM**: Spring Data JPA (Hibernate)
- **Testing**: Kotest, MockK
- **API Docs**: SpringDoc (OpenAPI 3.0)
- **Security**: Spring Security, JWT

## 5. 에이전트 상호작용 규칙 (Agent Interaction Rules)

이 문서는 Readful 프로젝트의 개발을 돕는 에이전트가 반드시 준수해야 할 규칙을 명시합니다.

- **한국어 답변**: 모든 답변은 반드시 한국어로만 제공되어야 합니다.
- **아키텍처 준수**: 코드를 작성하거나 수정할 때, Readful 프로젝트의 헥사고날 아키텍처 및 모듈 간 의존성 규칙을 **절대 위반해서는 안 됩니다.** 기존 아키텍처를 철저히 분석하고 이해한 후 변경을 제안해야 합니다.
- **선 제안 후 구현**: 코드 변경이나 파일 생성 등 실제 작업을 수행하기 전에, 반드시 변경 사항에 대한 계획을 사용자에게 먼저 제안하고 승인을 받은 후에 진행해야 합니다. 이는 사용자의 의도를 명확히 확인하고 불필요한 변경을 방지하기 위함입니다.
- **기존 코드 삭제 금지**: 기존 코드를 삭제하거나 주석 처리하는 것은 금지되어 있습니다. 필요하다면 변경 사항은 항상 새로운 코드로 추가하거나 수정하는 방식으로 진행해야 합니다.
