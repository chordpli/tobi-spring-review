package dao;

public class DaoFactory {
    public UserDao userDao() {
        return new UserDao(new LikelionConnectionMaker());
    }

    /*분리해서 중복을 제거한 ConnectionMaker타입 오브젝트 생성 코드*/
    public ConnectionMaker connectionMaker(){
        return new LikelionConnectionMaker();
    }
}
