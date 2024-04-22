
/**
 * Utility class to handle viewing of books.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewBooks {

    /**
     * Displays the available books in a tabular format.
     */
    public static void viewBooks() {
        try {
            // Establishing connection to the MySQL database
            Connection connection = dbConnect.getInstance().getConnection();

            // SQL query to retrieve book data
            String query = "SELECT title, author, pagenum, genre, summary, quantity FROM books";

            // Creating PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


            // Executing the query and getting the ResultSet
            ResultSet resultSet = preparedStatement.executeQuery();

            // Find the maximum width for the title column
            int titleWidth = getMaxWidth(resultSet, "title");
            int summaryWidth = 50; // Max width for summary

            // Printing the header
            printHeader(titleWidth, summaryWidth);

            // Reset ResultSet cursor
            resultSet.beforeFirst();

            // Printing the values in table format
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int pagenum = resultSet.getInt("pagenum");
                String genre = resultSet.getString("genre");
                String summary = resultSet.getString("summary");
                int quantity = resultSet.getInt("quantity");

                // Printing values in table format
                printFormattedLine(title, author, pagenum, genre, summary, quantity, titleWidth, summaryWidth);
                printSeparatorLine(titleWidth, summaryWidth);
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
    private static void printHeader(int titleWidth, int summaryWidth) {
        // Calculate the width of other columns
        int authorWidth = 20;
        int pagenumWidth = 10;
        int genreWidth = 20;

        // Print header
        System.out.printf("%-" + titleWidth + "s  %-" + authorWidth + "s  %-" + pagenumWidth + "s  %-" + genreWidth + "s  %-" + summaryWidth + "s  %s%n", "Title", "Author", "Pages", "Genre", "Summary", "Quantity");
    }

    // Method to print formatted line
    private static void printFormattedLine(String title, String author, int pagenum, String genre, String summary, int quantity, int titleWidth, int summaryWidth) {
        // Calculate the width of other columns
        int authorWidth = 20;
        int pagenumWidth = 10;
        int genreWidth = 20;

        // Print formatted line
        System.out.printf("%-" + titleWidth + "s  %-" + authorWidth + "s  %-" + pagenumWidth + "d  %-" + genreWidth + "s  %-" + summaryWidth + "s  %d%n", title, author, pagenum, genre, wrapSummary(summary, summaryWidth, titleWidth + authorWidth + pagenumWidth + genreWidth + 2), quantity);
    }

    // Method to wrap summary to multiple lines if too long
    private static String wrapSummary(String summary, int summaryWidth, int totalWidth) {
        StringBuilder wrappedSummary = new StringBuilder();
        int remainingWidth = totalWidth;
        for (int i = 0; i < summary.length(); i += summaryWidth) {
            int endIndex = Math.min(i + summaryWidth, summary.length());
            wrappedSummary.append(String.format("%-" + remainingWidth + "s", summary.substring(i, endIndex)));
            if (endIndex < summary.length()) {
                wrappedSummary.append("\n");
                remainingWidth = totalWidth; // Reset remaining width for new line
            }
        }
        return wrappedSummary.toString();
    }

    // Method to print separator line
    private static void printSeparatorLine(int titleWidth, int summaryWidth) {
        StringBuilder separatorLine = new StringBuilder();
        for (int i = 0; i < titleWidth + 20 + 10 + 20 + summaryWidth + 15; i++) {
            separatorLine.append("-");
        }
        System.out.println(separatorLine.toString());
    }
}
