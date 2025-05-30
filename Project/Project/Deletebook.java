import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class Deletebook {
    private static final String BOOK_FILE = "C:\\Users\\huimi\\Downloads\\JavaProject\\book.txt"; // Update with your actual file path

    public void showDeleteBookPage(final JFrame parentFrame) {
        // Create a new frame for the Delete Book page
        final JFrame frame = new JFrame("Delete Book");
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.WHITE);

        // Header Label
        JLabel headerLabel = new JLabel("Delete Book by ISBN", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(Color.BLACK);
        headerLabel.setForeground(Color.WHITE);
        frame.add(headerLabel, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel isbnLabel = new JLabel("Enter ISBN:");
        final JTextField isbnField = new JTextField(); // Marked as final

        JButton deleteButton = new JButton("Delete");
        JButton backButton = new JButton("Back");

        inputPanel.add(isbnLabel);
        inputPanel.add(isbnField);
        inputPanel.add(deleteButton);
        inputPanel.add(backButton);

        frame.add(inputPanel, BorderLayout.CENTER);

        // Action Listener for Delete Button
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String isbn = isbnField.getText().trim();

                if (isbn.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "ISBN cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    boolean isDeleted = deleteBookByISBN(isbn);

                    if (isDeleted) {
                        JOptionPane.showMessageDialog(frame, "Book deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Book with ISBN " + isbn + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error accessing book file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action Listener for Back Button
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the Delete Book frame
            }
        });

        // Set frame properties
        frame.setLocationRelativeTo(parentFrame); // Center relative to the parent frame
        frame.setVisible(true);
    }

    private boolean deleteBookByISBN(String isbn) throws IOException {
        File bookFile = new File(BOOK_FILE);
        File tempFile = new File("temp_books.txt");

        BufferedReader reader = new BufferedReader(new FileReader(bookFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String line;
        boolean isDeleted = false;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",", 5); // ISBN, Title, Author, Year, Status
            if (parts[0].equals(isbn)) {
                isDeleted = true; // Found and skipped the book
                continue;
            }
            writer.write(line);
            writer.newLine();
        }

        reader.close();
        writer.close();

        if (isDeleted) {
            // Replace the original file with the updated file
            if (!bookFile.delete()) {
                throw new IOException("Failed to delete the original book file.");
            }
            if (!tempFile.renameTo(bookFile)) {
                throw new IOException("Failed to rename temporary file.");
            }
        } else {
            // Cleanup temporary file if no book was deleted
            tempFile.delete();
        }

        return isDeleted;
    }
}
