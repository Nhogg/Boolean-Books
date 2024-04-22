/**
 * Provides methods for user login functionality.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login {

    /**
     * Checks if the provided username and password match those stored in the database.
     * If the login is successful, it prints a success message and redirects to the patron screen.
     * If the login fails, it prints an error message and prompts for login again.
     *
     * @param username The username provided by the user.
     * @param password The password provided by the user.
     * @return True if login is successful, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public static boolean checkLogin(String username, String password) throws SQLException {
        Connection connection = dbConnect.getInstance().getConnection();
        String query = "SELECT * FROM users WHERE Username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("Password");
                if (storedPassword.equals(password)) {
                    System.out.println("Login successful!");
                    Patron.patronScreen();
                    return true;
                } else {
                    System.out.println("Incorrect Password! Try again.");
                    CLIScreens.login();
                    Main.login();
                }
            } else {

                return false;
            }
        }

        return true;
    }

}
