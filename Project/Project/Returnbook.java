import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Returnbook {

    public void showReturnBookPage(final JFrame parentFrame) {
        // Create a new frame for the Return Book page
        final JFrame frame = new JFrame("Return a Book");
        frame.setSize(400, 300);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.getContentPane().setBackground(Color.WHITE);

        // Title Label
        JLabel titleLabel = new JLabel("Return a Book");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.add(titleLabel);

        // ISBN input field
        JLabel isbnLabel = new JLabel("Enter Book ISBN:");
        final JTextField isbnField = new JTextField(); // Declare as final
        frame.add(isbnLabel);
        frame.add(isbnField);

        // Student ID input field
        JLabel studentIdLabel = new JLabel("Enter Student ID:");
        final JTextField studentIdField = new JTextField(); // Declare as final
        frame.add(studentIdLabel);
        frame.add(studentIdField);

        // Success message label
        final JLabel successLabel = new JLabel("");
        successLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        successLabel.setForeground(Color.GREEN);
        successLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.add(successLabel);

        // Return Button
        JButton returnButton = new JButton("Return Book");
        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.add(returnButton);

        // Back to Main Menu Button
        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.add(backButton);

        // Action Listener for Return Button
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String isbn = isbnField.getText().trim();
                String studentId = studentIdField.getText().trim();

                if (!isbn.isEmpty() && !studentId.isEmpty()) {
                    try {
                        // Perform the return action
                        boolean success = returnBook(isbn, studentId);
                        if (success) {
                            successLabel.setText("Book returned successfully!");
                            successLabel.setForeground(Color.GREEN);
                        } else {
                            successLabel.setText("No matching record found.");
                            successLabel.setForeground(Color.RED);
                        }
                    } catch (IOException ioException) {
                        successLabel.setText("Error returning the book.");
                        successLabel.setForeground(Color.RED);
                    }
                } else {
                    successLabel.setText("Please fill in all fields.");
                    successLabel.setForeground(Color.RED);
                }
            }
        });

        // Action Listener for Back Button
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the Return Book window
            }
        });

        // Set frame properties
        frame.setLocationRelativeTo(parentFrame);
        frame.setVisible(true);
    }

    public boolean returnBook(String isbn, String studentId) throws IOException {
        boolean bookReturned = false;

        // Update the status of the book in book.txt
        File bookFile = new File("C:\\Users\\huimi\\Downloads\\JavaProject\\txt_file\\book.txt");
        File tempBookFile = new File("C:\\Users\\huimi\\Downloads\\JavaProject\\txt_file\\book_temp.txt");
        BufferedReader bookReader = new BufferedReader(new FileReader(bookFile));
        BufferedWriter bookWriter = new BufferedWriter(new FileWriter(tempBookFile));

        String line;
        while ((line = bookReader.readLine()) != null) {
            String[] bookDetails = line.split(",");
            if (bookDetails[0].equals(isbn)) {
                // Update the book status to "available"
                bookDetails[4] = "available"; // Assuming the status is at index 4
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < bookDetails.length; i++) {
                    sb.append(bookDetails[i]);
                    if (i < bookDetails.length - 1) {
                        sb.append(",");
                    }
                }
                line = sb.toString();
                bookReturned = true; // Mark as book found and updated
            }
            bookWriter.write(line);
            bookWriter.newLine();
        }

        bookReader.close();
        bookWriter.close();
        bookFile.delete(); // Delete the original book.txt
        tempBookFile.renameTo(bookFile); // Rename the temporary file to book.txt

        // Remove the borrow record from borrow.txt
        File borrowFile = new File("C:\\Users\\huimi\\Downloads\\JavaProject\\txt_file\\borrow.txt");
        File tempBorrowFile = new File("C:\\Users\\huimi\\Downloads\\JavaProject\\txt_file\\borrow_temp.txt");
        BufferedReader borrowReader = new BufferedReader(new FileReader(borrowFile));
        BufferedWriter borrowWriter = new BufferedWriter(new FileWriter(tempBorrowFile));

        while ((line = borrowReader.readLine()) != null) {
            String[] borrowDetails = line.split(",");
            if (borrowDetails[0].equals(isbn) && borrowDetails[1].equals(studentId)) {
                // Don't write this record to the temporary file (effectively deletes it)
                continue;
            }
            borrowWriter.write(line);
            borrowWriter.newLine();
        }

        borrowReader.close();
        borrowWriter.close();
        borrowFile.delete(); // Delete the original borrow.txt
        tempBorrowFile.renameTo(borrowFile); // Rename the temporary file to borrow.txt

        return bookReturned;
    }
}
