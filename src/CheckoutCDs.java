/**
 * Represents a utility class for checking out CDs from the library.
 * This class provides methods to prompt the user for CD and user information,
 * check the availability of the CD in the database, and update the database
 * to reflect the checkout.
 */
import java.sql.*;
import java.util.Scanner;
public class CheckoutCDs {
    /**
     * Prompts the user to enter the title of the CD and their username,
     * then checks the availability of the CD and calls the next method.
     * Pre-condition: None.
     * Post-condition: If the CD is available, the CD is checked out for the user.
     */
    public static void promptUserForCheckout() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter title: ");
            String title = scanner.nextLine();
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            scanner.close();
            checkCDAvailability(title, username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Checks the availability of the specified CD in the database.
     * Pre-condition: The database connection must be established.
     * Post-condition: If the CD is available, the next method is called to update the database.
     *
     * @param title    The title of the CD to be checked out.
     * @param username The username of the user who wants to check out the CD.
     * @throws SQLException if a database access error occurs.
     */
    public static void checkCDAvailability(String title, String username) throws SQLException {
        try (Connection connection = dbConnect.getInstance().getConnection()) {
            String selectQuery = "SELECT *  FROM albums WHERE album_name=? AND quantity > 0";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String cdTitle = resultSet.getString("album_name");
                updateCDAndUser(cdTitle, username);
            } else {
                System.out.println("CD not available or out of stock.");
            }
        }
    }
    /**
     * Updates the CD quantity and adds the CD title to the user's checkouts column in the database.
     * Pre-condition: The database connection must be established.
     * Post-condition: If successful, the CD is checked out for the user.
     *
     * @param title    The title of the CD to be checked out.
     * @param username The username of the user who wants to check out the CD.
     * @throws SQLException if a database access error occurs.
     */
    public static void updateCDAndUser(String title, String username) throws SQLException {
        try (Connection connection = dbConnect.getInstance().getConnection()) {
            String updateBookQuery = "UPDATE albums SET quantity = quantity - 1 WHERE album_name = ?";
            String updateUserQuery = "UPDATE users SET checkouts = CONCAT_WS(',', checkouts, ?) WHERE username = ?";
            PreparedStatement updateBookStatement = connection.prepareStatement(updateBookQuery);
            updateBookStatement.setString(1, title);
            int updatedRows = updateBookStatement.executeUpdate();
            if (updatedRows > 0) {
                PreparedStatement updateUserStatement = connection.prepareStatement(updateUserQuery);
                updateUserStatement.setString(1, title);
                updateUserStatement.setString(2, username);
                int updatedUserRows = updateUserStatement.executeUpdate();
                if (updatedUserRows > 0) {
                    System.out.println("Album '" + title + "' checked out successfully for user '" + username + "'.");
                } else {
                    System.out.println("Failed to update user.");
                }
            } else {
                System.out.println("Failed to update album quantity.");
            }
        }
    }
}
