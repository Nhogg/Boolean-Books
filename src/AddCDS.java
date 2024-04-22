/**
 * Adds CDs either manually or from a CSV file to the database.
 *
 *
 *
 * Pre-conditions:
 * - The database connection must be established.
 *
 * Post-conditions:
 * - CDs are added to the database.
 */
import java.sql.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.util.Scanner;
import java.io.FileReader;
import java.io.Reader;

public class AddCDS {
    static Scanner stdin = new Scanner(System.in);
    /**
     * cdPrompt
     *   Prompt user for album name, artist, etc. and call addCD with those values as parameters.
     *
     */
    public static void cdPrompt() throws SQLException {
        CLIScreens.addCDs();
        System.out.println("Album Name: ");
        String albumName = stdin.next();
        System.out.println("Artist: ");
        String artist = stdin.next();
        System.out.println("Genre: ");
        String genre = stdin.next();
        System.out.println("Year: ");
        String year = stdin.next();
        System.out.println("Quantity: ");
        int quantity = stdin.nextInt();
        addCD(albumName, artist, genre, year, quantity);
    }
    /**
     * Add books to the database.
     * Pre-conditions:
     *  -Parameters have to have been extracted from either user input or the csv parser.
     *
     * Post-Conditions:
     *  -Books are added to the database.
     * @param albumName The name of the album.
     * @param artist The artist.
     * @param genre The genre of the album.
     * @param year The release year of the album.
     * @param quantity The quantity of the album to add.
     */
    public static void addCD(String albumName, String artist, String genre, String year, int quantity) throws SQLException {
        Connection connection = dbConnect.getInstance().getConnection();

        String addCDQuery = "INSERT INTO albums (album_name, artist, year, genre, quantity) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement addCDStatement = connection.prepareStatement(addCDQuery)) {
            addCDStatement.setString(1, albumName);
            addCDStatement.setString(2, artist);
            addCDStatement.setString(3, year);
            addCDStatement.setString(4, genre);
            addCDStatement.setInt(5, quantity);
            int rowsInserted = addCDStatement.executeUpdate();
            System.out.println("CD added successfully!");
        }

    }

    /**
     * addCDFromCSV
     *  CSV parser to extract books from albumlist.csv
     *
     * Extract books and call addBooks.
     * Ask user if they want to add more books. If yes, call addCDFromCSV.
     * If no, call Admin.adminScreen.
     *
     */
    public static void addCDFromCSV() {
        System.out.println("Please enter the path to your CSV file: ");
        String path = stdin.nextLine();

        try {
            Reader reader = new FileReader(path);

            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withHeader("Year", "Album", "Artist", "Genre", "Quantity")
                    .withSkipHeaderRecord(true));
            for (CSVRecord record : csvParser) {
                String year = record.get("Year");
                String album = record.get("Album");
                String artist = record.get("Artist");
                String genre = record.get("Genre");
                String quantityStr = record.get("Quantity");
                int quantity = 0;
                if (!quantityStr.isEmpty()) {
                    quantity = Integer.parseInt(quantityStr);
                }
                addCD(album, artist, genre, year, quantity);
                System.out.println("Album: " + album);
                System.out.println("Artist: " + artist);
                System.out.println("Genre: " + genre);
                System.out.println("Year: " + year);
                System.out.println();
            }
            System.out.println("CDs added successfully! Would you like to add more? (yes or no): ");
            String response = stdin.next();
            if (response.equals("yes"))
                addCDFromCSV();
            else
                Admin.adminScreen();


            csvParser.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
