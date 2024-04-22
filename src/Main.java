/**
 * Run the program from here.
 * Boolean Books is a full-featured library simulation.
 * To begin, run Main.java. Select create an Account. Follow the
 * prompts in the command line. If you would like to log in as an
 * admin, use the username "admin" and password "admin"
 * Sample CSV files can be found within this package. albumlist.csv
 * can be used to add CDs from csv, Books.csv can be used
 * to add books from csv, and movies.csv can be used to add
 * DVDs from csv.
 */

import java.sql.SQLException;
import java.util.Scanner;
public class Main {
    public static Scanner stdin = new Scanner(System.in);

    /**
     * Main method to run the Boolean Books program.
     *
     * @param args Command-line arguments (not used).
     * @throws SQLException If a database access error occurs.
     */
    public static void main(String[] args) throws SQLException {
        DatabaseCreator.create();
        int screenNum = 1;
        printCLI();
        int screen1Selection = stdin.nextInt();
        if (screen1Selection == 1) {
            CLIScreens.login();
            login();
        } else if (screen1Selection == 2) {
            CLIScreens.createUser();
            createUser();
        } else if (screen1Selection == 3) {
            CLIScreens.adminLogin();
            adminLogin();

        } else
            System.out.print("Please select from the list above.");

    }

    /**
     * Prints the Command Line Interface (CLI) for the user to make a selection.
     */
    public static void printCLI() {
        CLIScreens.printStartCLI();
    }

    /**
     * Handles the login process.
     *
     * @throws SQLException If a database access error occurs.
     */
    public static void login() throws SQLException {
        System.out.print("Username: ");
        String username = stdin.next();
        System.out.print("Password: ");
        String password = stdin.next();
        login.checkLogin(username, password);
    }

    /**
     * Handles the user creation process.
     */
    public static void createUser() {
        CreateUser.creatUserPrompt();
    }

    /**
     * Handles the admin login process.
     *
     * @throws SQLException If a database access error occurs.
     */
    public static void adminLogin() throws SQLException {
        System.out.print("Username: ");
        String username = stdin.next();
        System.out.print("Password: ");
        String password = stdin.next();
        AdminLogin.adminLogin(username, password);
    }



}
