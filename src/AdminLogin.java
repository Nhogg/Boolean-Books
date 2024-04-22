/**
 * Authenticates administrator login.
 *
 * Pre-conditions:
 * - The database connection must be established.
 *
 * @param username The username of the administrator.
 * @param password The password of the administrator.
 * @throws SQLException if a database access error occurs.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminLogin {

    public static void adminLogin(String username, String password) throws SQLException {
        Connection connection = dbConnect.getInstance().getConnection();

        String checkUsernameQuery = "SELECT * FROM users WHERE username = ? AND admin = 1";
        try (PreparedStatement checkUsernameStatement = connection.prepareStatement((checkUsernameQuery))) {
            checkUsernameStatement.setString(1, username);
            ResultSet resultSet = checkUsernameStatement.executeQuery();
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                System.out.println("Login successful!");
                Admin.adminScreen();
            } else {
                System.out.println("Error! You are not an administrator.");
                System.exit(0);
            }
        }

    }
}
