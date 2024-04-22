/**
 * Utility class to handle viewing of CDs.
 */

import java.sql.*;
import java.util.Scanner;

public class ViewCDs {
    public static Scanner stdin = new Scanner(System.in);

    /**
     * Display available CDs in tabular format.
     */
    public static void viewCDs() {
        try {
            // Establishing connection to the MySQL database
            Connection connection = dbConnect.getInstance().getConnection();
            // SQL query to retrieve album data
            String query = "SELECT album_name, artist, year, genre, quantity FROM albums";

            // Creating PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            // Executing the query and getting the ResultSet
            ResultSet resultSet = preparedStatement.executeQuery();

            // Find the maximum width for the album_name column
            int albumNameWidth = getMaxWidth(resultSet, "album_name");
            int genreWidth = 15; // Assuming genre has a fixed width

            // Printing the header
            printHeader(albumNameWidth, genreWidth);

            // Reset ResultSet cursor
            resultSet.beforeFirst();

            // Printing the values in table format
            while (resultSet.next()) {
                String albumName = resultSet.getString("album_name");
                String artist = resultSet.getString("artist");
                String year = resultSet.getString("year");
                String genre = resultSet.getString("genre");
                String quantity = resultSet.getString("quantity");

                // Printing values in table format
                printFormattedLine(albumName, artist, year, genre, quantity, albumNameWidth, genreWidth);
                printSeparatorLine(albumNameWidth, genreWidth);
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
    private static void printHeader(int albumNameWidth, int genreWidth) {
        // Calculate the width of other columns
        int artistWidth = 20; // Assuming artist has a fixed width
        int yearWidth = 5; // Assuming year is an integer
        int quantityWidth = 8; // Assuming quantity is an integer

        // Print header
        System.out.printf("%-" + albumNameWidth + "s  %-" + artistWidth + "s  %-" + yearWidth + "s  %-" + genreWidth + "s  %-" + quantityWidth + "s%n", "Album Name", "Artist", "Year", "Genre", "Quantity");
    }

    // Method to print formatted line
    // Method to print formatted line
    private static void printFormattedLine(String albumName, String artist, String year, String genre, String quantity, int albumNameWidth, int genreWidth) {
        // Calculate the width of other columns
        int artistWidth = 20; // Assuming artist has a fixed width
        int yearWidth = 5; // Assuming year is an integer
        int quantityWidth = 8; // Assuming quantity is an integer

        // Print formatted line
        System.out.printf("%-" + albumNameWidth + "s  %-" + artistWidth + "s  %-" + yearWidth + "s  %-" + genreWidth + "s  %-" + quantityWidth + "s%n", albumName, artist, year, genre, quantity);
    }


    // Method to print separator line
    private static void printSeparatorLine(int albumNameWidth, int genreWidth) {
        StringBuilder separatorLine = new StringBuilder();
        for (int i = 0; i < albumNameWidth + 20 + 5 + genreWidth + 8; i++) {
            separatorLine.append("-");
        }
        System.out.println(separatorLine.toString());
    }
}
