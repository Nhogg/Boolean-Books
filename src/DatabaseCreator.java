/**
 * A class responsible for creating database tables for the Boolean Books database.
 * It contains SQL commands to create tables for albums, books, DVDs, and users.
 * This class establishes a connection to the database using a singleton pattern and
 * creates tables using SQL statements.
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseCreator {

    private static final String[] CREATE_TABLES = {
            "CREATE TABLE IF NOT EXISTS albums (" +
                    "ID INT AUTO_INCREMENT PRIMARY KEY," +
                    "album_name VARCHAR(255)," +
                    "artist VARCHAR(255)," +
                    "year VARCHAR(1000)," +
                    "genre VARCHAR(255)," +
                    "quantity INT)",
            "CREATE TABLE IF NOT EXISTS books (" +
                    "ID INT AUTO_INCREMENT PRIMARY KEY," +
                    "title VARCHAR(255)," +
                    "author VARCHAR(255)," +
                    "pagenum INT," +
                    "genre VARCHAR(100)," +
                    "summary TEXT," +
                    "quantity INT)",
            "CREATE TABLE IF NOT EXISTS dvds (" +
                    "ID INT AUTO_INCREMENT PRIMARY KEY," +
                    "title VARCHAR(300)," +
                    "year VARCHAR(300)," +
                    "genre VARCHAR(300)," +
                    "length VARCHAR(3000)," +
                    "director VARCHAR(300)," +
                    "description VARCHAR(3000)," +
                    "Quantity VARCHAR(300)," +
                    "cast VARCHAR(300))",
            "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "Name VARCHAR(100)," +
                    "Username VARCHAR(30)," +
                    "Password VARCHAR(30)," +
                    "DOB VARCHAR(11)," +
                    "Admin INT," +
                    "checkouts VARCHAR(3000))"
    };

    /**
     * Main method to create the database, execute table creation.
     * It establishes a connection to the MySQL server, creates the database, selects it,
     * creates tables, and prints a success message.
     * Any SQL exception is caught and printed.
     */
    public static void create() {
        try {
            // Get the singleton instance of dbConnect
            dbConnect dbConnection = dbConnect.getInstance();
            Connection conn = dbConnection.getConnection();

            // Create the database if it doesn't exist
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS boolean_books");

            // Select the database
            stmt.executeUpdate("USE boolean_books");

            // Create tables
            for (String createTable : CREATE_TABLES) {
                stmt.executeUpdate(createTable);
            }

            System.out.println("Database and tables created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
