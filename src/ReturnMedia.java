/**
 * Utility class to handle returning media.
 *
 */

import java.sql.*;
import java.util.Scanner;

public class ReturnMedia {
    static Scanner scanner = new Scanner(System.in);
    /**
     * Prompts the user to return media items.
     */
    public static void prompt() {
        try {
            // Prompt the user for their username
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();

            // Connect to the database
            try (Connection connection = dbConnect.getInstance().getConnection()) {
                // Print the contents of the "checkouts" column for the user
                String selectCheckoutsQuery = "SELECT checkouts FROM users WHERE username = ?";
                try (PreparedStatement checkoutsStatement = connection.prepareStatement(selectCheckoutsQuery)) {
                    checkoutsStatement.setString(1, username);
                    try (ResultSet resultSet = checkoutsStatement.executeQuery()) {
                        if (resultSet.next()) {
                            String checkouts = resultSet.getString("checkouts");
                            System.out.println("Items checked out by " + username + ": " + checkouts);
                        } else {
                            System.out.println("No checkouts found for user " + username);
                            return; // No checkouts found, exit method
                        }
                    }
                }

                // Prompt the user for the type of item they want to return
                System.out.print("What type of item would you like to return (Book, CD, DVD): ");
                String itemType = scanner.nextLine();

                // Prompt the user for the item(s) they want to return
                System.out.print("Enter the name of the item(s) you want to return (comma-separated if multiple): ");
                String itemNames = scanner.nextLine();

                // Split the item names if there are multiple items
                String[] items = itemNames.split(",");

                for (String item : items) {
                    // Check if the item exists in the specified table
                    String selectItemQuery = "";
                    switch (itemType.toLowerCase()) {
                        case "book":
                            selectItemQuery = "SELECT * FROM books WHERE title = ?";
                            break;
                        case "cd":
                            selectItemQuery = "SELECT * FROM albums WHERE album_name = ?";
                            break;
                        case "dvd":
                            selectItemQuery = "SELECT * FROM dvds WHERE title = ?";
                            break;
                        default:
                            System.out.println("Invalid item type!");
                            return;
                    }

                    try (PreparedStatement itemStatement = connection.prepareStatement(selectItemQuery)) {
                        itemStatement.setString(1, item.trim());
                        try (ResultSet resultSet = itemStatement.executeQuery()) {
                            if (resultSet.next()) {
                                // If the item exists, increase the quantity value
                                String updateQuery = "";
                                switch (itemType.toLowerCase()) {
                                    case "book":
                                        updateQuery = "UPDATE books SET quantity = quantity + 1 WHERE title = ?";
                                        break;
                                    case "cd":
                                        updateQuery = "UPDATE albums SET quantity = quantity + 1 WHERE album_name = ?";
                                        break;
                                    case "dvd":
                                        updateQuery = "UPDATE dvds SET quantity = quantity + 1 WHERE title = ?";
                                        break;
                                }

                                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                                    updateStatement.setString(1, item.trim());
                                    int rowsUpdated = updateStatement.executeUpdate();

                                    if (rowsUpdated > 0) {
                                        System.out.println(item.trim() + " returned successfully.");
                                        // Remove the returned item from the user's checkouts
                                        String updateCheckoutsQuery = "UPDATE users SET checkouts = REPLACE(checkouts, ?, '') WHERE username = ?";
                                        try (PreparedStatement updateCheckoutsStatement = connection.prepareStatement(updateCheckoutsQuery)) {
                                            updateCheckoutsStatement.setString(1, item.trim());
                                            updateCheckoutsStatement.setString(2, username);
                                            updateCheckoutsStatement.executeUpdate();
                                        }
                                    } else {
                                        System.out.println("Failed to return " + item.trim() + ".");
                                    }
                                }
                            } else {
                                System.out.println(item.trim() + " not found in the " + itemType + " database.");
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
