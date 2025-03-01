package Roomley.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import Roomley.util.PropertiesLoader;

public class Database {

    private final Logger logger = LogManager.getLogger(this.getClass());
    private static Database instance = new Database();
    private Properties properties;
    private Connection connection;

    PropertiesLoader loader = new PropertiesLoader() {

        public Properties loadProperties(String propertiesFilePath) {
            return super.loadProperties(propertiesFilePath);

        }

    };

    private Database() {
        this.properties = this.loader.loadProperties("/database.properties");
    }

    public static Database getInstance() {
        return instance;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void connect() throws Exception {
        if (this.connection == null) {
            try {
                Class.forName(this.properties.getProperty("driver"));
            } catch (ClassNotFoundException var2) {
                throw new Exception("Database.connect()... Error: MySQL Driver not found");
            }

            String url = this.properties.getProperty("url");
            this.connection = DriverManager.getConnection(url, this.properties.getProperty("username"), this.properties.getProperty("password"));
        }
    }

    public void disconnect() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                this.logger.error("Cannot close connection", e);
            }
        }

        this.connection = null;
    }

    public void runSQL(String sqlFile) {
        Statement stmt = null;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(classloader.getResourceAsStream(sqlFile)));

            try {
                this.connect();
                stmt = this.connection.createStatement();
                String sql = "";

                while(br.ready()) {
                    char inputValue = (char)br.read();
                    if (inputValue == ';') {
                        stmt.executeUpdate(sql);
                        sql = "";
                    } else {
                        sql = sql + inputValue;
                    }
                }
            } catch (Throwable var14) {
                try {
                    br.close();
                } catch (Throwable var13) {
                    var14.addSuppressed(var13);
                }

                throw var14;
            }

            br.close();
        } catch (SQLException se) {
            this.logger.error("SQL Exception", se);
        } catch (Exception e) {
            this.logger.error("Exception", e);
        } finally {
            this.disconnect();
        }

    }
}
