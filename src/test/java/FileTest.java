import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileTest {
    public static void main(String[] args){
        File file = new File("password.txt");
        String website = "google.com";
        String username = "yashwin";
        String password = "Abc123";
        PasswordEntry entry = new PasswordEntry(website, username, password);
        PasswordEntry entry1 = new PasswordEntry("chrome.com","bisen","7y7g87tf7");
        PasswordEntry entry2 = new PasswordEntry("github.com","yashwin", "idi7788g");
        PasswordEntry entry3 = new PasswordEntry("facebook.com","bisen123","y7ygut67r");
        String combine = entry.toString();
        ArrayList<PasswordEntry> entries = new ArrayList<>();
        entries.add(entry1);
        entries.add(entry2);
        entries.add(entry3);
        // for (PasswordEntry pass : entries) {
        //     System.out.println(pass.toString());
        // }

        try{
            FileWriter writer = new FileWriter(file, true);
            for (PasswordEntry pass : entries) {
                writer.write(pass.toString());
                writer.write(System.lineSeparator());
            }
            writer.close();
            System.out.println("Data written successfully.");
        }

        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
