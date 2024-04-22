/**
 * Adds DVDs either manually or from a CSV file to the database.
 *
 *
 *
 * Pre-conditions:
 * - The database connection must be established.
 *
 * Post-conditions:
 * - DVDs are added to the database.
 */
import java.io.FileNotFoundException;
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


public class AddDVDs {
    public static Scanner stdin = new Scanner(System.in);
    /**
     * addDVDsManual
     *   Prompt user for title, genre, year, length, director, cast, summary, and quantity.
     *   Call addDVDs with those values as parameters.
     *
     */
    public static void addDVDsManual() {

        CLIScreens.addDVDsManual();
        System.out.print("Title: ");
        String title = stdin.nextLine();
        System.out.print("Genre: ");
        String genre = stdin.nextLine();
        System.out.print("Year: ");
        String year = stdin.next();
        System.out.print("Length: ");
        String length = stdin.next();
        System.out.print("Director: ");
        String director = stdin.next();
        System.out.print("Cast: ");
        String cast = stdin.next();
        System.out.print("Summary: ");
        String description = stdin.next();
        System.out.print("Quantity: ");
        String quantity = stdin.next();
        addDVDs(title, genre, year, length, director, description, quantity, cast);
    }

    /**
     * Add DVDs to the database.
     * Pre-conditions:
     *  -Parameters have to have been extracted from either user input or the csv parser.
     *
     * Post-Conditions:
     *  -Books are added to the database.
     * @param title - Title of the dvd.
     * @param genre - Genre of the dvd.
     * @param year - Release year of the dvd.
     * @param length - Runtime of the dvd.
     * @param director - Director of the dvd.
     * @param cast - Cast of the dvd.
     * @param description - Description of the dvd.
     * @param quantity - Quantity of the dvd to be added.
     */
    public static void addDVDs(String title, String genre, String year, String length, String director, String cast, String description, String quantity) {
        Connection connection = dbConnect.getInstance().getConnection();


        String addDVDsQuery = "INSERT INTO dvds (title, year, genre, length, director, description, quantity, cast) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement addDVDsStatement = connection.prepareStatement(addDVDsQuery)) {
            addDVDsStatement.setString(1, title);
            addDVDsStatement.setString(2, genre);
            addDVDsStatement.setString(3, year);
            addDVDsStatement.setString(4, length);
            addDVDsStatement.setString(5, director);
            addDVDsStatement.setString(6, description);
            addDVDsStatement.setString(7, cast);
            addDVDsStatement.setString(8, quantity);
            int rowsInserter = addDVDsStatement.executeUpdate();
            System.out.println("DVDs added successfully!");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * addDVDsFromCSV
     *  CSV parser to extract books from movies.csv
     *
     * Extract DVDS and call addDVDs.
     * Ask user if they want to add more books. If yes, call addDVDsFromCSV.
     * If no, call Admin.adminScreen.
     *
     */
    public static void addDVDsFromCSV() {
        System.out.print("Please enter the path to your CSV: ");
        String path = stdin.nextLine();
        //title	year	genre	length	director			actors	description	Quantity
        try {
            Reader reader = new FileReader(path);

            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withHeader("title", "year", "genre", "length", "director", "actors", "description", "Quantity")
                    .withSkipHeaderRecord(true));
            for (CSVRecord record : csvParser) {
                String title = record.get("title");
                String year = record.get("year");
                String genre = record.get("genre");
                String length = record.get("length");
                String director = record.get("director");
                String actors = record.get("actors");
                String description = record.get("description");
                String quantity = record.get("Quantity");
                addDVDs(title, year, genre, length, director, quantity, description, actors);

                System.out.println("Title: " + title);
                System.out.println("Year: " + year);
                System.out.println("Genre: " + genre);
                System.out.println("Length: " + length);
                System.out.println("Director: " + director);
                System.out.println("Cast: " + actors);
                System.out.println("Description: " + description);
                System.out.println("Quantity: " + quantity);
            }
            System.out.println("DVDs added succesfully! Would you like to add more? (Yes or No): ");
            String response = stdin.next();
            if (response.equals("Yes"))
                addDVDsFromCSV();
            else
                Admin.adminScreen();



        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
