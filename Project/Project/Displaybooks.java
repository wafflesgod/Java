import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Displaybooks {
    private static final String BOOK_FILE = "C:\\Users\\huimi\\Downloads\\JavaProject\\txt_file\\book.txt";

    public void showDisplayBooksPage(final JFrame parentFrame) {
        // Create a new frame for displaying books
        final JFrame frame = new JFrame("Display Books");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Create column names and data
        String[] columns = {"ISBN", "Title", "Author", "Year", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        BufferedReader reader = null; // Declare reader outside the try block
        try {
            reader = new BufferedReader(new FileReader(BOOK_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 5); // ISBN, Title, Author, Year, Status
                model.addRow(parts); // Add book data as a new row
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error reading book file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error closing book file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Create table and add it to a scroll pane
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Create a back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the Display Books window
            }
        });

        // Add the button to a panel and add the panel to the frame
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Set frame properties
        frame.setLocationRelativeTo(parentFrame);
        frame.setVisible(true);
    }
}
