import javax.swing.*;
import java.awt.*;

public class PasswordManagerGUI {

    private static PasswordManager manager;

    public static void main(String[] args) {

        manager = new PasswordManager();

        SwingUtilities.invokeLater(() -> createGUI());
    }

    private static void createGUI() {

        JFrame frame = new JFrame("Password Manager");
        frame.setSize(550, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(35, 80, 35, 80));

        JLabel title = new JLabel("PASSWORD MANAGER");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Manage your passwords securely");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(5));
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(35));

        JButton addButton = createButton("Add Password");
        JButton viewButton = createButton("View Passwords");
        JButton searchButton = createButton("Search Password");
        JButton updateButton = createButton("Update Password");
        JButton deleteButton = createButton("Delete Password");
        JButton exitButton = createButton("Exit");

        panel.add(addButton);
        panel.add(Box.createVerticalStrut(12));
        panel.add(viewButton);
        panel.add(Box.createVerticalStrut(12));
        panel.add(searchButton);
        panel.add(Box.createVerticalStrut(12));
        panel.add(updateButton);
        panel.add(Box.createVerticalStrut(12));
        panel.add(deleteButton);
        panel.add(Box.createVerticalStrut(12));
        panel.add(exitButton);

        addButton.addActionListener(e -> addPassword(frame));
        viewButton.addActionListener(e -> viewPasswords(frame));
        searchButton.addActionListener(e -> searchPassword(frame));
        updateButton.addActionListener(e -> updatePassword(frame));
        deleteButton.addActionListener(e -> deletePassword(frame));

        exitButton.addActionListener(e -> {

            int choice = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to exit?",
                "Exit",
                JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                frame.dispose();
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private static JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(220, 40));
        button.setPreferredSize(new Dimension(220, 40));
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));

        return button;
    }

    private static void addPassword(JFrame frame) {

        String website = JOptionPane.showInputDialog(
            frame,
            "Enter website name:"
        );

        if (website == null || website.isBlank()) {
            JOptionPane.showMessageDialog(
                frame,
                "Website cannot be empty."
            );
            return;
        }

        website = website.trim();

        if (manager.websiteExists(website)) {
            JOptionPane.showMessageDialog(
                frame,
                "This website already exists."
            );
            return;
        }

        String username = JOptionPane.showInputDialog(
            frame,
            "Enter username:"
        );

        if (username == null || username.isBlank()) {
            JOptionPane.showMessageDialog(
                frame,
                "Username cannot be empty."
            );
            return;
        }

        username = username.trim();

        String lengthInput = JOptionPane.showInputDialog(
            frame,
            "Enter password length (8-16):"
        );

        if (lengthInput == null || lengthInput.isBlank()) {
            JOptionPane.showMessageDialog(
                frame,
                "Password length cannot be empty."
            );
            return;
        }

        int passwordLength;

        try {
            passwordLength = Integer.parseInt(lengthInput.trim());
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                frame,
                "Please enter a valid number."
            );
            return;
        }

        if (passwordLength < 8 || passwordLength > 16) {
            JOptionPane.showMessageDialog(
                frame,
                "Password length must be between 8 and 16."
            );
            return;
        }

        String password = PasswordGenerator.generatePassword(
            passwordLength,
            true,
            true,
            true,
            true
        );

        PasswordEntry entry = new PasswordEntry(
            website,
            username,
            password
        );

        manager.addPassword(entry);
        manager.savePasswords();

        JTextArea passwordArea = new JTextArea(password, 2, 20);
        passwordArea.setEditable(false);
        passwordArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        passwordArea.setLineWrap(true);
        passwordArea.setWrapStyleWord(true);

        JScrollPane passwordScrollPane = new JScrollPane(passwordArea);

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));

        JLabel successLabel = new JLabel("Password added successfully!");
        successLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel passwordLabel = new JLabel("Generated Password:");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton copyButton = new JButton("Copy Password");
        copyButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        copyButton.addActionListener(e -> {

            java.awt.datatransfer.StringSelection selection =
                new java.awt.datatransfer.StringSelection(password);

            Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(selection, null);

            JOptionPane.showMessageDialog(
                frame,
                "Password copied to clipboard."
            );
        });

        resultPanel.add(successLabel);
        resultPanel.add(Box.createVerticalStrut(10));
        resultPanel.add(passwordLabel);
        resultPanel.add(Box.createVerticalStrut(5));
        resultPanel.add(passwordScrollPane);
        resultPanel.add(Box.createVerticalStrut(10));
        resultPanel.add(copyButton);

        JOptionPane.showMessageDialog(
            frame,
            resultPanel,
            "Password Generated",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private static void viewPasswords(JFrame frame) {

        String passwords = manager.getPasswordsAsText();

        if (passwords.equals("No passwords saved yet.")) {
            JOptionPane.showMessageDialog(
                frame,
                "No passwords saved yet.",
                "Saved Passwords",
                JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        JTextArea textArea = createTextArea(passwords);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(550, 350));

        JOptionPane.showMessageDialog(
            frame,
            scrollPane,
            "Saved Passwords",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private static void searchPassword(JFrame frame) {

        String website = JOptionPane.showInputDialog(
            frame,
            "Enter website to search:"
        );

        if (website == null || website.isBlank()) {
            JOptionPane.showMessageDialog(
                frame,
                "Website cannot be empty."
            );
            return;
        }

        website = website.trim();

        String result = manager.searchPasswordsAsText(website);

        if (result.equals("Password not found!")) {
            JOptionPane.showMessageDialog(
                frame,
                "No matching website found.",
                "Search Result",
                JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        JTextArea textArea = createTextArea(result);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(550, 300));

        JOptionPane.showMessageDialog(
            frame,
            scrollPane,
            "Search Results",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private static void updatePassword(JFrame frame) {

        String website = JOptionPane.showInputDialog(
            frame,
            "Enter website whose password you want to update:"
        );

        if (website == null || website.isBlank()) {
            JOptionPane.showMessageDialog(
                frame,
                "Website cannot be empty."
            );
            return;
        }

        website = website.trim();

        if (!manager.websiteExists(website)) {
            JOptionPane.showMessageDialog(
                frame,
                "Website not found."
            );
            return;
        }

        String newPassword = JOptionPane.showInputDialog(
            frame,
            "Enter new password:"
        );

        if (newPassword == null || newPassword.isBlank()) {
            JOptionPane.showMessageDialog(
                frame,
                "Password cannot be empty."
            );
            return;
        }

        if (newPassword.length() < 8 || newPassword.length() > 16) {
            JOptionPane.showMessageDialog(
                frame,
                "Password length must be between 8 and 16."
            );
            return;
        }

        manager.updatePassword(website, newPassword);
        manager.savePasswords();

        JOptionPane.showMessageDialog(
            frame,
            "Password updated successfully."
        );
    }

    private static void deletePassword(JFrame frame) {

        String website = JOptionPane.showInputDialog(
            frame,
            "Enter website you want to delete:"
        );

        if (website == null || website.isBlank()) {
            JOptionPane.showMessageDialog(
                frame,
                "Website cannot be empty."
            );
            return;
        }

        website = website.trim();

        if (!manager.websiteExists(website)) {
            JOptionPane.showMessageDialog(
                frame,
                "Website not found."
            );
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
            frame,
            "Are you sure you want to delete the password for:\n" + website + "?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        manager.deletePassword(website);
        manager.savePasswords();

        JOptionPane.showMessageDialog(
            frame,
            "Password deleted successfully."
        );
    }

    private static JTextArea createTextArea(String text) {

        JTextArea textArea = new JTextArea(text, 15, 40);

        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        return textArea;
    }
}