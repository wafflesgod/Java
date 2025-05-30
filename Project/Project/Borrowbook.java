import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class Borrowbook {
    private static final String BOOK_FILE = "C:\\Users\\huimi\\Downloads\\JavaProject\\txt_file\\book.txt"; // Update with your actual file path
    private static final String BORROWED_FILE = "C:\\Users\\huimi\\Downloads\\JavaProject\\txt_file\\borrow.txt"; // File to record borrowed books

    public void showBorrowBookPage(final JFrame parentFrame) {
        // Create a new frame for the Borrow Book page
        final JFrame frame = new JFrame("Borrow Book");
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.WHITE);

        // Header Label
        JLabel headerLabel = new JLabel("Borrow Book by ISBN", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(Color.BLACK);
        headerLabel.setForeground(Color.WHITE);
        frame.add(headerLabel, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel isbnLabel = new JLabel("Enter ISBN:");
        final JTextField isbnField = new JTextField();

        JLabel studentIdLabel = new JLabel("Enter Student ID:");
        final JTextField studentIdField = new JTextField();

        JLabel borrowDateLabel = new JLabel("Borrow Date (YYYY-MM-DD):");
        final JTextField borrowDateField = new JTextField();

        JLabel returnDateLabel = new JLabel("Return Date (YYYY-MM-DD):");
        final JTextField returnDateField = new JTextField();

        JButton borrowButton = new JButton("Borrow");
        JButton backButton = new JButton("Back");

        inputPanel.add(isbnLabel);
        inputPanel.add(isbnField);
        inputPanel.add(studentIdLabel);
        inputPanel.add(studentIdField);
        inputPanel.add(borrowDateLabel);
        inputPanel.add(borrowDateField);
        inputPanel.add(returnDateLabel);
        inputPanel.add(returnDateField);
        inputPanel.add(borrowButton);
        inputPanel.add(backButton);

        frame.add(inputPanel, BorderLayout.CENTER);

        // Action Listener for Borrow Button
        borrowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String isbn = isbnField.getText().trim();
                String studentId = studentIdField.getText().trim();
                String borrowDate = borrowDateField.getText().trim();
                String returnDate = returnDateField.getText().trim();

                if (isbn.isEmpty() || studentId.isEmpty() || borrowDate.isEmpty() || returnDate.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    boolean isBorrowed = borrowBook(isbn, studentId, borrowDate, returnDate);

                    if (isBorrowed) {
                        JOptionPane.showMessageDialog(frame, "Book borrowed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Book with ISBN " + isbn + " is not available or does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error accessing book file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action Listener for Back Button
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the Borrow Book frame
            }
        });

        // Set frame properties
        frame.setLocationRelativeTo(parentFrame); // Center relative to the parent frame
        frame.setVisible(true);
    }

    private boolean borrowBook(String isbn, String studentId, String borrowDate, String returnDate) throws IOException {
        File bookFile = new File(BOOK_FILE);
        File tempFile = new File("temp_books.txt");
        boolean isBorrowed = false;

        BufferedReader reader = new BufferedReader(new FileReader(bookFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",", 5); // ISBN, Title, Author, Year, Status

            if (parts[0].equals(isbn) && parts[4].equalsIgnoreCase("available")) {
                // Mark the book as "not available"
                parts[4] = "not available";
                isBorrowed = true;

                // Save borrow details in borrowed_books.txt
                saveBorrowDetails(isbn, studentId, borrowDate, returnDate);
            }

            // Manually join the array parts into a single line
            StringBuilder updatedLine = new StringBuilder();
            for (int i = 0; i < parts.length; i++) {
                updatedLine.append(parts[i]);
                if (i < parts.length - 1) {
                    updatedLine.append(",");
                }
            }

            writer.write(updatedLine.toString());
            writer.newLine();
        }

        reader.close();
        writer.close();

        if (isBorrowed) {
            // Replace the original file with the updated file
            if (!bookFile.delete()) {
                throw new IOException("Failed to delete the original book file.");
            }
            if (!tempFile.renameTo(bookFile)) {
                throw new IOException("Failed to rename temporary file.");
            }
        } else {
            // Cleanup temporary file if no book was borrowed
            tempFile.delete();
        }

        return isBorrowed;
    }

    private void saveBorrowDetails(String isbn, String studentId, String borrowDate, String returnDate) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(BORROWED_FILE, true));
        writer.write(isbn + "," + studentId + "," + borrowDate + "," + returnDate);
        writer.newLine();
        writer.close();
    }
}
