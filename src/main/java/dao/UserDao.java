package dao;

import com.mysql.cj.protocol.Resultset;
import domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    private ConnectionMaker connectionMaker;

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public UserDao() {
    }

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws SQLException, ClassNotFoundException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement(
                "insert into users(id, name, password) values (?,?,?)"
        );

        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement(
                "select * from users where id =?"
        );
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();

        // 테스트를 성공시키기 위한 코드의 수정
        // id를 조건으로 한 쿼리의 결과가 있으면 User 오브젝트를 만들고 값을 넣어준다.
        User user = null;

        if (rs.next()) {
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        rs.close();
        ps.close();
        c.close();

        // 결과가 없으면 User는 null 상태 그대로, 이를 확인해서 예외를 던져준다.
        if (user == null) throw new EmptyResultDataAccessException(1);
        return user;
    }

    public void deleteAll() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement("delete from users");
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
            }

            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
            }
        }
    }

    public int getCount() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement("select count(*) from users");

            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
            }
        }
    }

}