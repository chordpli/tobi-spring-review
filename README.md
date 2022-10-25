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
</details>