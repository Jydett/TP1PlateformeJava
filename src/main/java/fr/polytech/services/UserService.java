package fr.polytech.services;

import fr.polytech.beans.User;
import fr.polytech.doa.user.UserDao;
import fr.polytech.exceptions.ServiceException;
import fr.polytech.form.LoginForm;
import fr.polytech.form.SigInForm;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void deleteUser(Long id) {
        try {
            User user = userDao.findOneByID(id).orElseThrow(() -> new ServiceException("pas d'utilisateur avec cet id"));
            userDao.remove(user);
        } catch(SQLException e) {
            e.printStackTrace();
            throw new ServiceException("remove()");
        }
    }

    public User authentification(LoginForm loginForm) {
        try {
            Optional<User> optionalUser = userDao.findUserByLogin(loginForm.getLogin());
            if (!optionalUser.isPresent()) {
                throw new ServiceException("Erreur d'authentification.");
            }
            User user = optionalUser.get();
            if (!user.getPassword().equals(loginForm.getPassword())) {
                throw new ServiceException("Erreur d'authentification.");
            }
            return user;
        } catch(SQLException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur findUserByLogin()");
        }
    }

    public List<User> allNonAdminUsers() {
        try {
            return userDao.findNonAdmin();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur findNonAdmin()");
        }
    }

    public User createUser(SigInForm sigInForm) {
        try {
            User user = new User(sigInForm.getLogin(), sigInForm.getPassword(), sigInForm.isAdmin());
            userDao.save(user);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur createUser()");
        }
    }
}
