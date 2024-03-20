import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class Tracker extends Thread {
    public String path;
    public Stack<Checkpoint> history = new Stack<>();
    public boolean isEnabled = true;
    public boolean isUndo = false;
    public boolean isTerminated = false;

    Tracker(String path) {
        this.path = path;
    }

    public void run() {
        File file = new File(path);
        history.push(new Checkpoint(file));

        while (isEnabled) {
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

            if (isUndo) {
                isUndo = false;

                if (history.size() <= 1) {
                    System.out.println("No other checkpoints left");
                    continue;
                }

                try (FileWriter write = new FileWriter(file)) {
                    write.write(history.pop().content.substring(0, history.peek().content.length() - 1));
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }

                Checkpoint ckpt = new Checkpoint(file);
                System.out.println(ckpt.date + " (" + ckpt.size + " bytes): Reverted to last checkpoint");
                continue;
            }

            Checkpoint ckpt = new Checkpoint(file);
            System.out.println(ckpt.date + " (" + ckpt.size + " bytes): Changes found");
            history.push(ckpt);
        }
    }
}