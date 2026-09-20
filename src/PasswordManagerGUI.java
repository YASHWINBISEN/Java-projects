import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;


public class PasswordManagerGUI {
    public static void main(String[] args){

        PasswordManager manager = new PasswordManager();

        JFrame frame = new JFrame("Password Manager");
        JLabel title = new JLabel("PASSWORD MANAGER");
        JButton addButton = new JButton("Add Password");
        JButton viewButton = new JButton("View Password");
        JButton searchButton = new JButton("Search Password");
        JButton updateButton = new JButton("Update Password");
        JButton deleteButton = new JButton("Delete Password");
        JButton exitButton = new JButton("Exit");
        JPanel panel = new JPanel();


        frame.setSize(500,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        title.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        addButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        viewButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        searchButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        updateButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        deleteButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(JButton.CENTER_ALIGNMENT);

        addButton.setPreferredSize(new Dimension(180, 35));
        viewButton.setPreferredSize(new Dimension(180, 35));
        searchButton.setPreferredSize(new Dimension(180, 35));
        updateButton.setPreferredSize(new Dimension(180, 35));
        deleteButton.setPreferredSize(new Dimension(180, 35));
        exitButton.setPreferredSize(new Dimension(180, 35));
        
        
        panel.add(title);
        panel.add(Box.createVerticalStrut(10));
        panel.add(addButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(viewButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(searchButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(updateButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(deleteButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(exitButton);
        panel.add(Box.createVerticalStrut(10));


        addButton.addActionListener(e -> {
            String website = JOptionPane.showInputDialog("Enter website name:");
            if(website == null || website.isBlank()){
                JOptionPane.showMessageDialog(null, "Website cannot be empty.");
                return;
            }
            if(manager.websiteExists(website)){
                JOptionPane.showMessageDialog(null, "This website already exists.");
                return;
            }

            String username = JOptionPane.showInputDialog("Enter username:");
            if(username == null || username.isBlank()){
                JOptionPane.showMessageDialog(null, "Username cannot be empty.");
                return;
            }

            String lengthInput = JOptionPane.showInputDialog("Enter password length (8-16):");
            if(lengthInput == null){
                return;
            }

            int passwordLength;

            try {
                passwordLength = Integer.parseInt(lengthInput);
            }
            catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                return;
            }  
            
            if(passwordLength < 8 || passwordLength > 16){
                JOptionPane.showMessageDialog(null, "Password length must be between 8 and 16.");
                return;
            }

            boolean useUpper = true;
            boolean useLower = true;
            boolean useNumber = true;
            boolean useSymbol = true;

            String password = PasswordGenerator.generatePassword(
                passwordLength,
                useUpper,
                useLower,
                useNumber,
                useSymbol
            );

            PasswordEntry entry = new PasswordEntry(website, username, password);
            manager.addPassword(entry);
            manager.savePasswords();
            
            System.out.println("Website: " + website);
            System.out.println("Username: " + username);
            System.out.println("Password Length: " + passwordLength);
            JOptionPane.showMessageDialog(null, "Generated Password: " + password);
        });

        viewButton.addActionListener(e -> {

            String passwords = manager.getPasswordsAsText();

            JTextArea textArea = new JTextArea(passwords, 15, 40);
            textArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textArea);

            JOptionPane.showMessageDialog(
                frame,
                scrollPane,
                "Saved Passwords",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        searchButton.addActionListener(e -> {

            String website = JOptionPane.showInputDialog(
                frame,
                "Enter website to search:"
            );

            if(website == null || website.isBlank()){
                JOptionPane.showMessageDialog(
                    frame,
                    "Website cannot be empty."
                );
                return;
            }

            String result = manager.searchPasswordsAsText(website);

            JOptionPane.showMessageDialog(
                frame,
                result,
                "Search Results",
                JOptionPane.INFORMATION_MESSAGE
            );
        });        

        updateButton.addActionListener(e -> {

            String website = JOptionPane.showInputDialog(
                frame,
                "Enter website whose password you want to update:"
            );

            if(website == null || website.isBlank()){
                JOptionPane.showMessageDialog(
                    frame,
                    "Website cannot be empty."
                );
                return;
            }

            String newPassword = JOptionPane.showInputDialog(
                frame,
                "Enter new password:"
            );

            if(newPassword == null || newPassword.isBlank()){
                JOptionPane.showMessageDialog(
                    frame,
                    "Password cannot be empty."
                );
                return;
            }

            if(newPassword.length() < 8 || newPassword.length() > 16){
                JOptionPane.showMessageDialog(
                    frame,
                    "Password length must be between 8 and 16."
                );
                return;
            }

            manager.updatePassword(website, newPassword);
            manager.savePasswords();

            System.out.println("Website to update: " + website);
            System.out.println("New password: " + newPassword);
        });   
             
        deleteButton.addActionListener(e -> {

            String website = JOptionPane.showInputDialog(
                frame,
                "Enter website you want to delete:"
            );

            if(website == null || website.isBlank()){
                JOptionPane.showMessageDialog(
                    frame,
                    "Website cannot be empty."
                );
                return;
            }

            int choice = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to delete this password?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
            );

            if(choice != JOptionPane.YES_OPTION){
                return;
            }
            
            manager.deletePassword(website);
            manager.savePasswords();
            
            System.out.println("Website to delete: " + website);
        });

        exitButton.addActionListener(e -> {System.out.println("Exit button clicked.");});

        frame.setVisible(true);
        

    }
}