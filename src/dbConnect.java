/**
 * Represents a singleton class for establishing a database connection to the Boolean Books database.
 * This class ensures that only one instance of the database connection is created and shared across the application.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class dbConnect {

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "admin1234";
    private static dbConnect instance;
    private Connection connection;

    /**
     * Private constructor to prevent instantiation from outside the class.
     * Establishes a connection to the database.
     */
    private dbConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // You may want to handle this exception appropriately.
            // For simplicity, printing the stack trace here.
        }
    }

    /**
     * Returns the singleton instance of the dbConnect class.
     *
     * @return The singleton instance of dbConnect.
     */
    public static synchronized dbConnect getInstance() {
        if (instance == null) {
            instance = new dbConnect();
        }
        return instance;
    }

    /**
     * Gets the connection to the database.
     *
     * @return The database connection.
     */
    public Connection getConnection() {
        return this.connection;
    }
}
