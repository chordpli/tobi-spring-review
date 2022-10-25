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

# 2.3 개발자를 위한 테스팅 프레임워크 JUnit
<details>
<summary> 더보기 </summary>

## Commit
#### [COMMIT!] 테스트 결과의 일관성
- [COMMIT!] DeleteAll()의 getCount() 추가
  - [COMMIT!] deleteAll()
    - USER 테이블의 모든 레코드를 삭제
  - [COMMIT!] getCount()
      - USER 테이블의 레코드 갯수를 반환
- [COMMIT!] DeleteAll()과 getCount()의 테스트

#### [COMMIT!] 포괄적인 테스트
- [COMMIT!] getCount()테스트
- [COMMIT!] addAndGet() 테스트 보완
  - User 하나를 더 추가해서 두 개의 User를 add()하고, 각 User의 id를 파라미터로 전달해서 get()을 실행
- [COMMIT!] get()예외조건에 대한 테스트
  - get() 메소드에 전달된 id 값에 해당하는 사용자 정보가 없을 때,
  - Junit에서 예외 조건 테스트 위한 방법 제공.
    - assertThrows(EmptyResultDataAccessException.class, ()->{ });
- [COMMIT!] 테스트를 성공시키기 위한 코드의 수정
- [COMMIT!] 포괄적인 테스트
  - 항상 네거티브 테스트를 먼저 만들어라.
</details>

# 3.1 다시 보는 초난감 DAO
<details>
<summary> 더보기 </summary>

## Commit
#### [COMMIT!] 예외처리 기능을 갖춘 DAO
- [COMMIT!] JDBC 수정 기능의 예외처리 코드
  - PreparedStatement를 처리하는 중 예외가 발생하면, 메소드 실행을 끝마치지 못하면서 Connection과 PreparedStatement의 close() 메소드가 실행되지 않아서 제대로 리소스가 반환되지 않을 수 있다.
- [COMMIT!] _JDBC 조회 기능의 예외처리 코드_
  - ResultSet 추가

</details>

# 3.2 변하는 것과 변하지 않는 것
<details>
<summary> 더보기 </summary>

## Commit
#### [COMMIT!] JDBC try/catch/finally 코드의 문제점
- 2중으로 중첩 및 모든 메소드마다 반복되는 상황
#### [COMMIT!] 분리와 재사용을 위한 디자인 패턴 적용
- [COMMIT!] 메소드 추출
  - 변하는 부분을 메소드로 빼는 것.
- [COMMIT!] 템플릿 메소드 패턴의 적용
    - 개방 폐쇄 원칙을 그럭저럭 지키는 구조
    - 하지만 템플릿 메소드 패턴으로의 접근 제한이 많다.
- [COMMIT!] 전략 패턴의 적용
  - 오브젝트를 아예 둘로 분리하고 클래스 레벨에서는 인터페이스를 통해서만 의존하도록 만드는 전략 패턴
  - 컨텍스트가 StatementStrategy 인터페이스뿐 아니라 특정 구현 클래스인 DeleteAllStatement를 직접 알고 있다는 건, 전략 패턴에도 OCP에도 잘 들어맞는다고 볼 수 없다.
- [COMMIT!] DI 적용을 위한 클라이언트/컨텍스트 분리
  - 의존관계와 책임으로 볼 때 이상적인 클라이언트/컨텍스트 관계를 갖고 있다.
  - 클라이언트가 컨텍스트가 사용할 전략을 정해서 전달하는 면에서 DI구조라고 이해할 수도 있다.
</details>

# 3.3 JDBC 전략 패턴의 최적화
<details>
<summary> 더보기 </summary>

## Commit
#### [COMMIT!] 전략 클래스의 추가 정보
- User 타입 오브젝트를 AddStatement의 생성자를 통해 제공
#### [COMMIT!] 전략과 클라이언트의 동거
- 두가지 불만
  - DAO 메소드마다 새로운 StatementStrategy 구현 클래스를 만들어야 하는 점
  - DAO 메소드에서 StatementStrategy에 전달할 User와 같은 부가적인 정보가 있는 경우, 이를 위해 오브젝트를 전달받는 생성자와 이를 저장해둘 인스턴스 변수를 번거롭게 만들어야 한다는 점.
