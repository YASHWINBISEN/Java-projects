import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome To Password Generator");
        System.out.println("Enter Password Length:");
        int passwordLength = sc.nextInt();

        System.out.println("Include Uppercase? (true/false)");
        boolean useUpper = sc.nextBoolean();

        System.out.println("Include Lowercase? (true/false)");
        boolean useLower = sc.nextBoolean();

        System.out.println("Include Numbers? (true/false)");
        boolean useNumber = sc.nextBoolean();

        System.out.println("Include Symbol? (true/false)");
        boolean useSymbol = sc.nextBoolean();


        // Generate a 12-character password with all options turned on
        String newPassword = PasswordGenerator.generatePassword(passwordLength, useUpper, useLower, useNumber, useSymbol);
        
        System.out.println("Generated Password: " + newPassword);
    }
}
