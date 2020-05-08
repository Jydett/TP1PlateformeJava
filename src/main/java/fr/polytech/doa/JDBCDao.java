package fr.polytech.doa;

import fr.polytech.beans.User;
import fr.polytech.doa.jdbc.BasicConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class JDBCDao<I, T extends Identifiable<I>> {
    protected final BasicConnectionPool pool = BasicConnectionPool.INSTANCE;
    protected final String tableName;

    private final Function<ResultSet, T> resultSetTransformer;
    private final BiFunction<Connection, T, PreparedStatement> saveStatement;

    public JDBCDao(Class<T> clazz,
                       Function<ResultSet, T> resultSetTransformer,
                       BiFunction<Connection, T,PreparedStatement> saveStatement
                   ) {
        tableName = clazz.getName().substring(clazz.getName().lastIndexOf('.'));
        this.resultSetTransformer = resultSetTransformer;
        this.saveStatement = saveStatement;
    }

    protected Optional<T> findOne(String sql) throws SQLException {
        Connection connection = pool.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        if (resultSet.next()) {
            return Optional.of(resultSetTransformer.apply(resultSet));
        }
        pool.releaseConnection(connection);
        return Optional.empty();
    }

    public List<T> findAll() throws SQLException {
        List<T> res = new ArrayList<>();
        Connection connection = pool.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * from " + tableName);
        while (resultSet.next()) {
            res.add(resultSetTransformer.apply(resultSet));
        }
        pool.releaseConnection(connection);
        return res;
    }

    public Optional<T> findOneByID(I id) throws SQLException {
        return findOne("SELECT * from " + tableName + " where id=" + id);
    }

    public boolean delete(T toDelete) throws SQLException {
        Connection connection = pool.getConnection();
        boolean res = connection.createStatement().executeUpdate("DELETE FROM " + tableName + " WHERE id='" + toDelete.getId() + "'") != 0;
        pool.releaseConnection(connection);
        return res;
    }

    public void save(T toSave) throws SQLException {
        Connection connection = pool.getConnection();
        PreparedStatement stmt = saveStatement.apply(connection, toSave);
        stmt.execute();
        ResultSet resultSet = stmt.getGeneratedKeys();
        if (resultSet.next()) {
            ((User) toSave).setId(resultSet.getLong(1));
        }
        pool.releaseConnection(connection);
    }

    public List<T> find(String query) throws SQLException {
        List<T> res = new ArrayList<>();
        Connection connection = pool.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery(query);
        while (resultSet.next()) {
            res.add(resultSetTransformer.apply(resultSet));
        }
        pool.releaseConnection(connection);
        return res;
    }
}
