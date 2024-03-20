import java.util.*;
import java.io.*;
import java.text.*;

public class Checkpoint {
    public String name;
    public String date;
    public String content = "";
    public String path;
    public long size;

    // Constructor to create a checkpoint for a given file
    Checkpoint(File file) {
        name = file.getName();
        // Format the current date and time
        date = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
        
        // Read content of the file
        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                content += scan.nextLine() + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        path = file.getAbsolutePath();
        size = file.length();
    }
}