import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

public class Checkpoint {
    public String name;
    public String date;
    public String content = "";
    public String path;
    public long size;

    Checkpoint(File file) {
        name = file.getName();
        date = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
        
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