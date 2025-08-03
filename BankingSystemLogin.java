import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class BankingSystemLogin {

    private static JFrame loginFrame;
    private static JTextField usernameField;
    private static JPasswordField passwordField;

    private static final HashMap<String, String> customerAccounts = new HashMap<>();
    private static final HashMap<String, Double> customerBalances = new HashMap<>();

    static {
        customerAccounts.put("john", "john123");
        customerAccounts.put("emma", "emma123");

        customerBalances.put("john", 5000.0);
        customerBalances.put("emma", 3500.0);
    }

    public static void main(String[] args) {
        showLoginUI();
    }

    private static void showLoginUI() {
        loginFrame = new JFrame("Customer Login");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);

        JButton loginButton = new JButton("Login");
        JButton resetButton = new JButton("Reset");

        gbc.gridx = 0; gbc.gridy = 0; panel.add(usernameLabel, gbc);
        gbc.gridx = 1; panel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(passwordLabel, gbc);
        gbc.gridx = 1; panel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(loginButton, gbc);
        gbc.gridx = 1; panel.add(resetButton, gbc);

        loginFrame.add(panel);
        loginFrame.setSize(350, 200);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);

        loginButton.addActionListener(e -> handleLogin());
        resetButton.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
        });
    }

    private static void handleLogin() {
        String username = usernameField.getText().toLowerCase().trim();
        String password = new String(passwordField.getPassword());

        if (customerAccounts.containsKey(username) && customerAccounts.get(username).equals(password)) {
            JOptionPane.showMessageDialog(loginFrame, "Login successful!");
            loginFrame.dispose();
            showDashboard(username);
        } else {
            JOptionPane.showMessageDialog(loginFrame, "Invalid username or password.");
        }
    }

    private static void showDashboard(String username) {
        JFrame dashboard = new JFrame("Customer Dashboard");
        JPanel panel = new JPanel(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel buttonPanel = new JPanel();
        JButton viewBalanceButton = new JButton("View Balance");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton logoutButton = new JButton("Logout");

        buttonPanel.add(viewBalanceButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(logoutButton);

        panel.add(welcomeLabel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        dashboard.add(panel);
        dashboard.setSize(450, 200);
        dashboard.setLocationRelativeTo(null);
        dashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashboard.setVisible(true);

        viewBalanceButton.addActionListener(e -> {
            double balance = customerBalances.getOrDefault(username, 0.0);
            JOptionPane.showMessageDialog(dashboard, "Your balance: ₹" + String.format("%.2f", balance));
        });

        depositButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(dashboard, "Enter amount to deposit:");
            if (input != null && input.matches("\\d+(\\.\\d{1,2})?")) {
                double amount = Double.parseDouble(input);
                customerBalances.put(username, customerBalances.getOrDefault(username, 0.0) + amount);
                JOptionPane.showMessageDialog(dashboard, "₹" + amount + " deposited successfully!");
            } else {
                JOptionPane.showMessageDialog(dashboard, "Invalid amount.");
            }
        });

        withdrawButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(dashboard, "Enter amount to withdraw:");
            if (input != null && input.matches("\\d+(\\.\\d{1,2})?")) {
                double amount = Double.parseDouble(input);
                double currentBalance = customerBalances.getOrDefault(username, 0.0);
                if (amount <= currentBalance) {
                    customerBalances.put(username, currentBalance - amount);
                    JOptionPane.showMessageDialog(dashboard, "₹" + amount + " withdrawn successfully!");
                } else {
                    JOptionPane.showMessageDialog(dashboard, "Insufficient balance!");
                }
            } else {
                JOptionPane.showMessageDialog(dashboard, "Invalid amount.");
            }
        });

        logoutButton.addActionListener(e -> {
            dashboard.dispose();
            showLoginUI();
        });
    }
}