- [COMMIT!] 로컬 클래스
  - AddStatement가 사용될 곳이 하나라면, 클래스 파일이 하나 줄고, 메소드 안에서 PreparedStatement 생성 로직을 함께 볼 수 있으니 코드를 이해하기도 좋다.
  - 내부 클래스이므로 선언된 곳의 정보에 접근할 수 있다.
- [COMMIT!] 익명 내부 클래스
</details>


# 3.4 컨텍스트와 DI
<details>
<summary> 더보기 </summary>

## Commit
#### [COMMIT!] JdbcContext의 분리
- [COMMIT!] 클래스의 분리
  - JdbcContext가 DataSource에 의존하고 있으므로 DataSource 타입빈을 DI 받을 수 있게 해줘야 한다.
- [COMMIT!] 빈 의존관계 변경
  - 스프링의 DI는 기본적으로 인터페이스를 사이에 두고 의존 클래스를 바꿔서 사용하는 게 목적
  - 이 경우 JdbcContext는 그 자체로 독립적인 JDBC 컨텍스트를 제공해주는 서비스 오브젝트로서 의미가 있을 뿐, 구현 방법이 바귈 가능성은 없다
  - 따라서 인터페이스를 구현하도록 만들지 않음. UserDao와 JdbcContext는 인터페이스를 사이에 두지 않고 DI를 적용하는 특별 구조
</details>

# 3.5 템플릿과 콜백
<details>
<summary> 더보기 </summary>
UserDao와 StatementStrategy, JdbcContext를 이용해 만든 코드는 일종의 전략 패턴이 적용 된 것.
복잡하지만 바뀌지 않는 일정한 패턴을 갖는 작업 흐름이 존재하고 그 중 일부만 자주 바꿔서 사용해야 하는 경우에 적합한 구조
전략 패턴의 기본 구조에 익명 내부 클래스를 활용한 방식이며 이런 방식을 스프링에서는 템플릿/콜백 패턴 이라고 부른다.

## Commit
#### [COMMIT!] 템플릿/콜백의 동작 원리
- [COMMIT!] 템플릿/콜백의 특징
  - 템플릿/콜백 패턴의 콜백은 보통 단일 메소드 인터페이스 사용
#### [COMMIT!] 편리한 콜백의 재활용
- DAO 메소드에서 매번 익명 내부 클래스를 사용하기 때문에 상대적으로 코드를 작성하고 읽기가 불편
- [COMMIT!] 콜백의 분리와 재활용
  - 복잡한 익명 내부 클래스인 콜백을 직접 만들 필요조차 사라짐.
- [COMMIT!] 콜백과 템플릿의 결합
  - 일반적으로는 성격이 다른 코드들은 가능한 한 분리하는 편이 낫지만, 하나의 목적을 위해 서로 긴밀하게 연관되어 동작하는 응집력이 강한 코드들이기 때문에 한 군데 모여 있는 게 유리하다.
</details>

# 3.6 스프링의 JdbcTemplate
<details>
<summary> 더보기 </summary>
스프링에서 제공하는 JdbcTemplate는 JdbcContext와 유사하지만 훨씬 강력하고 편리한 기능을 제공한다.

## Commit
#### [COMMIT!] update()
#### [COMMIT!] queryForInt() -> quryForObject()로 대체
- [COMMIT!] getCount()
#### [COMMIT!] quryForObject()
- [COMMIT!] get()
  - ResultSetExtractor 콜백 대신 RowMapper 콜백 사용
  - 둘 다 템플릿으로부터 ResultSet을 전달받고, 필요한 정보를 추출하여 리턴
  - ResultSetExtractor는 Resultset을 한 번 전달받아 알아서 추출작없을 모두 진행, 최종 결과만 리턴
  - RowMapper는 ResultSet의 로우 하나를 매핑하기 위해 사용되기 때문에 여러번 호출
