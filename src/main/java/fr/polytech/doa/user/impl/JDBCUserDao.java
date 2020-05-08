package fr.polytech.doa.user.impl;

import fr.polytech.beans.User;
import fr.polytech.doa.JDBCDao;
import fr.polytech.doa.user.UserDao;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class JDBCUserDao extends JDBCDao<Long, User> implements UserDao {

    public JDBCUserDao() {
        super(User.class, JDBCUserDao::resultSetTransformer, JDBCUserDao::saveStatement);
    }

    public static User resultSetTransformer(ResultSet resultSet) {
        try {
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");
            boolean isAdmin = resultSet.getBoolean("isAdmin");
            Long id = resultSet.getLong("id");
            User user = new User(login, password, isAdmin);
            user.setId(id);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PreparedStatement saveStatement(Connection connection, User user) {
        String addUser = "INSERT INTO User(login, password, isAdmin, id) VALUES(?, ?, ?, ?) ON DUPLICATE KEY UPDATE login = VALUES(login), isAdmin = VALUES(isAdmin), password = VALUES(password)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(addUser, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, user.getLogin());
            pstmt.setString(2, user.getPassword());
            pstmt.setBoolean(3, user.isAdmin());
            if (user.getId() != null)
                pstmt.setLong(4, user.getId());
            else
                pstmt.setNull(4, Types.BIGINT);
            return pstmt;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws SQLException {
        return super.findOne("SELECT * FROM User WHERE login = '" + login + "'");
    }

    @Override
    public void remove(User user) throws SQLException {
        super.delete(user);
    }

    @Override
    public List<User> findNonAdmin() throws SQLException {
        return super.find("SELECT * FROM User WHERE isAdmin = 0");
    }
}