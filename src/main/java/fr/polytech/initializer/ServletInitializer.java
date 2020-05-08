package fr.polytech.initializer;

import fr.polytech.doa.jdbc.BasicConnectionPool;
import fr.polytech.doa.user.UserDao;
import fr.polytech.doa.user.impl.JDBCUserDao;
import fr.polytech.services.UserService;

import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public final class ServletInitializer implements ServletContextListener {
    static {
        try {
            BasicConnectionPool.create(3308,"localhost", "jeetp", "root","");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //UserDao userDao = new UserHibernateDao(new Configuration().configure().buildSessionFactory().openSession());
        UserDao userDao = new JDBCUserDao();
        USER_SERVICE = new UserService(userDao);
    }

    public static final UserService USER_SERVICE;
}
