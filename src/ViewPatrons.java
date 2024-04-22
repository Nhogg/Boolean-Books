/**
 * Utility class to handle viewing of patrons.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewPatrons {

    /**
     * View patrons in tabular format.
     */
    public static void viewPatrons() {
        try {
            // Establishing connection to the MySQL database
            Connection connection = dbConnect.getInstance().getConnection();

            // SQL query to retrieve user data
            String query = "SELECT Name, Username, DOB FROM users";

            // Creating PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            // Executing the query and getting the ResultSet
            ResultSet resultSet = preparedStatement.executeQuery();

            // Find the maximum width for the Name column
            int nameWidth = getMaxWidth(resultSet, "Name");
            int usernameWidth = getMaxWidth(resultSet, "Username");
            int dobWidth = getMaxWidth(resultSet, "DOB"); // Adjust as per your date format

            // Printing the header
            printHeader(nameWidth, usernameWidth, dobWidth);

            // Reset ResultSet cursor
            resultSet.beforeFirst();

            // Printing the values in table format
            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                String username = resultSet.getString("Username");
                String dob = resultSet.getString("DOB");

                // Printing values in table format
                printFormattedLine(name, username, dob, nameWidth, usernameWidth, dobWidth);
                printSeparatorLine(nameWidth, usernameWidth, dobWidth);
            }

            // Closing the resources

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get the maximum width of a column
    private static int getMaxWidth(ResultSet resultSet, String columnName) throws SQLException {
        int maxWidth = 0;
        while (resultSet.next()) {
            String value = resultSet.getString(columnName);
            maxWidth = Math.max(maxWidth, value.length());
        }
        return maxWidth;
    }

    // Method to print header
    private static void printHeader(int nameWidth, int usernameWidth, int dobWidth) {
        // Print header with minimum width of 1 for each column
        System.out.printf("%-" + Math.max(1, nameWidth) + "s  %-" + Math.max(1, usernameWidth) + "s  %-" + Math.max(1, dobWidth) + "s%n", "Name", "Username", "DOB");
    }

    // Method to print formatted line
    private static void printFormattedLine(String name, String username, String dob, int nameWidth, int usernameWidth, int dobWidth) {
        // Print formatted line with minimum width of 1 for each column
        System.out.printf("%-" + Math.max(1, nameWidth) + "s  %-" + Math.max(1, usernameWidth) + "s  %-" + Math.max(1, dobWidth) + "s%n", name, username, dob);
    }

    // Method to print separator line
    private static void printSeparatorLine(int nameWidth, int usernameWidth, int dobWidth) {
        StringBuilder separatorLine = new StringBuilder();
        for (int i = 0; i < nameWidth + usernameWidth + dobWidth + 2; i++) {
            separatorLine.append("---");
        }
        System.out.println(separatorLine.toString());
    }
}