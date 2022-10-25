package dao;

import domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;
    UserDao userDao;

    @BeforeEach
    void setUp() throws SQLException {
        this.userDao = context.getBean("localDao", UserDao.class);
        userDao.deleteAll();
    }

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        User user01 = new User("chord", "코드", "1234");
        User user02 = new User("pli", "플리", "1234");
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user01);
        userDao.add(user02);
        User userget1 = userDao.get(user01.getId());
        assertEquals(userget1.getName(), user01.getName());
        assertEquals(userget1.getPassword(), user01.getPassword());

        User userget2 = userDao.get(user02.getId());
        assertEquals(userget2.getName(), user02.getName());
        assertEquals(userget2.getPassword(), user02.getPassword());
    }

    @Test
    @DisplayName("getCount() Test")
    public void count() throws SQLException, ClassNotFoundException {
        User user01 = new User("junho", "김준호", "1234");
        User user02 = new User("pli", "플리", "1234");
        User user03 = new User("chord", "코드", "1234");

        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user01);
        assertEquals(1, userDao.getCount());

        userDao.add(user02);
        assertEquals(2, userDao.getCount());

        userDao.add(user03);
        assertEquals(3, userDao.getCount());
    }

    @Test
    public void getUserFailure() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();

        assertThrows(EmptyResultDataAccessException.class, ()->{
            userDao.get("hello");
        });
    }
}