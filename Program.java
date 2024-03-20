import java.util.*;
import java.io.*;

public class Program {
    // Scanner object for user input
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        File file;

        // Loop to prompt user until a valid file path is entered
        while (true) {
            System.out.print("Enter the path of the file you want to track: ");

            try {
                file = new File(scan.nextLine());

                // Check if the file exists
                if (!file.exists()) {
                    System.out.println("File not found");
                    continue;
                }
            } catch (Exception e) {
                System.out.println("Invalid input");
                continue;
            }

            break;
        }

        // Print the path of the file being tracked
        System.out.println("Currently tracking: " + file.getAbsolutePath());
        System.out.println();
        System.out.println("Commands:");
        System.out.println("history - Check list of checkpoints");
        System.out.println("undo - Revert to last checkpoint");
        System.out.println("exit - Terminates the program");
        System.out.println();

        // Create a new Tracker instance for the specified file
        Tracker tracker = new Tracker(file.getAbsolutePath());
        // Start tracking changes in a separate thread
        tracker.start();

        // Main loop to handle user commands
        while (true) {
            String input = scan.nextLine();

            // Switch statement to handle different commands
            switch (input) {
                case "history":
                    // Display history of checkpoints
                    for (Checkpoint ckpt: tracker.history) {
                        System.out.println(ckpt.date + " (" + ckpt.size + " bytes)");
                    }

                    break;
                case "undo":
                    // Signal to revert to last checkpoint
                    tracker.isUndo = true;
                    break;
                case "exit":
                    // Terminate the program
                    System.out.println("Terminating program...");
                    tracker.isTerminated = true;
                    return;
                default:
                    // Invalid command
                    System.out.println("Unknown command");
                    break;
            }
        }
    }
}