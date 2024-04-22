/**
 * Utility class to handle patron-side operations.
 */

import java.util.Scanner;
import java.sql.SQLException;
public class Patron {
    public static Scanner stdin = new Scanner(System.in);

    /**
     * Displays the main user page and handles user selection.
     *
     * @throws SQLException If a database access error occurs.
     */
    public static void patronScreen() throws SQLException {
        CLIScreens.mainUserPage();
        int selection = stdin.nextInt();
        switch (selection) {
            case 1:
                browseMedia();
                break;
            case 2:
                returnMedia();
                break;

        }
    }

    /**
     * Displays the options to browse media and handles user selection.
     *
     * @throws SQLException If a database access error occurs.
     */
    public static void browseMedia() throws SQLException {
        CLIScreens.chooseMedia();
        int selection = stdin.nextInt();
        switch (selection) {
            case 1:
                viewBooks();
                break;
            case 2:
                viewCDs();
                break;
            case 3:
                viewDVDs();
                break;
        }
    }

    /**
     * Displays available books and prompts the user to check out.
     *
     * @throws SQLException If a database access error occurs.
     */
    public static void viewBooks() throws SQLException {
        ViewBooks.viewBooks();
        System.out.print("Would you like to checkout a book? (yes or no)");
        String selection = stdin.next();
        switch (selection) {
            case "yes":
                CheckoutBook.promptUserForCheckout();
                break;
            case "no":
                CLIScreens.mainUserPage();
                break;
        }

    }

    /**
     * Displays available DVDs and prompts the user to check out.
     */
    public static void viewDVDs() {
        ViewDVDs.viewDVDs();
        System.out.print("Would you like to checkout a DVD? (yes or no)");
        String selection = stdin.next();
        switch (selection) {
            case "yes":
                CheckoutDVD.promptUserForCheckout();
                break;
            case "no":
                CLIScreens.mainUserPage();
                break;
        }
    }

    /**
     * Displays available CDs and prompts the user to check out.
     */
    public static void viewCDs() {
        ViewCDs.viewCDs();
        System.out.print("Would you like to checkout a CD? (yes or no)");
        String selection = stdin.next();
        switch (selection) {
            case "yes":
                CheckoutCDs.promptUserForCheckout();
                break;
            case "no":
                CLIScreens.mainUserPage();
                break;
        }
    }

    /**
     * Prompts the user to return media.
     */
    public static void returnMedia() {
        ReturnMedia.prompt();
    }

}