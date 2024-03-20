import java.util.*;
import java.io.*;

public class Tracker extends Thread {
    public String path;
    public Stack<Checkpoint> history = new Stack<>();
    public boolean isEnabled = true;
    public boolean isUndo = false;
    public boolean isTerminated = false;

    // Constructor to initialize the Tracker with a file path
    Tracker(String path) {
        this.path = path;
    }

    // Thread's run method to track changes in the file
    public void run() {
        File file = new File(path);
        // Create initial checkpoint
        history.push(new Checkpoint(file));

        // Continuous loop to monitor file changes
        while (isEnabled) {
            // Wait until content of file changes or undo is requested
            while (history.peek().content.equals(new Checkpoint(file).content) && !isUndo && !isTerminated) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }

            if (isTerminated) {
                return;
            }

            // Handle undo operation
            if (isUndo) {
                isUndo = false;

                // Check if there are previous checkpoints to revert to
                if (history.size() <= 1) {
                    System.out.println("No other checkpoints left");
                    continue;
                }

                try (FileWriter write = new FileWriter(file)) {
                    // Revert to last checkpoint
                    write.write(history.pop().content.substring(0, history.peek().content.length() - 1));
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }

                // Create a new checkpoint after undo operation
                Checkpoint ckpt = new Checkpoint(file);
                System.out.println(ckpt.date + " (" + ckpt.size + " bytes): Reverted to last checkpoint");
                continue;
            }

            // Create a new checkpoint if changes are detected
            Checkpoint ckpt = new Checkpoint(file);
            System.out.println(ckpt.date + " (" + ckpt.size + " bytes): Changes found");
            history.push(ckpt);
        }
    }
}