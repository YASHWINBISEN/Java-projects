import java.security.SecureRandom;

public class PasswordGenerator{
    public static String generatePassword(int passwordLength, boolean  useUpper, boolean useLower, boolean useNumber, boolean useSymbol){
        // characters = " "
        String character = "";

        //  if uppercase selected add uppercase
        if(useUpper) {
            character += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        }

        //  if lowercase selected add uppercase
        if(useLower){
            character += "abcdefghijklmnopqrstuvwxyz";
        }
        
        //  if numbers selected add number
        if(useNumber){
            character += "0123456789";
        }
        //  if symbol selected add symbol
        if(useSymbol){
            character += "!@#$%^&*";
        }
        //  if all the parameter are false
        if(character.isEmpty()){
            return "";
        }

        String password = "";
        SecureRandom random = new SecureRandom();

        for(int i=0;i<passwordLength;i++){
            int randomIndex = random.nextInt(character.length());
            char randomChar = character.charAt(randomIndex);

            //  add charcter to password
            password += randomChar;
        }

        //  return password
        return password;
    }
}