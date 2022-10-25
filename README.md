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

# UserDao

## JDBC 순서
- DB 연결을 위한 Connection 을 가져온다.
- SQL을 담은 Statement 또는 PreparedStatement를 만든다.
- 만들어진 atement 를 실행한다.
- 조회의 경우 SQL 쿼리의 실행 결과를 ResultSet으로 받아서 
정보를 저장할 오브젝트(여기서는 user)에 옮겨준다.
- 작업 중에 생성된 Connection, Statement, ResultSet 같은 리소스는 
작업을 마친 후 반드시 닫아준다.
- JDBC API가 만들어내는 예외(exception)를 잡아서 직접 처리하거나, 
메소드에 throws를 선언해서 예외가 발생하면 메소드 밖으로 던지게 한다.
