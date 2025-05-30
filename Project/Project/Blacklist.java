/* 
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Blacklist {
    public Blacklist() {
        final JFrame frame = new JFrame("Blacklist Student");
        frame.setSize(500, 400);
        frame.setLayout(null);

        JLabel bookLabel = new JLabel("Enter Book ID:");
        bookLabel.setBounds(50, 50, 150, 25);
        final JTextField bookField = new JTextField();
        bookField.setBounds(200, 50, 200, 25);

        JLabel studentLabel = new JLabel("Enter Student ID:");
        studentLabel.setBounds(50, 100, 150, 25);
        final JTextField studentField = new JTextField();
        studentField.setBounds(200, 100, 200, 25);

        JButton submitButton = new JButton("Blacklist");
        submitButton.setBounds(150, 200, 100, 30);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(270, 200, 100, 30);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String bookId = bookField.getText().trim();
                String studentId = studentField.getText().trim();

                if (!bookId.isEmpty() && !studentId.isEmpty()) {
                    if (processBlacklist(bookId, studentId)) {
                        JOptionPane.showMessageDialog(frame, "Student and book blacklisted successfully.");
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid Book ID or Student ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.add(bookLabel);
        frame.add(bookField);
        frame.add(studentLabel);
        frame.add(studentField);
        frame.add(submitButton);
        frame.add(cancelButton);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private boolean processBlacklist(String bookId, String studentId) {
        boolean bookFound = false;
        boolean studentFound = false;
        String bookDetails = "";
        String studentDetails = "";

        // Read and remove book from txt/book.txt
        File bookFile = new File("txt/book.txt");
        File tempBookFile = new File("txt/book_temp.txt");

        try {
            BufferedReader bookReader = new BufferedReader(new FileReader(bookFile));
            BufferedWriter bookWriter = new BufferedWriter(new FileWriter(tempBookFile));
            String line;

            while ((line = bookReader.readLine()) != null) {
                String[] details = line.split(",");
                if (details[0].trim().equals(bookId)) {
                    bookFound = true;
                    bookDetails = line;
                    continue; // Skip writing this line to remove it
                }
                bookWriter.write(line);
                bookWriter.newLine();
            }

            bookReader.close();
            bookWriter.close();

            bookFile.delete();
            tempBookFile.renameTo(bookFile);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Read and remove student from txt/student.txt
        File studentFile = new File("txt/student.txt");
        File tempStudentFile = new File("txt/student_temp.txt");

        try {
            BufferedReader studentReader = new BufferedReader(new FileReader(studentFile));
            BufferedWriter studentWriter = new BufferedWriter(new FileWriter(tempStudentFile));
            String line;

            while ((line = studentReader.readLine()) != null) {
                String[] details = line.split(",");
                if (details[0].trim().equals(studentId)) {
                    studentFound = true;
                    studentDetails = line;
                    continue; // Skip writing this line to remove it
                }
                studentWriter.write(line);
                studentWriter.newLine();
            }

            studentReader.close();
            studentWriter.close();

            studentFile.delete();
            tempStudentFile.renameTo(studentFile);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Save to txt/blacklist.txt
        if (bookFound && studentFound) {
            try {
                BufferedWriter blacklistWriter = new BufferedWriter(new FileWriter("txt/blacklist.txt", true));
                blacklistWriter.write(bookDetails + "," + studentDetails);
                blacklistWriter.newLine();
                blacklistWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return true;
        }
        return false;
    }
}
*/