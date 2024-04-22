/**
 * Adds books either manually or from a CSV file to the database.
 *
 * Pre-conditions:
 * - The database connection must be established.
 *
 * Post-conditions:
 * - Books are added to the database.
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.Reader;

public class AddBooks {
    public static Scanner stdin = new Scanner(System.in);
    /**
     * addBooksManual
     *   Prompt user for title, author, etc. and call addBooks with those values as parameters.
     *
     */
    public static void addBooksManual() {

        CLIScreens.addBooksManual();
        System.out.print("Title: ");
        String title = stdin.nextLine();
        System.out.print("Author: ");
        String author = stdin.nextLine();
        System.out.print("Page Number: ");
        String pageNum = stdin.next();
        System.out.print("Genre: ");
        String genre = stdin.next();
        System.out.print("Summary: ");
        String summary = stdin.next();
        System.out.print("Quantity: ");
        String quantity = stdin.next();
        addBooks(title, author, pageNum, genre, summary, quantity);
    }
    /**
     * Add books to the database.
     * Pre-conditions:
     *  -Parameters have to have been extracted from either user input or the csv parser.
     *
     * Post-Conditions:
     *  -Books are added to the database.
     * @param title The title of the book.
     * @param author The author of the book.
     * @param pageNumber The number of pages in the book.
     * @param genre The genre of the book.
     * @param summary The summary of the book.
     * @param quantity The quantity of books to add.
     */
    public static void addBooks(String title, String author, String pageNumber, String genre, String summary, String quantity) {
        Connection connection = dbConnect.getInstance().getConnection();

        String addBooksQuery = "INSERT INTO books (Title, Author, PageNum, Genre, Summary, quantity) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement addBooksStatement = connection.prepareStatement(addBooksQuery)) {
            addBooksStatement.setString(1, title);
            addBooksStatement.setString(2, author);
            addBooksStatement.setInt(3, Integer.parseInt(pageNumber));
            addBooksStatement.setString(4, genre);
            addBooksStatement.setString(5, summary);
            addBooksStatement.setInt(6, Integer.parseInt(quantity));
            int rowsInserter = addBooksStatement.executeUpdate();
            System.out.println("Books added successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * addBooksFromCSV
     *  CSV parser to extract books from Books.csv
     *
     * Extract books and call addBooks.
     * Ask user if they want to add more books. If yes, call addBooksFromCSV.
     * If no, call Admin.adminScreen.
     *
     */
    public static void addBooksFromCSV() {
        System.out.print("Please enter the path to your CSV: ");
        String path = stdin.nextLine();
        try {
            Reader reader = new FileReader(path);

            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withHeader("Title","Author","Page Number","Genre","Summary","Quantity")
                    .withSkipHeaderRecord(true));

            for (CSVRecord record : csvParser) {
                String title = record.get("Title");
                String author = record.get("Author");
                String pageNumber = record.get("Page Number");
                String genre = record.get("Genre");
                String summary = record.get("Summary");
                String quantity = record.get("Quantity");
                addBooks(title, author, pageNumber, genre, summary, quantity);

                System.out.println("Title: " + title);
                System.out.println("Author: " + author);
                System.out.println("Number of Pages: " + pageNumber);
                System.out.println("Genre: " + genre);
                System.out.println("Summary: " + summary);
                System.out.println("Quantity: " + quantity);
            }
            System.out.println("Books added successfully! Would you like to add more? (Yes or No): ");
            String response = stdin.next();

            if (response.equals("Yes"))
                addBooksFromCSV();
            else
                Admin.adminScreen();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
