package roomley.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;

/**
 * Creates a database connection through a properties file
 * and allows for running of SQL files
 */
public class Database implements PropertiesLoader {

    private final Logger logger = LogManager.getLogger(this.getClass());
    private static Database instance = new Database();
    private Connection connection;
    private Properties properties;

    /**
     * Database constructor
     * instantiates properties file
     */
    public Database() {
        properties = loadProperties("/database.properties");

    }

    /**
     *  get the only Database object available
     *  @return the single database object
     */
    public static Database getInstance() { return instance; }

    /**
     * get the database connection
     * @return the database connection
     */
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

    /**
     * Disconnects from DB
     */
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

    /**
     * Runs SQL from a file
     * @param sqlFile SQL file to be ran
     */
    public void runSQL(String sqlFile) {
        Statement stmt = null;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(classloader.getResourceAsStream(sqlFile))));

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
