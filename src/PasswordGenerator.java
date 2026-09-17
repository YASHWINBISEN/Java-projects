import java.security.SecureRandom;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class PasswordGenerator{
    public static String generatePassword(int passwordLength, boolean  useUpper, boolean useLower, boolean useNumber, boolean useSymbol){
        // characters = " "
        StringBuilder character = new StringBuilder("");
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder("");
        int cnt = 0;

        int randomIndex ;
        char randomChar ;
        
        //  if uppercase selected add uppercase
        if(useUpper) {
            String upperCase ="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            character.append(upperCase);
            cnt++;
            randomIndex = random.nextInt(upperCase.length());
            randomChar = upperCase.charAt(randomIndex);
            password.append(randomChar);
        }

        //  if lowercase selected add uppercase
        if(useLower){
            String lowercase ="abcdefghijklmnopqrstuvwxyz";
            character.append(lowercase);
            cnt++;
            randomIndex = random.nextInt(lowercase.length());
            randomChar = lowercase.charAt(randomIndex);
            password.append(randomChar);
        }

        //  if numbers selected add number
        if(useNumber){
            String number = "0123456789";
            character.append(number);
            cnt++;
            randomIndex = random.nextInt(number.length());
            randomChar = number.charAt(randomIndex);
            password.append(randomChar);
        }

        //  if symbol selected add symbol
        if(useSymbol){
            String symbol = "!@#$%^&*";
            character.append(symbol);
            cnt++;
            randomIndex = random.nextInt(symbol.length());
            randomChar = symbol.charAt(randomIndex);
            password.append(randomChar);
        }

        //  if all the parameter are false
        if(character.isEmpty()){
            return "";
        }

        int requiredCount = cnt;
        int remaining = passwordLength - requiredCount;

        for(int i=0;i<remaining;i++){
            randomIndex = random.nextInt(character.length());
            randomChar = character.charAt(randomIndex);
            // changed random to securerandom
            //  add charcter to password
            password.append(randomChar);
        }

        //  return password
        return shuffleStringBuilder(password);
    }
    
    public static String shuffleStringBuilder(StringBuilder input){

        List<Character> characters = new ArrayList<>();

        for(int i=0;i<input.length(); i++){
            characters.add(input.charAt(i));
        }

        Collections.shuffle(characters);

        StringBuilder shuffled = new StringBuilder();
        for(char c : characters){
            shuffled.append(c);
        }

        return shuffled.toString();
    }
    
}