#### [COMMIT!] query()
- [COMMIT!] 기능 정의와 테스트 작성
- [COMMIT!] query() 템플릿을 이용하는 getAll() 구현
#### [COMMIT!] 재사용 가능한 콜백의 분리
- [COMMIT!] DI를 위한 코드 정리
  - 필요 없어진 DataSource 인스턴스 변수 제거.
  - 단지 JdbcTemplate을 생성하면서 직접 DI 해주기 위해 필요한 DataSource를 전달받아야하므로 수정자 메소드만 남겨놓는다.
- [COMMIT!] 중복제거
- [COMMIT!] 템플릿/콜백 패턴과 UserDao
  - UserDao에는 User정보를 DB에 넣거나 가져오거나 조작하는 방법에 대한 핵심적인 로직만 담겨있다.
  만약 사용할 테이블 필드 정보가 바뀌면 UserDao의 거의 모든 코드가 함께 바뀐다. 따라서 응집도가 높다고 볼 수 있다.
  - JDBC API를 사용하는 방식, 예외처리, 리소스의 반납, DB연결을 어떻게 가져올지에 관한 책임과 관심은 모두 JdbcTemplate에게 있다. 따라서 변경이 일어난다고 해도 UserDao 코드에는 아무런 영향을 주지 않는다.
  그런 면에서 책임이 다른 코드와는 낮은 결합도를 유지하고 있다.

### 두 가지 욕심을 내고 싶은 부분
- userMapper가 인스턴스 변수로 설정되어 있고, 한 번 만들어지면 변경되지 않는 프로퍼티와 같은 성격을 띠고 있으니 아예 UserDao 빈의 DI용 프로퍼티를 만드는 것은?
- DAO 메소드에서 사용하는 SQL문장을 UserDao 코드가 아니라 외부 리소스에 담고 이를 읽어와 사용하게 하는 것.

</details>

# 3.7 정리
<details>
<summary> 더보기 </summary>

- JDBC와 같은 예외가 발생할 가능성이 있으며 공유 리소스의 반환이 필요한 코드는 반드시 try/catch/finally 블록으로 관리
- 일정한 작업 흐름이 반복되면서 일부 기능만 바뀌는 코드가 존재한다면 전략 패턴을 적용한다. 바뀌지 않는 부분은 컨텍스트로, 바뀌는 부분은 전략으로 만들고 인터페이스를 통해 유연하게 전략을 변경할 수 있도록 구성한다.
- 같은 애플리케이션 안에서 여러 가지 종류의 전략을 다이나믹하게 구성하고 사용해야한다면 컨텍스르를 이용하는 클라이언트 메소드에서 직접 전략을 정의하고 제공하게 만든다.
- 클라이언트 메소드 안에 익명 내부 클래스를 사용해서 전략 오브젝트를 구현하면 코드도 간결해지고 메소드의 정보를 직접 사용할 수 있어서 편리하다.
- 컨텍스트가 하나 이상의 클라이언트 오브젝트에서 사용된다면 클래스를 분리해서 공유하도록 만든다.
- 컨텍스트는 별도의 빈으로 등록해서 DI 받거나 클라이언트 클래스에서 지겆ㅂ 생성해서 사용한다. 클래스 내부에서 컨텍스트를 사용할 때 컨텍스트가 의존하는 외부의 오브젝트가 있다면 코드를 이용해서 직접 DI 해줄 수 있다.
- 단일 전략 메소드를 갖는 전략 패턴이면서 익명 내부 클래스를 사용해서 매번 전략을 새로 만들어 사용하고, 컨텍스트 호출과 동시에 전략 DI를 수행하는 방식을 템플릿/콜백 패턴이라고 한다.
- 콜백의 코드에도 일정한 패턴이 반복된다면 콜백을 템플릿에 넣고 재활용하는 것이 편리하다.
- 템플릿과 콜백의 타입이 다양하게 바뀔 수 있다면 제네릭스를 이용한다.
- 스프링은 JDBC 코드 작성을 위해 JdbcTemplate을 기반으로 하는 다양한 템플릿과 콜백을 제공한다.
- 템플릿은 한 번에 하나 이상의 콜백을 사용할 수도 있고, 하나의 콜백을 여러 번 호출할 수도 있다.
- 템플릿/콜백을 설계할 때는 템플릿과 콜백 사이에 주고받는 정보게 관심을 둬야 한다.
</details>