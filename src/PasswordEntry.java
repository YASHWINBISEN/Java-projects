public class PasswordEntry{
    
    private String website;
    private String username;
    private String password;
    
    public PasswordEntry(String website, String username, String password){
        if(website.isBlank() || username.isBlank() || password.isBlank()){
            throw new IllegalArgumentException("You tried to create an invalid PasswordEntry: Website, username, and password cannot be blank.");
        }
        validatePassword(password);
        this.website = website;
        this.username = username;
        this.password = password;
    }

    public String getWebsite() {
        return website;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password){
        validatePassword(password);
        this.password = password;
    }

    public String toString(){
        return website + "|" + username + "|" + password;
    }

    private void validatePassword(String password){
        if(password.isBlank()){
            throw new IllegalArgumentException("Invalid Password");
        }
        if(password.length()<8||password.length()>16){
            throw new IllegalArgumentException("invalid password length");
        }
    }
}