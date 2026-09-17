import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        PasswordManager manager = new PasswordManager();

        int choice = 0;

        while(choice != 6){

            System.out.println("===== PASSWORD MANAGER =====");
            System.out.println("1. Add Password");
            System.out.println("2. View Password");
            System.out.println("3. Search Password");
            System.out.println("4. Update Password");
            System.out.println("5. Delete Password");
            System.out.println("6. Exit");
            System.out.println();

            System.out.println("Enter your choice: ");
            if(sc.hasNextInt()){
                choice = sc.nextInt();
                sc.nextLine();
            }
            else{
                sc.nextLine();
                System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                continue;
            }
            
            switch (choice) {
                case 1:
                    System.out.println("Add Password selected");
                    System.out.println("Enter the website name: ");
                    String website = sc.nextLine().trim();

                    if(website.isBlank()){
                        System.out.println("This cannot be empty. Enter the website name:");
                        continue;
                    }

                    if(manager.websiteExists(website)){
                        System.out.println("This website already exists.");
                        continue;
                    }

                    System.out.println("Enter the username: ");
                    String username = sc.nextLine().trim();
                    if(username.isBlank()){
                        System.out.println("This cannot be empty. Enter the username:");
                        continue;
                    }

                    System.out.println("Welcome To Password Generator");
                    System.out.println("Enter Password Length:");
                    int passwordLength = 0;
                    if(sc.hasNextInt()){
                        passwordLength = sc.nextInt();
                        sc.nextLine();
                    }
                    else{
                        sc.nextLine();
                        System.out.println("Invalid input. Enter a valid number:");
                        continue;
                    }

                    if(passwordLength<8||passwordLength>16){
                        System.out.println("Invalid password Length");
                        continue;
                    }   
                    
                    boolean useUpper = askBoolean(sc,"Include Uppercase? (true/false)") ;
                    boolean useLower = askBoolean(sc,"Include Lowercase? (true/false)") ;                
                    boolean useNumber = askBoolean(sc,"Include Numbers? (true/false)");
                    boolean useSymbol= askBoolean(sc,"Include Symbol? (true/false)");
                    
                    if(!useUpper && !useLower && !useNumber && !useSymbol){
                        System.out.println("At least select one character. ");
                        continue;
                    }

                    String newPassword = PasswordGenerator.generatePassword(passwordLength, useUpper, useLower, useNumber, useSymbol);               
                    System.out.println("Generated Password: " + newPassword);

                    try{
                        PasswordEntry entry = new PasswordEntry(website, username, newPassword);
                        manager.addPassword(entry);
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("View Password selected");
                    manager.viewPasswords();
                    break;

                case 3:
                    System.out.println("Search Password selected");
                    System.out.println("Enter the website name whose password you want to search:");
                    String web = sc.nextLine().trim();
                    if(web.isBlank()){
                        System.out.println("This cannot be empty. Enter the website name:");
                        continue;
                    }
                    manager.searchPassword(web);

                    break;

                case 4:
                    System.out.println("Update Password selected");
                    System.out.println("Enter the website name whose password you want to update:");
                    String up = sc.nextLine().trim();
                    if(up.isBlank()){
                        System.out.println("This cannot be empty. Enter the website name:");
                        continue;
                    }
                    System.out.println("Enter the password you want to replace with:");
                    String newP = sc.nextLine().trim();
                    if(newP.isBlank()){
                        System.out.println("This cannot be empty. Enter the password:");
                        continue;
                    }
                    manager.updatePassword(up,newP);

                    break;

                case 5:
                    System.out.println("Delete Password selected");
                    System.out.println("Enter the website name whose password you want to delete:");
                    String del = sc.nextLine().trim();
                    if(del.isBlank()){
                        System.out.println("This cannot be empty. Enter the website name:");
                        continue;
                    }
                    manager.deletePassword(del);

                    break;

                case 6:
                    manager.savePasswords();
                    System.out.println("Exit selected");
                    break;
                
                default :
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                    break;
            }
        }
    }

    static boolean askBoolean(Scanner sc, String prompt){
        while(true){
            System.out.println(prompt);
            if(sc.hasNextBoolean()){
                boolean cl = sc.nextBoolean();
                sc.nextLine();
                return cl;
            }
            else{
                sc.nextLine();
                System.out.println("Invalid input. Please enter true or false.");
            }
        }
    }

}