package fr.polytech.doa.jdbc;

import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BasicConnectionPool {

    private static final int INITIAL_POOL_SIZE = 10;

    private final List<Connection> connectionPool;
    public static BasicConnectionPool INSTANCE;

    public static void create(String jdbc_driver, String jdbc_url, String jdbc_login, String jdbc_password) throws SQLException {
        try {
            Class.forName(jdbc_driver);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection(jdbc_url, jdbc_login, jdbc_password));
        }
        INSTANCE =  new BasicConnectionPool(pool);
    }

    public Connection getConnection() {
        return connectionPool.remove(connectionPool.size() - 1);
    }

    public void releaseConnection(Connection connection) {
        connectionPool.add(connection);
    }

    private static Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

}