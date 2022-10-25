>멋쟁이 사자처럼 백엔드 스쿨 2기<br>
토비의 스프링 1-3장 복습 기록

# User
<details>
<summary> code </summary>

```java
public class User {
    private String id;
    private String name;
    private String password;

    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
```

</details>

# 1.1 초난감 DAO

<details>
<summary> 더보기 </summary>

## JDBC 순서
- DB 연결을 위한 Connection 을 가져온다.
- SQL을 담은 Statement 또는 PreparedStatement를 만든다.
- 만들어진 Statement 를 실행한다.
- 조회의 경우 SQL 쿼리의 실행 결과를 ResultSet으로 받아서 
정보를 저장할 오브젝트(여기서는 user)에 옮겨준다.
- 작업 중에 생성된 Connection, Statement, ResultSet 같은 리소스는 
작업을 마친 후 반드시 닫아준다.
- JDBC API가 만들어내는 예외(exception)를 잡아서 직접 처리하거나, 
메소드에 throws를 선언해서 예외가 발생하면 메소드 밖으로 던지게 한다.


## Class.ForName 수정사항
- "com.mysql.jdbc.Driver" - > "com.mysql.cj.jdbc.Driver"
</details>

# 1.2 DAO의 분리

<details>
<summary> 더보기 </summary>

## UserDao의 관심사항
- DB와 연결을 위한 커넥션을 어떻게 가져올까?
- 사용자 등록을 위해 DB에 보낼 SQL 문장을 담을 Statement를 만들고 실행하는 것
- 작업이 끝나면 사용한 리소스인 Statement와 Connection 오브젝트를 닫아줘서 소중한 공유 리소스를 시스템에 돌려주는 것


## Commit
#### [COMMIT!] 중복 코드의 메소드 추출
#### [COMMIT!] DB 커넥션 만들기의 독립
  - [COMMIT!] 상속을 위한 확장
    - 상속이 문제가 된다.
    - 자바는 다중 상속을 허용하지 않음.
    - 상하위 클래스의 관계가 밀접
</details>

# 1.3 DAO의 확장

<details>
<summary> 더보기 </summary>

## Commit
#### [COMMIT!] 클래스의 분리
- SimpleConnectionMaker 클래스 생성
  - 상속을 이용한 방식을 사용하지 않으니 추상 클래스로 만들지 않는다.
- UserDao가 SimpleConnectionMaker에 종속되어 자유로운 확장이 불가
#### [COMMIT!] 인터페이스 도입
- 초기에 한 번 어떤 클래스의 오브젝트를 사용할지를 결정하는 생성자의 코드는 제거되지 않고 남아있다.
- 다시 원점, 자유로운 DB 커넥션 확장 기능을 가진 UserDao를 제공할 수 없다.
#### [COMMIT!] 관계 설정 책임의 분리
- 클래스 사이의 관계는 코드에 다른 클래스 이름이 나타나기 때문에 만들어지는 것.
- 하지만 오브젝트 사이의 관계는 그렇지 않다.
- 코드에서는 특정 클래스를 전혀 알지 못하더라도 해당 클래스가 구현한 인터페이스를 사용했다면, 그 클래스의 오브젝트를 인터페이스 타입으로 받아서 사용할 수 있다.
</details>

# 1.4 제어의 역전(IoC)
<details>
<summary> 더보기 </summary>

## Commit
#### [COMMIT!] 오브젝트 팩토리
- [COMMIT!] 팩토리
  - 객체의 생성 방법을 결정하고 만들어진 오브젝트를 돌려주는 것.
  - UserDaoTest는 이제 UserDao가 어떻게 만들어지는지, 초기화되는지 신경쓰지 않고 팩토리로 UserDao 오브젝트를 받아, 자기 관심사인 테스트를 위해 활용하면 된다.
#### [COMMIT!] 오브젝트 팩토리의 활용
- 어떤 ConnectionMaker를 구현 클래스를 사용할지를 결정하는 기능이 중복.
- 중복 문제를 해결하기 위해서는 분리하는 방법이 가장 좋다.
#### [COMMIT!] 제어권 이전을 통한 제어관계 역전
- 제어의 역전, 간단히 프로그램의 제어 흐름 구조가 뒤바뀌는 것
- 오브젝트가 자신이 사용할 오브젝트를 스스로 선택하지 않는다. 당연히 생성하지도 않는다.
</details>

# 1.5 스프링의 IoC
<details>
<summary> 더보기 </summary>

## Commit
#### [COMMIT!] 오브젝트 팩토리를 이용한 스프링 IoC
- [COMMIT!] 애플리케이션 컨텍스트와 설정정보
    - 빈
      - 스프링이 제어권을 가지고 직접 만들고 관계를 부여하는 오브젝트 빈(Bean)
      - 오브젝트 단위의 애플리케이션 컴포넌트
      - 스프링 컨테이너가 생성과 관계설정, 사용 등을 제어해주는 제어의 역전이 적용된 오브젝트
    - 빈의 생성과 관계설정 같은 제어를 담당하는 IoC 오브젝트를 빈 팩토리라고 부른다.
    - 보통 빈 팩토리보다는 이를 좀 더 확장한 애플리케이션 컨텍스트를 주로 사용
- [COMMIT!] DaoFactory를 사용하는 애플리케이션 컨텍스트

#### [COMMIT!] 애플리케이션 컨텍스트의 동작 방식
- 클라이언트는 구체적인 팩토리 클래스를 알 필요가 없다.
  - 애플리케이션 컨텍스트를 이용하면 일관된 방식으로 원하는 오브젝ㅌ르르 가져올 수 있따.
- 애플리케이션 컨텍스트는 종합 IoC 서비스를 제공
- 애플리케이션 컨텍스트는 빈을 검색하는 다양한 방법 제공

</details>

# 1.6 싱글톤 레지스트리와 오브젝트 스코프
<details>
<summary> 더보기 </summary>

## Commit
#### [COMMIT!] 싱글톤 레지스트리로서의 애플리케이션 컨텍스트
- 애플리케이션 컨텍스트는 싱글톤을 저장하고 관리하는 싱글톤 레지스트리기도 하다.
- 
- [COMMIT!] 서버 애플리케이션과 싱글톤
  - 서블릿 클래스당 하나의 오브젝트만 만들어주고, 사용자의 요청을 담당하는 여러 스레드에서 하나의 오브젝트를 공유해 동시 사용.
  - 애플리케이션 안에 제한된 수, 대개 한 개의 오브젝트만 만들어서 사용하는것이 싱글톤 패턴의 원리.
- [COMMIT!] 싱글톤 패턴의 한계

</details>

# 1.8 XML을 이용한 설정
<details>
<summary> 더보기 </summary>

## Commit
#### [COMMIT!] DataSource 인터페이스로 변환
- [COMMIT!] DataSource 인터페이스 적용
- [COMMIT!] 자바 코드 설정 방식

</details>

# 2.3.2 테스트 결과의 일관성
<details>
<summary> 더보기 </summary>

## Commit
#### [COMMIT!] DeleteAll()의 getCount() 추가
- [COMMIT!] deleteAll()
  - USER 테이블의 모든 레코드를 삭제
- [COMMIT!] getCount()
    - USER 테이블의 레코드 갯수를 반환
- [COMMIT!] DeleteAll()과 getCount()의 테스트
#### [COMMIT!] DeleteAll()과 getCount()의 테스트
</details>

# 2.3.3 포괄적인 테스트
<details>
<summary> 더보기 </summary>

## Commit
#### [COMMIT!] getCount()테스트
#### [COMMIT!] addAndGet() 테스트 보완

</details>