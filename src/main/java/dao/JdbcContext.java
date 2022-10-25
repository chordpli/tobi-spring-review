package dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = this.dataSource.getConnection();
            ps = stmt.makePreparedStatement(c);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
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

    public void executeSql(final String query) throws SQLException {
        workWithStatementStrategy(
                new StatementStrategy() { // 변하지 않는 콜백 클래스 정의와 오브젝트 생성
                    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                        return c.prepareStatement(query);
                    }
                }
        );
    }

}
