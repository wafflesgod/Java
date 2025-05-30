import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Addbook {
    private static final String BOOK_FILE = "C:\\Users\\huimi\\Downloads\\JavaProject\\txt_file\\book.txt";

    public void showAddBookPage(final JFrame parentFrame) {
        // Create a new frame for adding books
        final JFrame frame = new JFrame("Add Book");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Add New Book", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        final JLabel isbnLabel = new JLabel("ISBN:");
        final JTextField isbnField = new JTextField();

        final JLabel titleFieldLabel = new JLabel("Title:");
        final JTextField titleField = new JTextField();

        final JLabel authorLabel = new JLabel("Author:");
        final JTextField authorField = new JTextField();

        final JLabel yearLabel = new JLabel("Year of Publish:");
        final JTextField yearField = new JTextField();

        final JLabel statusLabel = new JLabel("Status:");
        final JComboBox statusBox = new JComboBox(new String[]{"available", "not available"}); // No diamond operator in Java 6

        formPanel.add(isbnLabel);
        formPanel.add(isbnField);
        formPanel.add(titleFieldLabel);
        formPanel.add(titleField);
        formPanel.add(authorLabel);
        formPanel.add(authorField);
        formPanel.add(yearLabel);
        formPanel.add(yearField);
        formPanel.add(statusLabel);
        formPanel.add(statusBox);

        frame.add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Book");
        JButton backButton = new JButton("Back");
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String isbn = isbnField.getText().trim();
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                String year = yearField.getText().trim();
                String status = statusBox.getSelectedItem().toString();

                // Validate input
                if (isbn.isEmpty() || title.isEmpty() || author.isEmpty() || year.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    // Append the book data to the book.txt file
                    BufferedWriter writer = new BufferedWriter(new FileWriter(BOOK_FILE, true));
                    writer.write(isbn + "," + title + "," + author + "," + year + "," + status);
                    writer.newLine();
                    writer.close();

                    JOptionPane.showMessageDialog(frame, "Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Clear the fields
                    isbnField.setText("");
                    titleField.setText("");
                    authorField.setText("");
                    yearField.setText("");
                    statusBox.setSelectedIndex(0);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error writing to file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the Add Book window
            }
        });

        // Set frame properties
        frame.setLocationRelativeTo(parentFrame);
        frame.setVisible(true);
    }
}
