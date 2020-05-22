package fr.polytech.initializer;

import fr.polytech.doa.jdbc.BasicConnectionPool;
import fr.polytech.doa.user.UserDao;
import fr.polytech.doa.user.impl.JDBCUserDao;
import fr.polytech.services.UserService;
import lombok.Getter;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public final class ServletInitializer implements ServletContextListener {

    @Getter
    private static UserService userService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        try {
            BasicConnectionPool.create(
                    context.getInitParameter("JDBC_DRIVER")
                    , context.getInitParameter("JDBC_URL")
                    , context.getInitParameter("JDBC_LOGIN")
                    , context.getInitParameter("JDBC_PASSWORD"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        UserDao userDao = new JDBCUserDao();
        userService = new UserService(userDao);
    }
}
