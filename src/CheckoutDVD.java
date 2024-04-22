/**
 * Represents a utility class for checking out DVDs from the library.
 * This class provides methods to prompt the user for DVD and user information,
 * check the availability of the DVD in the database, and update the database
 * to reflect the checkout.
 */
import java.sql.*;
import java.util.Scanner;
public class CheckoutDVD {
    /**
     * Prompts the user to enter the title of the DVD and their username,
     * then checks the availability of the DVD and calls the next method.
     * Pre-condition: None.
     * Post-condition: If the DVD is available, the DVD is checked out for the user.
     */
    public static void promptUserForCheckout() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter title: ");
            String title = scanner.nextLine();
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            scanner.close();
            checkDVDAvailability(title, username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    /**
     * Checks the availability of the specified DVD in the database.
     * Pre-condition: The database connection must be established.
     * Post-condition: If the DVD is available, the next method is called to update the database.
     *
     * @param title    The title of the DVD to be checked out.
     * @param username The username of the user who wants to check out the DVD.
     * @throws SQLException if a database access error occurs.
     */
    public static void checkDVDAvailability(String title, String username) throws SQLException {
        try (Connection connection = dbConnect.getInstance().getConnection()) {
            String selectQuery = "SELECT *  FROM dvds WHERE title=? AND quantity > 0";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String dvdTitle = resultSet.getString("title");
                updateDVDAndUser(dvdTitle, username);
            } else {
                System.out.println("DVD not available or out of stock.");
            }
        }
    }
    /**
     * Updates the DVD quantity and adds the DVD title to the user's checkouts column in the database.
     * Pre-condition: The database connection must be established.
     * Post-condition: If successful, the DVD is checked out for the user.
     *
     * @param title    The title of the DVD to be checked out.
     * @param username The username of the user who wants to check out the DVD.
     */
    public static void updateDVDAndUser(String title, String username) {
        try (Connection connection = dbConnect.getInstance().getConnection()) {
            String updateDVDQuery = "UPDATE dvds SET quantity = quantity - 1 WHERE title = ?";
            String updateUserQuery = "UPDATE users SET checkouts = CONCAT_WS(',', checkouts, ?) WHERE username = ?";
            PreparedStatement updateBookStatement = connection.prepareStatement(updateDVDQuery);
            updateBookStatement.setString(1, title);
            int updatedRows = updateBookStatement.executeUpdate();
            if (updatedRows > 0) {
                PreparedStatement updateUserStatement = connection.prepareStatement(updateUserQuery);
                updateUserStatement.setString(1, title);
                updateUserStatement.setString(2, username);
                int updatedUserRows = updateUserStatement.executeUpdate();
                if (updatedUserRows > 0) {
                    System.out.println("DVD '" + title + "' checked out successfully for user '" + username + "'.");
                } else {
                    System.out.println("Failed to update user.");
                }
            } else {
                System.out.println("Failed to update DVD quantity.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
