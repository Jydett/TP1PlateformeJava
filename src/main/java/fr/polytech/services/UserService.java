package fr.polytech.services;

import fr.polytech.beans.User;
import fr.polytech.doa.user.UserDao;
import fr.polytech.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void deleteUser(User user) {
        userDao.remove(user);
    }

    public User authentification(String login, String password) {
        Optional<User> optionalUser = userDao.findUserByLogin(login);
        if (! optionalUser.isPresent()) {
            throw new ServiceException("Erreur d'authentification.");
        }
        User user = optionalUser.get();
        if (! user.getPassword().equals(password)) {
            throw new ServiceException("Erreur d'authentification.");
        }
        return user;
    }

    public List<User> allNonAdminUsers() {
        return userDao.findNonAdmin();
    }
}
