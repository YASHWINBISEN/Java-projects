import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
 
public class PasswordManager{
    private ArrayList<PasswordEntry> entries;

    public PasswordManager(){
        this.entries = new ArrayList<>();
        loadPassword();
    }

    private void loadPassword(){
        File file = new File("password.txt");

        if(file.exists()){
            try{
                FileReader read = new FileReader(file);
                BufferedReader  reader = new BufferedReader(read);
                String line = reader.readLine();

                while(line != null){
                    String[] parts = line.split("\\|", -1);
                    if(parts.length == 3){
                        try{
                            PasswordEntry splitted = new PasswordEntry(parts[0], parts[1], parts[2]);
                            entries.add(splitted);
                        }
                        catch(IllegalArgumentException e){
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    else{
                        System.out.println("Invalid line in password file.");
                    }
                    line = reader.readLine();
                }
                reader.close();

            }
            catch(IOException e){
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void savePasswords(){
        File file = new File("password.txt");
        try{
            FileWriter writer = new FileWriter(file);
            for(PasswordEntry pass : entries){
                writer.write(pass.toString());
                writer.write(System.lineSeparator());
            }
            writer.close();
        }
        catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public boolean addPassword(PasswordEntry entry){

        for(PasswordEntry existingEntry : entries){

            if(entry.getWebsite().equalsIgnoreCase(existingEntry.getWebsite())){
                return false;
            }
        }

        entries.add(entry);
        return true;
    }

    public void viewPasswords(){
        if(entries.isEmpty()){
            System.out.println("No passwords saved yet.");
            return;
        }
        for(PasswordEntry entry : entries){
            System.out.println(entry.getWebsite());
            System.out.println(entry.getUsername());
            System.out.println(entry.getPassword());
        }
    }

    public String getPasswordsAsText(){
        if(entries.isEmpty()){
            return "No passwords saved yet.";
        }

        String result = "";

        for(PasswordEntry entry : entries){

            result += "Website: " + entry.getWebsite() + "\n";
            result += "Username: " + entry.getUsername() + "\n";
            result += "Password: " + entry.getPassword() + "\n";
            result += "-------------------------\n";
        }

        return result;
    }

    public void searchPassword(String website){
        boolean found = false ;
        for(PasswordEntry entry : entries){
            if(entry.getWebsite().toLowerCase().contains(website.toLowerCase())){
                System.out.println(entry.getWebsite());
                System.out.println(entry.getUsername());
                System.out.println(entry.getPassword());
                found = true;
            }
        }
        if(!found){
            System.out.println("Password not found !");
        }
    }

    public String searchPasswordsAsText(String website){

        String result = "";

        for(PasswordEntry entry : entries){

            if(entry.getWebsite().toLowerCase().contains(website.toLowerCase())){

                result += "Website: " + entry.getWebsite() + "\n";
                result += "Username: " + entry.getUsername() + "\n";
                result += "Password: " + entry.getPassword() + "\n";
                result += "-------------------------\n";
            }
        }

        if(result.isEmpty()){
            return "Password not found!";
        }

        return result;
    }

    public boolean updatePassword(String website, String newPassword){

        for(PasswordEntry entry : entries){

            if(website.equalsIgnoreCase(entry.getWebsite())){

                try{
                    entry.setPassword(newPassword);
                    return true;
                }
                catch(IllegalArgumentException e){
                    return false;
                }
            }
        }

        return false;
    }

    public boolean deletePassword(String website){

        Iterator<PasswordEntry> it = entries.iterator();

        while(it.hasNext()){

            PasswordEntry entry = it.next();

            if(website.equalsIgnoreCase(entry.getWebsite())){

                it.remove();
                return true;
            }
        }

        return false;
    }
    
    public boolean websiteExists(String website){
        for(PasswordEntry existingEntry : entries){
            if(website.equalsIgnoreCase(existingEntry.getWebsite())){
                return true;
            }
        }
        return false;
    }
}
