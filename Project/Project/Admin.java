
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;

class Admin implements ActionListener {
    JFrame frame, addFrame;
    JLabel welcomeLabel, backgroundLabel;
    JButton addLibrarian, removeLibrarian, blacklistStudent, notReturnedBook, displayLibrarian,back;
    JTextField idField, nameField;
    JPasswordField passField;

    Admin() {
        frame = new JFrame("Admin Panel");
        frame.setSize(920, 627);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        ImageIcon background = new ImageIcon("image/pic4.jpg");
        backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 920, 627);

        welcomeLabel = new JLabel("Welcome, Admin!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setBounds(260, 25, 400, 50);

        addLibrarian = new JButton("Add Librarian");
        displayLibrarian = new JButton("Display & Delete Librarians");
        blacklistStudent = new JButton("Blacklist Student");
        notReturnedBook = new JButton("Check Not Returned Book");
        back = new JButton("Back");
        

        addLibrarian.setBounds(310, 130, 300, 50);
        blacklistStudent.setBounds(310, 370, 300, 50);
        notReturnedBook.setBounds(310, 290, 300, 50);
        displayLibrarian.setBounds(310, 210, 300, 50);
        back.setBounds(310, 450, 300, 50);

        addLibrarian.addActionListener(this);
        blacklistStudent.addActionListener(this);
        notReturnedBook.addActionListener(this);
        displayLibrarian.addActionListener(this);
        back.addActionListener(this);

        frame.add(welcomeLabel);
        frame.add(addLibrarian);
        frame.add(blacklistStudent);
        frame.add(notReturnedBook);
        frame.add(displayLibrarian);
        frame.add(back);

        frame.add(backgroundLabel);

        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addLibrarian) {
            addLibrarian(frame);
        } else if (e.getSource() == blacklistStudent) {
            displayBlacklist(frame);
        } else if (e.getSource() == notReturnedBook) {
            blacklistStudent(frame);
        } else if (e.getSource() == displayLibrarian) {
            displayLibrarian(frame);
        } else if (e.getSource() == back) {
            frame.dispose();
            new LoginPage();
        }
    }

    
//ADD LIBRARIAN

    private void addLibrarian(final JFrame adminFrame) {  // 'final' added to access in inner classes
    adminFrame.setVisible(false); // Hide the Admin Panel

    final JFrame addFrame = new JFrame("Add Librarian");
    addFrame.setSize(920, 627);
    addFrame.setLayout(null);

    JLabel idLabel = new JLabel("Librarian ID:");
    idLabel.setBounds(50, 30, 100, 25);
    final JTextField idField = new JTextField();
    idField.setBounds(160, 30, 180, 25);

    JLabel nameLabel = new JLabel("Name:");
    nameLabel.setBounds(50, 70, 100, 25);
    final JTextField nameField = new JTextField();
    nameField.setBounds(160, 70, 180, 25);

    JLabel ageLabel = new JLabel("Age:");
    ageLabel.setBounds(50, 110, 100, 25);
    final JTextField ageField = new JTextField();
    ageField.setBounds(160, 110, 180, 25);

    JLabel passLabel = new JLabel("Password:");
    passLabel.setBounds(50, 150, 100, 25);
    final JPasswordField passField = new JPasswordField();
    passField.setBounds(160, 150, 180, 25);

    JButton saveButton = new JButton("Save");
    saveButton.setBounds(100, 200, 80, 30);
    JButton cancelButton = new JButton("Cancel");
    cancelButton.setBounds(200, 200, 80, 30);

    saveButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String age = ageField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            if (!id.isEmpty() && !name.isEmpty() && !age.isEmpty() && !password.isEmpty()) {
                BufferedWriter writer = null;
                try {
                    writer = new BufferedWriter(new FileWriter("txt/librarian.txt", true));
                    writer.write(id + "," + name + "," + age + "," + password);
                    writer.newLine();
                    JOptionPane.showMessageDialog(addFrame, "Librarian added successfully.");
                    addFrame.dispose();
                    adminFrame.setVisible(true); // Restore Admin Panel when closing
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (writer != null) writer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(addFrame, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });

    cancelButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            addFrame.dispose();
            adminFrame.setVisible(true); // Restore Admin Panel
        }
    });

    addFrame.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            adminFrame.setVisible(true); // Restore Admin Panel
        }
    });

    addFrame.add(idLabel);
    addFrame.add(idField);
    addFrame.add(nameLabel);
    addFrame.add(nameField);
    addFrame.add(ageLabel);
    addFrame.add(ageField);
    addFrame.add(passLabel);
    addFrame.add(passField);
    addFrame.add(saveButton);
    addFrame.add(cancelButton);

    addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    addFrame.setVisible(true);
}

    
//Display BlackList Student

