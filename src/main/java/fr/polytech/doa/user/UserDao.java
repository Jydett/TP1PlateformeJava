package fr.polytech.doa.user;

import fr.polytech.beans.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void save(User user);
    Optional<User> findUserByLogin(String login);
    void remove(User user);
    List<User> findNonAdmin();

}
