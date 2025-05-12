package roomley.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
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
        try {
            properties = loadProperties("/database.properties");

        } catch (Exception e) {
            logger.error(e);
        }

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

        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(classloader.getResourceAsStream(sqlFile))))) {
            this.connect();
            stmt = this.connection.createStatement();
            StringBuilder sql = new StringBuilder();

            while (br.ready()) {
                char inputValue = (char) br.read();

                // If semicolon is encountered, execute the SQL statement
                if (inputValue == ';') {
                    String query = sql.toString().trim();

                    if (query.isEmpty()) {
                        continue; // Skip empty queries
                    }

                    // Check if it's a SELECT query or not
                    if (isSelectQuery(query)) {
                        try (ResultSet rs = stmt.executeQuery(query)) {
                            // Process the result set if it's a SELECT query
                            processResultSet(rs);
                        } catch (SQLException e) {
                            this.logger.error("Error executing SELECT query", e);
                        }
                    } else {
                        try {
                            // For non-SELECT queries (INSERT, UPDATE, DELETE, etc.)
                            stmt.executeUpdate(query);
                        } catch (SQLException e) {
                            this.logger.error("Error executing DML/DDL query", e);
                        }
                    }

                    // Reset the SQL query string
                    sql.setLength(0);
                } else {
                    // Accumulate the characters for the SQL query
                    sql.append(inputValue);
                }
            }
        } catch (SQLException se) {
            this.logger.error("SQL Exception", se);
        } catch (Exception e) {
            this.logger.error("Exception", e);
        } finally {
            this.disconnect();
        }
    }

    /**
     * Helper method to determine if the query is a SELECT query.
     */
    private boolean isSelectQuery(String query) {
        return query.trim().toUpperCase().startsWith("SELECT");
    }

    /**
     * Helper method to process the result set of a SELECT query.
     */
    private void processResultSet(ResultSet rs) throws SQLException {
        while (rs.next()) {
            logger.info(rs.getString(1));

        }
    }

}
