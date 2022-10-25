package dao;

public class DaoFactory {
    public UserDao userDao(){
        ConnectionMaker connectionMaker = new LikelionConnectionMaker();
        UserDao userDao = new UserDao(connectionMaker);
        return userDao;
    }
}
