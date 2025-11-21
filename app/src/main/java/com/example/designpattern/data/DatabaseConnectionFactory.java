package com.example.designpattern.data;

import android.util.Log;

import com.example.designpattern.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DatabaseConnectionFactory {

    private static final String TAG = "DbConnectionFactory";

    private final String url;
    private final Properties props = new Properties();

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("PostgreSQL driver not found", e);
        }
    }

    public static DatabaseConnectionFactory fromConfig() {
        return new DatabaseConnectionFactory(
                Config.POSTGRES_URL,
                Config.POSTGRES_USER,
                Config.POSTGRES_PASSWORD
        );
    }

    public DatabaseConnectionFactory(String url, String user, String password) {
        this.url = url;
        props.setProperty("user", user);
        props.setProperty("password", password);
        props.setProperty("sslmode", "prefer");
    }

    public Connection newConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, props);
        Log.d(TAG, "Opened connection to " + url);
        return connection;
    }
}

