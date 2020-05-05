package fr.polytech.doa;

import fr.polytech.doa.jdbc.BasicConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class JDBCDao<I, T extends Identifiable<I>> {
    protected final BasicConnectionPool pool = BasicConnectionPool.INSTANCE;
    protected final String tableName;

    protected final Function<ResultSet, T> resultSetTransformer;
    protected final Function<Connection, PreparedStatement> createStatement;
    private Function<Connection, PreparedStatement> saveStatement;

    public JDBCDao(Class<T> clazz,
                       Function<ResultSet, T> resultSetTransformer,
                       Function<Connection, PreparedStatement> createStatement,
                       Function<Connection, PreparedStatement> saveStatement
                   ) {
        tableName = clazz.getName().substring(clazz.getName().lastIndexOf('.'));
        this.resultSetTransformer = resultSetTransformer;
        this.createStatement = createStatement;
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
        return findOne("SELECT from " + tableName + " where id=" + id);
    }

    public boolean delete(T toDelete) throws SQLException {
        Connection connection = pool.getConnection();
        boolean res = connection.createStatement().executeUpdate("DELETE FROM " + tableName + " WHERE id='" + toDelete.getId() + "'") != 0;
        pool.releaseConnection(connection);
        return res;
    }

    public boolean save(T toSave) throws SQLException {
        Connection connection = pool.getConnection();
        boolean res = saveStatement.apply(connection).executeUpdate() != 0;
        pool.releaseConnection(connection);
        return res;
    }
}
