/**
 * Utility class to handle viewing of DVDs
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewDVDs {

    /**
     * Displays available DVDs in tabular format.
     */
    public static void viewDVDs() {
        try {
            // Establishing connection to the MySQL database
            Connection connection = dbConnect.getInstance().getConnection();

            // SQL query to retrieve DVD data
            String query = "SELECT title, year, genre, length, director, description, Quantity, cast FROM dvds";

            // Creating PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            // Executing the query and getting the ResultSet
            ResultSet resultSet = preparedStatement.executeQuery();

            // Find the maximum width for the title column
            int titleWidth = getMaxWidth(resultSet, "title");
            int descriptionWidth = 60; // Max width for description

            // Printing the header
            printHeader(titleWidth, descriptionWidth);

            // Reset ResultSet cursor
            resultSet.beforeFirst();

            // Printing the values in table format
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                int year = resultSet.getInt("year");
                String genre = resultSet.getString("genre");
                int length = resultSet.getInt("length");
                String director = resultSet.getString("director");
                String description = resultSet.getString("description");
                int quantity = resultSet.getInt("Quantity");
                String cast = resultSet.getString("cast");

                // Printing values in table format
                printFormattedLine(title, year, genre, length, director, description, quantity, cast, titleWidth, descriptionWidth);
                printSeparatorLine(titleWidth, descriptionWidth);
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
    private static void printHeader(int titleWidth, int descriptionWidth) {
        // Calculate the width of other columns
        int yearWidth = 5; // Assuming year is an integer
        int genreWidth = 15;
        int lengthWidth = 7; // Assuming length is an integer
        int directorWidth = 20;
        int quantityWidth = 8; // Assuming Quantity is an integer

        // Print header
        System.out.printf("%-" + titleWidth + "s  %-" + yearWidth + "s  %-" + genreWidth + "s  %-" + lengthWidth + "s  %-" + directorWidth + "s  %-" + descriptionWidth + "s  %-" + quantityWidth + "s  %s%n", "Title", "Year", "Genre", "Length", "Director", "Description", "Quantity", "Cast");
    }

    // Method to print formatted line
    private static void printFormattedLine(String title, int year, String genre, int length, String director, String description, int quantity, String cast, int titleWidth, int descriptionWidth) {
        // Calculate the width of other columns
        int yearWidth = 5; // Assuming year is an integer
        int genreWidth = 15;
        int lengthWidth = 7; // Assuming length is an integer
        int directorWidth = 20;
        int quantityWidth = 8; // Assuming Quantity is an integer

        // Print formatted line
        System.out.printf("%-" + titleWidth + "s  %-" + yearWidth + "d  %-" + genreWidth + "s  %-" + lengthWidth + "d  %-" + directorWidth + "s  %-" + descriptionWidth + "s  %-" + quantityWidth + "d  %s%n", title, year, genre, length, director, wrapDescription(description, descriptionWidth, titleWidth + yearWidth + genreWidth + lengthWidth + directorWidth + quantityWidth + 6), quantity, cast);
    }

    // Method to wrap description to multiple lines if too long
    private static String wrapDescription(String description, int descriptionWidth, int totalWidth) {
        StringBuilder wrappedDescription = new StringBuilder();
        int remainingWidth = totalWidth;
        for (int i = 0; i < description.length(); i += descriptionWidth) {
            int endIndex = Math.min(i + descriptionWidth, description.length());
            wrappedDescription.append(String.format("%-" + remainingWidth + "s", description.substring(i, endIndex)));
            if (endIndex < description.length()) {
                wrappedDescription.append("\n");
                remainingWidth = totalWidth; // Reset remaining width for new line
            }
        }
        return wrappedDescription.toString();
    }

    // Method to print separator line
    private static void printSeparatorLine(int titleWidth, int descriptionWidth) {
        StringBuilder separatorLine = new StringBuilder();
        for (int i = 0; i < titleWidth + 5 + 15 + 7 + 20 + descriptionWidth + 8 + 8; i++) {
            separatorLine.append("-");
        }
        System.out.println(separatorLine.toString());
    }
}
