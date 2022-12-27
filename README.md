# 구현 기능 목록
## Cross Origin Resource Sharing 구현(12.27.2022)
### - CorsConfig : 모든 헤더, 메서드, 출처의 외부 ip를 허용하며, "/api/**"로 api뒤에 어떤 주소로든 외부 ip를 등록할 수 있다. 

## Swagger UI 구현(12.27.2022)
### - SwaggerConfig : 스웨거 ui를 통한 api를 사용하기 위해 설정
- api : 아래의 키, 시큐리티 콘텍스트, api 정보를 통해 스웨거 ui의 api를 설정해준다. 
- apiInfo : 해당 api에 대한 전체적인 설명을 정리해둔다. 
- apiKey : 인증방식의 키를 설정해준다. 
- securityContext : 보안 참조값을 defaultAuth로 받아 /api로 시작하는 주소를 swagger ui에서 모은다.
- defaultAuth : 기본적인 Authorization 방식을 설정해준다. 

## jwt 구현(미완)

## Member 기능 구현(미완)

## Board 기능 구현(미완)

## Category 기능 구현(미완)

## Comment 기능 구현(미완)

## Message 기능 구현(미완)
