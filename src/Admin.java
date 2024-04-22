/**
 * Represents an administrative interface for managing media resources and patrons.
 *
 * Pre-conditions:
 * - The database connection must be established.
 *
 * @throws SQLException if a database access error occurs.
 */

import java.sql.SQLException;
import java.util.Scanner;
public class Admin {
    public static Scanner stdin = new Scanner(System.in);
    public static void adminScreen() throws SQLException {

        CLIScreens.mainAdminPage();
        int selection = stdin.nextInt();
        switch (selection) {
            case 1:
                CLIScreens.chooseMedia();
                chooseMedia();
                break;
            case 2:
                ViewPatrons.viewPatrons();
                break;
        }


    }
    public static void chooseMedia() throws SQLException {
        int selection = stdin.nextInt();
        switch (selection) {
            case 1:
                addBooks();
                break;
            case 2:
                addCDs();
                break;
            case 3:
                addDVDs();
                break;
        }
    }
    public static void addBooks() throws SQLException {
        CLIScreens.addMedia();
        int selection = stdin.nextInt();
        switch (selection) {
            case 1:
                AddBooks.addBooksFromCSV();
                break;
            case 2:
                AddBooks.addBooksManual();
                break;
        }
    }
    public static void addCDs() throws SQLException {
        CLIScreens.addMedia();
        int selection = stdin.nextInt();
        switch (selection) {
            case 1:
                AddCDS.addCDFromCSV();
                break;
            case 2:
                AddCDS.cdPrompt();
                break;
        }
    }
    public static void addDVDs() throws SQLException {
        CLIScreens.addMedia();
        int selection = stdin.nextInt();
        switch (selection) {
            case 1:
                AddDVDs.addDVDsFromCSV();
                break;
            case 2:
                AddDVDs.addDVDsManual();
                break;
        }
    }
}
