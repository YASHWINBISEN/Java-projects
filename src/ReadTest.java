import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadTest {
    public static void main(String args[]){
        File file = new File("password.txt");
        
        try{
            ArrayList<PasswordEntry> entries = new ArrayList<>();
            if(file.exists()){
                FileReader read = new FileReader(file);
                BufferedReader reader = new BufferedReader(read);
                String line = reader.readLine();
                
                while(line != null){
                    System.out.println(line);
                    String[] parts = line.split("\\|");
                    PasswordEntry splited = new PasswordEntry(parts[0], parts[1], parts[2]);
                    entries.add(splited);
                    line = reader.readLine();
                }
                reader.close();

                // for(PasswordEntry entry : entries){
                //     System.out.println(entry);
                // }
            }
            else{
                
            }
        }
        catch (IOException e){            
            System.out.println("Error: " + e.getMessage());
        }
    }
}