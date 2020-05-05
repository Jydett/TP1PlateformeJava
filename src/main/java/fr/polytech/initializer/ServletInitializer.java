package fr.polytech.initializer;

import fr.polytech.doa.user.impl.UserHibernateDao;
import fr.polytech.services.UserService;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public final class ServletInitializer implements ServletContextListener {
    static {
        USER_SERVICE = new UserService(new UserHibernateDao(new Configuration().configure().buildSessionFactory().openSession()));
    }

    public static final UserService USER_SERVICE;
}
