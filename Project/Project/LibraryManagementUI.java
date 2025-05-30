import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LibraryManagementUI {
    public static void main(String[] args) {
        final JFrame frame = new JFrame("Librarian Panel"); // Declare 'final'
        frame.setSize(920, 647);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);  

        //wallpaper
        JLabel backgroundLabel;
        ImageIcon background = new ImageIcon("image/pic4.jpg");
        backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 920, 627);


        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome, Librarian!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setBounds(260, 25, 400, 50);

        // Buttons
        JButton addBookButton = new JButton("Add Book");
        JButton displayBooksButton = new JButton("Display Books");
        JButton deleteBookButton = new JButton("Delete Book");
        JButton borrowBookButton = new JButton("Borrow Book");
        JButton returnBookButton = new JButton("Return Book");
        JButton backButton = new JButton("Back");  

        // Set Button Size & Position
        addBookButton.setBounds(310, 120, 300, 50);
        displayBooksButton.setBounds(310, 190, 300, 50);
        deleteBookButton.setBounds(310, 260, 300, 50);
        borrowBookButton.setBounds(310, 340, 300, 50);
        returnBookButton.setBounds(310, 410, 300, 50);
        backButton.setBounds(310, 490, 300, 50);

        // Add Action Listeners
        addBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Addbook addBook = new Addbook();
                addBook.showAddBookPage(frame);
            }
        });

        displayBooksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Displaybooks displaybooks = new Displaybooks();
                displaybooks.showDisplayBooksPage(frame);
            }
        });

        deleteBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Deletebook deletebook = new Deletebook();
                deletebook.showDeleteBookPage(frame);
            }
        });

        borrowBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Borrowbook borrowbook = new Borrowbook();
                borrowbook.showBorrowBookPage(frame);
            }
        });

        returnBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Returnbook returnbook = new Returnbook();
                returnbook.showReturnBookPage(frame);
            }
        });

        // Back Button Functionality
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the Librarian Panel
                new LoginPage(); // Open the Login Page
            }
        });


        // Add Components to Frame
        frame.add(welcomeLabel);
        frame.add(addBookButton);
        frame.add(displayBooksButton);
        frame.add(deleteBookButton);
        frame.add(borrowBookButton);
        frame.add(returnBookButton);
        frame.add(backButton);

        frame.add(backgroundLabel);
    

        // Show Frame
        frame.setVisible(true);
    }
}
