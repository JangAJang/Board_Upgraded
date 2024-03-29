# 도메인 로직
## Member(~1.11.2023)
### 회원가입(Register)
-  회원가입이 진행된다. 
-  비밀번호가 서로 일치하지 않아 예외처리한다.
-  이메일이 형식에 맞지 않아 예외처리한다.

### 인스턴스 변경(Change)
-  닉네임 변경에 대한 테스트 추가
  - 닉네임이 정상적으로 변경된다.
  - 닉네임이 기존과 동일해 예외처리한다.
-  이메일 변경에 대한 테스트 추가
  - 이메일이 정상적으로 변경된다. 
  - 이메일 형식에 맞지 않아 예외처리한다. 
  - 이메일이 기존과 같아 예외처리한다. 
-  비밀번호 변경에 대한 테스트 추가
  - 비밀번호 두번 입력이 서로 같지 않아 예외처리한다. 
  - 비밀번호가 기존과 같아 예외처리한다. 
  - 비밀번호가 정상적으로 변경된다. 

# Repository
## Member
### 검색
- SearchMemberDto로 검색을 수행한다. 
- null값이 존재할 경우, null이지 않은 값을 가지고 검색을 수행한다. 
- 만약 전부 null값일 경우 예외처리한다. 
- 검색 결과가 없는 경우, 빈 List를 반환한다. 

# Service
## Member
### 회원가입
- RegisterRequestDto로 입력을 받는다. 
- 아이디, 닉네임, 이메일 중복이 존재하면 예외처리한다. 
- 비밀번호가 서로 다르면 예외처리한다. 
- member객체로 생성해 repository.save해준다. 

### 정보 수정
- 닉네임 변경 : 닉네임이 중복되지 않으면 저장한다. 
- 이메일 변경 : 이메일이 중복되지 않으면 저장한다. 
- 비밀번호 변경 : 비밀번호가 기존과 다르고 입력이 서로 같으면 저장한다. 최종 수정일자를 초기화시킨다. 

### 검색
- SearchMemberDto로 입력을 받는다. 
- 전부 null값이면 예외처리한다. 
- 하나라도 null값이 아니면 결과를 리스트로 반환한다. 