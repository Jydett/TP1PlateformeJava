package fr.polytech.doa.user.impl;

import fr.polytech.beans.User;
import fr.polytech.doa.HashMapDao;
import fr.polytech.doa.user.UserDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class HashMapUserDao extends HashMapDao<Long, User> implements UserDao {
    private final static AtomicLong id = new AtomicLong(0);

    protected HashMapUserDao() {
        super(id::incrementAndGet);
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws SQLException {
        return table.values().stream().filter(u -> Objects.equals(u.getLogin(), login)).findAny();
    }

    @Override
    public List<User> findNonAdmin() throws SQLException {
        return table.values().stream().filter(User::isAdmin).collect(Collectors.toList());
    }

    @Override
    public Optional<User> findOneByID(Long id) throws SQLException {
        return super.findOneById(id);
    }
}
