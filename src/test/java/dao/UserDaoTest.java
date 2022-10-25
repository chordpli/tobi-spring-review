package dao;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ConnectionMaker connectionMaker = new LikelionConnectionMaker();

        UserDao userDao = new UserDao(connectionMaker);
    }

}