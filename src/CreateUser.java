/**
 * Represents a utility class for creating user accounts and managing them in a MySQL database.
 * This class provides methods to prompt the user for their information, validate it,
 * and add the user to the database if the information is valid.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class CreateUser {
    public static Scanner stdin = new Scanner(System.in);
    /**
     * Prompts the user to input their information and creates a new user account.
     * Pre-condition: None.
     * Post-condition: If successful, a new user account is created and added to the database.
     */
    public static void creatUserPrompt() {
        System.out.print("Name: ");
        String name = stdin.next();
        System.out.print("Username: ");
        String username = stdin.next();
        System.out.print("Date of Birth: ");
        String DOB = stdin.next();
        System.out.print("Password: ");
        String password = stdin.next();
        System.out.print("Reenter Password: ");
        String repeatedPassword = stdin.next();
        while (!Objects.equals(password, repeatedPassword)) {
            System.out.print("Passwords do not match!\nPassword: ");
            repeatedPassword = stdin.next();
        }
        addUserToDB(name, username, DOB, password);
    }
    /**
     * Adds a new user to the database.
     * Pre-condition: The database connection must be established.
     * Post-condition: If successful, the user is added to the database.
     *
     * @param name     The name of the user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param dob      The date of birth of the user.
     */
    public static void addUserToDB(String name, String username, String password, String dob) {
        Connection connection = dbConnect.getInstance().getConnection();
        String checkUsernameQuery = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement checkUsernameStatement = connection.prepareStatement(checkUsernameQuery)) {
            checkUsernameStatement.setString(1, username);
            ResultSet resultSet = checkUsernameStatement.executeQuery();

            if (!resultSet.next()) {
                String addUserQuery = "INSERT INTO users (name, username, password, dob) VALUES (?, ?, ?, ?)";
                try (PreparedStatement addUserStatement = connection.prepareStatement(addUserQuery)) {
                    addUserStatement.setString(1, name);
                    addUserStatement.setString(2, username);
                    addUserStatement.setString(3, dob);
                    addUserStatement.setString(4, password);


                    int rowsAffected = addUserStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("User added successfully.");
                        Patron.patronScreen();
                    } else {
                        System.out.println("Failed to add user.");
                    }
                }
            } else {
                System.out.println("Username already exists!");
                System.out.println("Login or create an account!");

            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


}
