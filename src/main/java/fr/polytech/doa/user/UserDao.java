package fr.polytech.doa.user;

import fr.polytech.beans.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    void save(User user) throws SQLException;
    Optional<User> findUserByLogin(String login) throws SQLException;
    void remove(User user) throws SQLException;
    List<User> findNonAdmin() throws SQLException;
    Optional<User> findOneByID(Long id) throws SQLException;
}
