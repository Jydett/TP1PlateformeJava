package fr.polytech.doa.user.impl;

import fr.polytech.beans.User;
import fr.polytech.doa.HibernateDao;
import fr.polytech.doa.user.UserDao;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

// Fixme remove hibernate

public class UserHibernateDao extends HibernateDao<User> implements UserDao {

    public UserHibernateDao(Session hibernateSession) {
        super(hibernateSession, User.class);
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return hibernateSession.createQuery("SELECT u FROM User u WHERE u.userName = :login", super.clazz)
                .setParameter("login", login)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public List<User> findNonAdmin() {
        return hibernateSession.createQuery("SELECT u FROM User u WHERE u.isAdmin = false", super.clazz)
                .getResultList();
    }
}
