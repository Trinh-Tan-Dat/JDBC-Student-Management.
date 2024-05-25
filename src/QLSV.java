import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class QLSV_21520714_TrinhTanDat extends JFrame {
    private JTable studentTable;
    private JTable classTable;
    private DefaultTableModel studentTableModel;
    private DefaultTableModel classTableModel;

    private JTextField maSVField;
    private JTextField hoTenField;
    private JTextField lopField;
    private JTextField diemTBField;

    private JTextField maLopField;
    private JTextField tenLopField;
    private JTextField cvhtField;

    private JComboBox<String> searchOptions;
    private JTextField searchField1;
    private JTextField searchField2;
    private JButton searchButton;
    private JPanel searchPanel;

    private JRadioButton sortAscButton;
    private JRadioButton sortDescButton;
    private ButtonGroup sortGroup;

    private JButton countStudentsButton;
    private JButton mostStudentsButton;
    private JButton highestAvgScoreButton;
    private JButton mostFailedStudentsButton;

    public QLSV_21520714_TrinhTanDat() {
        setTitle("Quan ly sinh vien va lop");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel studentPanel = new JPanel(new BorderLayout());
        studentTableModel = new DefaultTableModel();
        studentTable = new JTable(studentTableModel);
        JScrollPane studentScrollPane = new JScrollPane(studentTable);
        studentPanel.add(studentScrollPane, BorderLayout.CENTER);

        JPanel studentControlPanel = new JPanel();
        studentControlPanel.setLayout(new GridLayout(5, 2));
        studentControlPanel.add(new JLabel("MaSV"));
        maSVField = new JTextField();
        studentControlPanel.add(maSVField);
        studentControlPanel.add(new JLabel("HoTen"));
        hoTenField = new JTextField();
        studentControlPanel.add(hoTenField);
        studentControlPanel.add(new JLabel("Lop"));
        lopField = new JTextField();
        studentControlPanel.add(lopField);
        studentControlPanel.add(new JLabel("DiemTB"));
        diemTBField = new JTextField();
        studentControlPanel.add(diemTBField);
        JButton addStudentButton = new JButton("Them");
        JButton deleteStudentButton = new JButton("Xoa");
        JButton updateStudentButton = new JButton("Sua");
        studentControlPanel.add(addStudentButton);
        studentControlPanel.add(deleteStudentButton);
        studentControlPanel.add(updateStudentButton);
        studentPanel.add(studentControlPanel, BorderLayout.SOUTH);

        searchPanel = new JPanel();
        searchOptions = new JComboBox<>(new String[] { "MaSV", "HoTen", "Lop", "DiemTB" });
        searchPanel.add(searchOptions);
        searchField1 = new JTextField(10);
        searchPanel.add(searchField1);
        searchField2 = new JTextField(10);
        searchPanel.add(searchField2);
        searchField2.setVisible(false);
        searchButton = new JButton("Tim");
        searchPanel.add(searchButton);
        studentPanel.add(searchPanel, BorderLayout.NORTH);

        JPanel sortPanel = new JPanel();
        sortAscButton = new JRadioButton("Sap xep tang dan");
        sortDescButton = new JRadioButton("Sap xep giam dan");
        sortGroup = new ButtonGroup();
        sortGroup.add(sortAscButton);
        sortGroup.add(sortDescButton);
        sortPanel.add(sortAscButton);
        sortPanel.add(sortDescButton);
        studentPanel.add(sortPanel, BorderLayout.EAST);

        JPanel queryPanel = new JPanel(new GridLayout(4, 1));
        countStudentsButton = new JButton("So sinh vien moi lop hoc");
        mostStudentsButton = new JButton("Lop co nhieu sinh vien nhat");
        highestAvgScoreButton = new JButton("Lop co diem trung binh cao nhat");
        mostFailedStudentsButton = new JButton("Lop co nhieu sinh vien khong dat");
        queryPanel.add(countStudentsButton);
        queryPanel.add(mostStudentsButton);
        queryPanel.add(highestAvgScoreButton);
        queryPanel.add(mostFailedStudentsButton);
        studentPanel.add(queryPanel, BorderLayout.WEST);

        tabbedPane.addTab("Sinh Vien", studentPanel);

        JPanel classPanel = new JPanel(new BorderLayout());
        classTableModel = new DefaultTableModel();
        classTable = new JTable(classTableModel);
        JScrollPane classScrollPane = new JScrollPane(classTable);
        classPanel.add(classScrollPane, BorderLayout.CENTER);

        JPanel classControlPanel = new JPanel();
        classControlPanel.setLayout(new GridLayout(4, 2));
        classControlPanel.add(new JLabel("MaLop"));
        maLopField = new JTextField();
        classControlPanel.add(maLopField);
        classControlPanel.add(new JLabel("TenLop"));
        tenLopField = new JTextField();
        classControlPanel.add(tenLopField);
        classControlPanel.add(new JLabel("CVHT"));
        cvhtField = new JTextField();
        classControlPanel.add(cvhtField);
        JButton addClassButton = new JButton("Them");
        JButton deleteClassButton = new JButton("Xoa");
        JButton updateClassButton = new JButton("Sua");
        classControlPanel.add(addClassButton);
        classControlPanel.add(deleteClassButton);
        classControlPanel.add(updateClassButton);
        classPanel.add(classControlPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Danh sach lop", classPanel);

        add(tabbedPane, BorderLayout.CENTER);

        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = tabbedPane.getSelectedIndex();
                if (selectedIndex == 0) {
                    loadStudentData();
                } else if (selectedIndex == 1) {
                    loadClassData();
                }
            }
        });

        loadStudentData();

        addStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        deleteStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });

        updateStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateStudent();
            }
        });

        addClassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addClass();
            }
        });

        deleteClassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteClass();
            }
        });

        updateClassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateClass();
            }
        });

        searchOptions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateSearchFields();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchStudent();
            }
        });

        sortAscButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadStudentData();
            }
        });

        sortDescButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadStudentData();
            }
        });

        countStudentsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                countStudentsPerClass();
            }
        });

        mostStudentsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findClassWithMostStudents();
            }
        });

        highestAvgScoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findClassWithHighestAvgScore();
            }
        });

        mostFailedStudentsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findClassWithMostFailedStudents();
            }
        });
    }

    private void updateSearchFields() {
        String selectedOption = (String) searchOptions.getSelectedItem();
        if ("DiemTB".equals(selectedOption)) {
            searchField2.setVisible(true);
        } else {
            searchField2.setVisible(false);
        }
        searchPanel.revalidate();
        searchPanel.repaint();
    }

    private void searchStudent() {
        String selectedOption = (String) searchOptions.getSelectedItem();
        String searchValue1 = searchField1.getText();
        String searchValue2 = searchField2.getText();

        String url = "jdbc:mysql://localhost:3306/QLSV";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);
            String query = null;
            PreparedStatement pstmt = null;

            if ("MaSV".equals(selectedOption)) {
                query = "SELECT * FROM SinhVien WHERE MaSV = ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, searchValue1);
            } else if ("HoTen".equals(selectedOption)) {
                query = "SELECT * FROM SinhVien WHERE HoTen LIKE ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, "%" + searchValue1 + "%");
            } else if ("Lop".equals(selectedOption)) {
                query = "SELECT * FROM SinhVien WHERE Lop LIKE ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, "%" + searchValue1 + "%");
            } else if ("DiemTB".equals(selectedOption)) {
                query = "SELECT * FROM SinhVien WHERE DiemTB BETWEEN ? AND ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, searchValue1);
                pstmt.setString(2, searchValue2);
            }

            ResultSet rs = pstmt.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columnNames[i] = metaData.getColumnName(i + 1);
            }
            studentTableModel.setColumnIdentifiers(columnNames);

            studentTableModel.setRowCount(0);

            while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = rs.getObject(i + 1);
                }
                studentTableModel.addRow(rowData);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadStudentData() {
        String url = "jdbc:mysql://localhost:3306/QLSV";
        String user = "root";
        String password = "";
        boolean sortAsc = sortAscButton.isSelected();
        String sortOrder = sortAsc ? "ASC" : "DESC";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);
            String query = "SELECT * FROM SinhVien ORDER BY DiemTB " + sortOrder;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columnNames[i] = metaData.getColumnName(i + 1);
            }
            studentTableModel.setColumnIdentifiers(columnNames);

            studentTableModel.setRowCount(0);

            while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = rs.getObject(i + 1);
                }
                studentTableModel.addRow(rowData);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadClassData() {
        String url = "jdbc:mysql://localhost:3306/QLSV";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);
            String query = "SELECT * FROM Lop";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columnNames[i] = metaData.getColumnName(i + 1);
            }
            classTableModel.setColumnIdentifiers(columnNames);

            classTableModel.setRowCount(0);

            while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = rs.getObject(i + 1);
                }
                classTableModel.addRow(rowData);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addStudent() {
        String url = "jdbc:mysql://localhost:3306/QLSV";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);
            String query = "INSERT INTO SinhVien (MaSV, HoTen, Lop, DiemTB) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, Integer.parseInt(maSVField.getText()));
            pstmt.setString(2, hoTenField.getText());
            pstmt.setString(3, lopField.getText());
            pstmt.setFloat(4, Float.parseFloat(diemTBField.getText()));
            pstmt.executeUpdate();

            loadStudentData();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void deleteStudent() {
        String url = "jdbc:mysql://localhost:3306/QLSV";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);
            String query = "DELETE FROM SinhVien WHERE MaSV = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, Integer.parseInt(maSVField.getText()));
            pstmt.executeUpdate();

            loadStudentData();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateStudent() {
        String url = "jdbc:mysql://localhost:3306/QLSV";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);
            String query = "UPDATE SinhVien SET HoTen = ?, Lop = ?, DiemTB = ? WHERE MaSV = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, hoTenField.getText());
            pstmt.setString(2, lopField.getText());
            pstmt.setFloat(3, Float.parseFloat(diemTBField.getText()));
            pstmt.setInt(4, Integer.parseInt(maSVField.getText()));
            pstmt.executeUpdate();

            loadStudentData();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addClass() {
        String url = "jdbc:mysql://localhost:3306/QLSV";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);
            String query = "INSERT INTO Lop (MaLop, TenLop, CVHT) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, Integer.parseInt(maLopField.getText()));
            pstmt.setString(2, tenLopField.getText());
            pstmt.setString(3, cvhtField.getText());
            pstmt.executeUpdate();

            loadClassData();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void deleteClass() {
        String url = "jdbc:mysql://localhost:3306/QLSV";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);
            String query = "DELETE FROM Lop WHERE MaLop = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, Integer.parseInt(maLopField.getText()));
            pstmt.executeUpdate();

            loadClassData();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateClass() {
        String url = "jdbc:mysql://localhost:3306/QLSV";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);
            String query = "UPDATE Lop SET TenLop = ?, CVHT = ? WHERE MaLop = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, tenLopField.getText());
            pstmt.setString(2, cvhtField.getText());
            pstmt.setInt(3, Integer.parseInt(maLopField.getText()));
            pstmt.executeUpdate();

            loadClassData();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // private void countStudentsPerClass() {
    // String url = "jdbc:mysql://localhost:3306/QLSV";
    // String user = "root";
    // String password = "";

    // try {
    // Class.forName("com.mysql.cj.jdbc.Driver");

    // Connection conn = DriverManager.getConnection(url, user, password);
    // String query = "SELECT Lop, COUNT(*) AS SoLuongSinhVien FROM SinhVien GROUP
    // BY Lop";
    // Statement stmt = conn.createStatement();
    // ResultSet rs = stmt.executeQuery(query);

    // // Display the result in a new window
    // DefaultTableModel resultTableModel = new DefaultTableModel();
    // JTable resultTable = new JTable(resultTableModel);
    // resultTableModel.addColumn("Lop");
    // resultTableModel.addColumn("SoLuongSinhVien");

    // while (rs.next()) {
    // Object[] rowData = new Object[2];
    // rowData[0] = rs.getString("Lop");
    // rowData[1] = rs.getInt("SoLuongSinhVien");
    // resultTableModel.addRow(rowData);
    // }

    // JOptionPane.showMessageDialog(null, new JScrollPane(resultTable), "Student
    // Count per Class",
    // JOptionPane.INFORMATION_MESSAGE);

    // } catch (SQLException | ClassNotFoundException e) {
    // e.printStackTrace();
    // }
    // }

    // Bo sung Cau 6
    private void countStudentsPerClass() {
        String url = "jdbc:mysql://localhost:3306/QLSV";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);
            String query = "{CALL CountStudentsPerClass()}";
            CallableStatement cstmt = conn.prepareCall(query);
            ResultSet rs = cstmt.executeQuery();

            DefaultTableModel resultTableModel = new DefaultTableModel();
            JTable resultTable = new JTable(resultTableModel);
            resultTableModel.addColumn("Lop");
            resultTableModel.addColumn("SoLuongSinhVien");

            while (rs.next()) {
                Object[] rowData = new Object[2];
                rowData[0] = rs.getString("Lop");
                rowData[1] = rs.getInt("SoLuongSinhVien");
                resultTableModel.addRow(rowData);
            }

            JOptionPane.showMessageDialog(null, new JScrollPane(resultTable), "Sinh vien moi lop",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // private void findClassWithMostStudents() {
    // String url = "jdbc:mysql://localhost:3306/QLSV";
    // String user = "root";
    // String password = "";

    // try {
    // Class.forName("com.mysql.cj.jdbc.Driver");

    // Connection conn = DriverManager.getConnection(url, user, password);
    // String query = "SELECT Lop, COUNT(*) AS SoLuongSinhVien FROM SinhVien GROUP
    // BY Lop ORDER BY SoLuongSinhVien DESC LIMIT 1";
    // Statement stmt = conn.createStatement();
    // ResultSet rs = stmt.executeQuery(query);

    // // Display the result in a new window
    // if (rs.next()) {
    // String lop = rs.getString("Lop");
    // int soLuongSinhVien = rs.getInt("SoLuongSinhVien");
    // JOptionPane.showMessageDialog(null,
    // "Lop co nhieu sinh vien nhat: " + lop + " (" + soLuongSinhVien + " students)",
    // "Lop co nhieu sinh vien nhat", JOptionPane.INFORMATION_MESSAGE);
    // }

    // } catch (SQLException | ClassNotFoundException e) {
    // e.printStackTrace();
    // }
    // }

    // Bo sung Cau 6
    private void findClassWithMostStudents() {
        String url = "jdbc:mysql://localhost:3306/QLSV";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);
            String query = "{CALL FindClassWithMostStudents()}";
            CallableStatement cstmt = conn.prepareCall(query);
            ResultSet rs = cstmt.executeQuery();

            if (rs.next()) {
                String lop = rs.getString("Lop");
                int soLuongSinhVien = rs.getInt("SoLuongSinhVien");
                JOptionPane.showMessageDialog(null,
                        "Lop co nhieu sinh vien nhat: " + lop + " (" + soLuongSinhVien + " students)",
                        "Lop co nhieu sinh vien nhat", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // private void findClassWithHighestAvgScore() {
    // String url = "jdbc:mysql://localhost:3306/QLSV";
    // String user = "root";
    // String password = "";

    // try {
    // Class.forName("com.mysql.cj.jdbc.Driver");

    // Connection conn = DriverManager.getConnection(url, user, password);
    // String query = "SELECT Lop, AVG(DiemTB) AS DiemTBTrungBinh FROM SinhVien
    // GROUP BY Lop ORDER BY DiemTBTrungBinh DESC LIMIT 1";
    // Statement stmt = conn.createStatement();
    // ResultSet rs = stmt.executeQuery(query);

    // // Display the result in a new window
    // if (rs.next()) {
    // String lop = rs.getString("Lop");
    // float diemTBTrungBinh = rs.getFloat("DiemTBTrungBinh");
    // JOptionPane.showMessageDialog(null,
    // "Class with highest average score: " + lop + " (Avg Score: " +
    // diemTBTrungBinh + ")",
    // "Lop co diem trung binh cao nhat", JOptionPane.INFORMATION_MESSAGE);
    // }

    // } catch (SQLException | ClassNotFoundException e) {
    // e.printStackTrace();
    // }
    // }

    // Bo sung Cau 6
    private void findClassWithHighestAvgScore() {
        String url = "jdbc:mysql://localhost:3306/QLSV";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);
            String query = "{CALL FindClassWithHighestAvgScore()}";
            CallableStatement cstmt = conn.prepareCall(query);
            ResultSet rs = cstmt.executeQuery();

            if (rs.next()) {
                String lop = rs.getString("Lop");
                float diemTBTrungBinh = rs.getFloat("DiemTBTrungBinh");
                JOptionPane.showMessageDialog(null,
                        "Class with highest average score: " + lop + " (Avg Score: " + diemTBTrungBinh + ")",
                        "Lop co diem trung binh cao nhat", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // private void findClassWithMostFailedStudents() {
    // String url = "jdbc:mysql://localhost:3306/QLSV";
    // String user = "root";
    // String password = "";

    // try {
    // Class.forName("com.mysql.cj.jdbc.Driver");

    // Connection conn = DriverManager.getConnection(url, user, password);
    // String query = "SELECT Lop, COUNT(*) AS SoLuongSinhVien FROM SinhVien WHERE
    // DiemTB < 5 GROUP BY Lop ORDER BY SoLuongSinhVien DESC LIMIT 1";
    // Statement stmt = conn.createStatement();
    // ResultSet rs = stmt.executeQuery(query);

    // // Display the result in a new window
    // if (rs.next()) {
    // String lop = rs.getString("Lop");
    // int soLuongSinhVien = rs.getInt("SoLuongSinhVien");
    // JOptionPane.showMessageDialog(null,
    // "Lop co nhieu sinh vien diem thap nhat: " + lop + " (" + soLuongSinhVien + " failed
    // students)",
    // "Lop co nhieu sinh vien diem thap nhat", JOptionPane.INFORMATION_MESSAGE);
    // }

    // } catch (SQLException | ClassNotFoundException e) {
    // e.printStackTrace();
    // }
    // }

    // Bo sung Cau 6
    private void findClassWithMostFailedStudents() {
        String url = "jdbc:mysql://localhost:3306/QLSV";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);
            String query = "{CALL FindClassWithMostFailedStudents()}";
            CallableStatement cstmt = conn.prepareCall(query);
            ResultSet rs = cstmt.executeQuery();

            if (rs.next()) {
                String lop = rs.getString("Lop");
                int soLuongSinhVien = rs.getInt("SoLuongSinhVien");
                JOptionPane.showMessageDialog(null,
                        "Lop co nhieu sinh vien diem thap nhat: " + lop + " (" + soLuongSinhVien + " Sinh vien)",
                        "Lop co nhieu sinh vien diem thap nhat", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new QLSV_21520714_TrinhTanDat().setVisible(true);
            }
        });
    }
}
