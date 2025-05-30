import java.awt.event.*;
import javax.swing.*;
import java.awt.Font;
import java.awt.Color;

class LoginPage implements ActionListener {
    JFrame frame;
    JButton adminButton, librarianButton;
    JLabel backgroundLabel, titleLabel;

    LoginPage() {
        frame = new JFrame("Login Page");
        frame.setSize(450, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        
        ImageIcon background = new ImageIcon("image/gif2.gif");
        backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 450, 450);
        
        titleLabel = new JLabel("Library Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK); // Set title text color to black
        titleLabel.setBounds(50, 30, 350, 50);
        
        adminButton = new JButton("Admin Login");
        librarianButton = new JButton("Librarian Login");
        
        adminButton.setBounds(70, 200, 150, 50);
        librarianButton.setBounds(230, 200, 150, 50);
        
        adminButton.setFocusable(false);
        librarianButton.setFocusable(false);
        
        adminButton.setBackground(Color.PINK);
        librarianButton.setBackground(Color.PINK);
        
        adminButton.addActionListener(this);
        librarianButton.addActionListener(this);
        
        // Adding hover effect
        addHoverEffect(adminButton);
        addHoverEffect(librarianButton);
        
        frame.add(titleLabel);
        frame.add(adminButton);
        frame.add(librarianButton);
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
                button.setBackground(Color.PINK);
                button.setForeground(Color.BLACK);
            }
        });
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == adminButton) {
            frame.dispose();
            new AdminLogin();
        } else if (e.getSource() == librarianButton) {
            frame.dispose();
            new LibrarianLogin();
        }
    }
}
