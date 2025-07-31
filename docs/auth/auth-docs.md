## 인증 서비스
인증 서비스는 사용자의 인증 및 권한 부여를 관리하는 시스템입니다. 
보안 관련 기능은 Spring Security를 사용하며 Oauth2를 사용하여 외부 인증 제공자와 통합할 수 있습니다.
인증 도메인에서는 Spring Security에 위임한 책임을 제외하고, 사용자 관리, 권한 관리, 인증 토큰 발급 등의 기능을 제공합니다.

인증 서비스는 다음과 같은 기능을 제공합니다:
- 사용자 조회/등록 : 외부 인증 제공자(Google, GitHub)로부터 제공 받은 사용자 정보를 기반으로 사용자를 조회 또는 등록(존재하지 않는 경우)합니다. 
- 토근 발급 및 검증: JWT(Json Web Token) 형식의 Access Token, Refresh Token을 사용하여 인증 토큰을 발급하고 검증하는 기능

### 전체 인증 로직(Oauth2 부분은 단순화)
```mermaid
sequenceDiagram
    participant User as 사용자
    participant Oauth2Provider as Oauth2 제공자
    participant SpringSecurity as 스프링 시큐리티
    participant AuthService as 인증 서비스
    participant MemberService as 회원 서비스
    participant Spring as 스프링컨텍스트 

    User->>Oauth2Provider: 인증 요청
    Oauth2Provider->>User: 인증 코드 발급
    User->>SpringSecurity: 인증 코드 전달
    SpringSecurity->>Oauth2Provider: 인증 코드 검증 요청
    Oauth2Provider->>SpringSecurity: 인증 코드 검증 결과
    SpringSecurity->>AuthService: 사용자 정보 요청
    AuthService->>MemberService: 사용자 조회 요청
    alt 사용자 존재
        MemberService->>AuthService: 사용자 정보 반환
    else 사용자 없음
        MemberService->>AuthService: 사용자 존재하지 않음
        AuthService->>MemberService: 사용자 생성 요청
        MemberService ->> Spring : 사용자 생성 이벤트 발행
        MemberService->>AuthService: 생성된 사용자 정보 반환
        AuthService ->> AuthService: 사용자 정보 저장
    end
    AuthService->>SpringSecurity: 사용자 정보 반환
    SpringSecurity->>AuthService: 인증 토큰 발급 요청
    AuthService->>SpringSecurity: 인증 토큰 발급
    SpringSecurity->>User: 인증 토큰 반환
```