private void displayBlacklist(final JFrame adminFrame) {  // 'final' added
    adminFrame.setVisible(false); // Hide the admin panel instead of disposing it

    final JFrame blacklistFrame = new JFrame("Blacklisted Books & Students"); // 'final' added
    blacklistFrame.setSize(920, 627);
    blacklistFrame.setLayout(new BorderLayout());

    String[] columnNames = {"Book ID", "Book Name", "Blacklisted ID", "Blacklisted Name"};
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    JTable blacklistTable = new JTable(tableModel);

    BufferedReader reader = null;
    try {
        reader = new BufferedReader(new FileReader("txt/blacklist.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 7) {  // Ensure valid format
                tableModel.addRow(new Object[]{
                    parts[0].trim(),  // Book ID
                    parts[1].trim(),  // Book Name
                    parts[5].trim(),  // Student ID
                    parts[6].trim()   // Student Name
                });
            }
        }
    } catch (IOException ex) {
        ex.printStackTrace();
    } finally {
        try {
            if (reader != null) {
                reader.close();  // Manually close in Java 6
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    JScrollPane scrollPane = new JScrollPane(blacklistTable);
    blacklistFrame.add(scrollPane, BorderLayout.CENTER);

    // Back Button
    JButton backButton = new JButton("Back");
    backButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            blacklistFrame.dispose();  // Close the blacklist frame
            adminFrame.setVisible(true);  // Restore the admin panel
        }
    });

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(backButton);
    blacklistFrame.add(buttonPanel, BorderLayout.SOUTH);

    // Ensure Admin Panel reopens when closing the blacklist frame
    blacklistFrame.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            adminFrame.setVisible(true);  // Restore the Admin Panel
        }
    });

    blacklistFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    blacklistFrame.setVisible(true);
}

    
    //Display Librarian and Remove Librarian
    
    private void displayLibrarian(JFrame adminFrame) {
        adminFrame.dispose(); // Close the admin frame first
        
        final JFrame librarianFrame = new JFrame("Librarian List");
librarianFrame.setSize(920, 627);
librarianFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

String[] columnNames = {"ID", "Name"};
final DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
JTable librarianTable = new JTable(tableModel);

BufferedReader reader = null;
try {
    reader = new BufferedReader(new FileReader("txt/librarian.txt"));
    String line;
    while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length >= 2) {  // Ensure valid format
            tableModel.addRow(new Object[]{parts[0].trim(), parts[1].trim()});
        }
    }
} catch (IOException ex) {
    ex.printStackTrace();
} finally {
    try {
        if (reader != null) reader.close();
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}

JScrollPane scrollPane = new JScrollPane(librarianTable);
librarianFrame.add(scrollPane, BorderLayout.CENTER);

JPanel removePanel = new JPanel();
removePanel.setLayout(new FlowLayout());
JLabel removeLabel = new JLabel("Enter Librarian ID to remove:");
final JTextField removeField = new JTextField(10);
JButton removeButton = new JButton("Remove");

removeButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        String idToRemove = removeField.getText().trim();
        if (!idToRemove.isEmpty()) {
            try {
                File inputFile = new File("txt/librarian.txt");
                File tempFile = new File("txt/temp.txt");

                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                String line;
                boolean found = false;

                while ((line = reader.readLine()) != null) {
                    if (!line.startsWith(idToRemove + ",")) {
                        writer.write(line);
                        writer.newLine();
                    } else {
                        found = true;
                    }
                }
                reader.close();
                writer.close();

                inputFile.delete();
                tempFile.renameTo(inputFile);

                if (found) {
                    JOptionPane.showMessageDialog(librarianFrame, "Librarian removed successfully.");
                    
                    librarianFrame.dispose(); // Close the current frame before reopening
                    displayLibrarian(new JFrame("Admin Panel")); // Reopen updated frame
                } else {
                    JOptionPane.showMessageDialog(librarianFrame, "Librarian ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
});

removePanel.add(removeLabel);
removePanel.add(removeField);
removePanel.add(removeButton);
librarianFrame.add(removePanel, BorderLayout.SOUTH);

librarianFrame.addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        new Admin(); // Reopen the admin page when closing this frame
    }
});

librarianFrame.setVisible(true);
    }


   // Blacklist Student

    private void blacklistStudent(final JFrame adminFrame) {  // 'final' to use in inner classes
    adminFrame.setVisible(false); // Hide Admin Panel

    final JFrame frame = new JFrame("Blacklist Student");
    frame.setSize(920, 627);
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
                    adminFrame.setVisible(true); // Restore Admin Panel
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
            adminFrame.setVisible(true); // Restore Admin Panel
        }
    });

    frame.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            adminFrame.setVisible(true); // Restore Admin Panel
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