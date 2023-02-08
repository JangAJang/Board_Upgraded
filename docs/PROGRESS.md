# 구현 기능 목록
## Cross Origin Resource Sharing 구현(~12.27.2022)
### - CorsConfig : 모든 헤더, 메서드, 출처의 외부 ip를 허용하며, "/api/**"로 api뒤에 어떤 주소로든 외부 ip를 등록할 수 있다.

## Swagger UI 구현(~12.27.2022)
### - SwaggerConfig : 스웨거 ui를 통한 api를 사용하기 위해 설정
- api : 아래의 키, 시큐리티 콘텍스트, api 정보를 통해 스웨거 ui의 api를 설정해준다.
- apiInfo : 해당 api에 대한 전체적인 설명을 정리해둔다.
- apiKey : 인증방식의 키를 설정해준다.
- securityContext : 보안 참조값을 defaultAuth로 받아 /api로 시작하는 주소를 swagger ui에서 모은다.
- defaultAuth : 기본적인 Authorization 방식을 설정해준다.

## Spring Security(12.28.2022~)
### -SecurityConfig
- filterChain : 
  - 모든 요청에 대해 security가 동작해 인증을 처리해준다(스웨거 동작 확인)
- webSecurityCustomizer : 등록한 url에 대해 시큐리티 검증을 무시해준다. 

### -SecurityUtil
- getCurrentMemberId : 
  - 사용자의 인증 정보가 없을 때, 인증 정보의 아이디가 없을 때 예외처리한다. 
  - 문제 없을 떄 아이디를 반환한다.

## jwt 구현(~1.4.2023)
### TokenProvider
- 생성자 : 암호키를 가지고 키를 생성하는 기능 구현
- generateTokenDto : 인증정보와 키를 이용해 TokenDto의 생성자로 토큰을 만드는 기능 구현
- parseClaim : 문자열 토큰과 키를 가지고 Jwt Claim으로 파싱해주는 기능 구현
- validateToken : 토큰의 상태를 보고, 오류/만료/지원 여부를 확인하고 이상 없으면 참을 반환하는 기능 구현
- getAuthentication : 토큰을 통해 인증 정보를 가져오는 기능 구현
### JwtAccessDenialHandler
- handle : 필요 권한이 없을 때 403에러를 반환하는 기능 구현

### JwtAuthenticationEntryPoint
- commence : 자격증명 없이 자격이 필요한 요청을 할 때 401에러를 반환하는 기능 구현

### JwtFilter
- doFilterInternal : resolveToken을 받아와 토큰이 사용가능한 상태일 때 Authentication을 설정하고 필터체인에서 필터링을 하는 기능 구현
  - resolveToken : Http요청을 받을 때 토큰의 헤더를 제외한 내용부분을 받아오는 기능 구현

### JwtSecurityConfig
- filterChain : 토큰 생성자를 이용해 JwtFilter를 만들고 필터체인 동작 전 필터를 추가하는 기능 구현

## Response 구현(~1.1.2023)
### Response
- success : 추가적인 데이터 없이, 요청이 성공적으로 수행되었음을 의미한다. 
- <T> success : 요청이 성공했으며, 요청에 대한 데이터를 반환한다. 
- failure : 요청이 실패했으며, 실패에 대한 에러 코드와 실패했음을 알리는 문자열을 반환한다. 

### Success<T>
- 성공에 대한 데이터 T를 가지며, null값이 있는 필드는 데이터로 가지고 가지 않는다. 

### Failure
- 실패 문구를 가진다. 

## Member 기능 구현(미완)
### domain
- 회원가입 기능
- 닉네임 변경
- 이메일 변경
- 비밀번호 변경

### Repository
- 전체 조회(리스트) <- 후에 페이징 처리 추가 예정
- 아이디, 닉네임, 이메일로 검색 기능 구현
- 생성, 삭제 기본적으로 추가되어 있음

### Service
- 회원가입
- 닉네임 변경
- 이메일 변경
- 비밀번호 변경
- 멤버 검색(페이징 처리 예정)

## RefreshToken 기능 구현(미완)

## Board 기능 구현(미완)

## Category 기능 구현(미완)

## Comment 기능 구현(미완)

## Message 기능 구현(미완)

### TokenDto 기능 구현(~1.3.2023)
- 생성자에 Key와 Authentication을 가져와 억세스토큰과 리프레쉬토큰을 생성하는 기능 구현
