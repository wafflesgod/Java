import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Scanner;

class AdminLogin implements ActionListener {
    JFrame frame;
    JLabel userLabel, passLabel, backgroundLabel;
    JTextField userText;
    JPasswordField passText;
    JButton loginButton, backButton;


    AdminLogin() {
        frame = new JFrame("Admin Login");
        userLabel = new JLabel("Admin ID:");
        userText = new JTextField();
        passLabel = new JLabel("Password:");
        passText = new JPasswordField();
        loginButton = new JButton("Login");
        backButton = new JButton("Back");
    
        // Define fonts before using them
        Font labelFont = new Font("Arial",Font.PLAIN,16); 
        Font buttonFont = new Font("Arial",Font.PLAIN,16);
    
        userLabel.setBounds(80, 80, 100, 25);
        userText.setBounds(200, 80, 170, 25);
        passLabel.setBounds(80, 120, 100, 25);
        passText.setBounds(200, 120, 170, 25);
        loginButton.setBounds(80, 250, 100, 30);
        backButton.setBounds(250, 250, 100, 30);
    
        userLabel.setFont(labelFont);
        passLabel.setFont(labelFont);
        userLabel.setForeground(Color.WHITE);
        passLabel.setForeground(Color.WHITE);
    
        loginButton.setFont(buttonFont);
        backButton.setFont(buttonFont);
    
        loginButton.setBackground(Color.white);
        loginButton.setForeground(Color.BLACK);
        backButton.setBackground(Color.white);
        backButton.setForeground(Color.BLACK);
        
        loginButton.addActionListener(this);
        backButton.addActionListener(this);
    
        addHoverEffect(loginButton);
        addHoverEffect(backButton);
    
        frame.add(userLabel);
        frame.add(userText);
        frame.add(passLabel);
        frame.add(passText);
        frame.add(loginButton);
        frame.add(backButton);
    
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 450);
        frame.setLayout(null);
        frame.setVisible(true);
    
        ImageIcon background = new ImageIcon("image/pic2.jpg");
        backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 450, 450);
        
        frame.add(backgroundLabel);
        
        frame.setVisible(true);
    }
    

    private void addHoverEffect(final JButton button) {
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.BLACK);
                button.setForeground(Color.WHITE);
            }
    
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.white);
                button.setForeground(Color.BLACK);
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            frame.dispose();
            new LoginPage();
        } else if (e.getSource() == loginButton) {
            String enteredUser = userText.getText();
            String enteredPass = new String(passText.getPassword());
            if (validateAdmin(enteredUser, enteredPass)) {
                frame.dispose();
                new Admin();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validateAdmin(String user, String pass) {
        try {
            File file = new File("admin.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String[] credentials = scanner.nextLine().split(",");
                if (credentials.length == 2 && credentials[0].trim().equals(user) && credentials[1].trim().equals(pass)) {
                    scanner.close();
                    return true;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}