package com.evcharge.app;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static String url = "jdbc:mysql://localhost:3306/ev_charging_app?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static String user = "root";
    private static String password = "208161";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        Properties props = new Properties();
        boolean loaded = false;

        // Try loading from classpath first
        try (InputStream is = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (is != null) {
                props.load(is);
                loaded = true;
            }
        } catch (Exception ignored) {}

        // Fallback: try loading from file system
        if (!loaded) {
            try (InputStream is = Files.newInputStream(Paths.get("db.properties"))) {
                props.load(is);
                loaded = true;
            } catch (Exception ignored) {}
        }

        if (loaded) {
            url = props.getProperty("db.url", url);
            user = props.getProperty("db.user", user);
            password = props.getProperty("db.password", password);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
