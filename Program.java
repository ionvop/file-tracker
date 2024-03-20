import java.util.*;
import java.io.*;

public class Program {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        File file;

        while (true) {
            System.out.print("Enter the path of the file you want to track: ");

            try {
                file = new File(scan.nextLine());

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

        System.out.println("Currently tracking: " + file.getAbsolutePath());
        System.out.println();
        System.out.println("Commands:");
        System.out.println("history - Check list of checkpoints");
        System.out.println("undo - Revert to last checkpoint");
        System.out.println("exit - Terminates the program");
        System.out.println();
        Tracker tracker = new Tracker(file.getAbsolutePath());
        tracker.start();

        while (true) {
            String input = scan.nextLine();

            switch (input) {
                case "history":
                    for (Checkpoint ckpt: tracker.history) {
                        System.out.println(ckpt.date + " (" + ckpt.size + " bytes)");
                    }

                    break;
                case "undo":
                    tracker.isUndo = true;
                    break;
                case "exit":
                    System.out.println("Terminating program...");
                    tracker.isTerminated = true;
                    return;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        }
    }

    public static String getArg(String[] args, String flag) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(flag)) {
                return args[i + 1];
            }
        }

        return null;
    }
}