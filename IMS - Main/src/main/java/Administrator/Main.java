/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Administrator;

import com.github.javafaker.Faker;
import java.awt.Color;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.Image;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Stream;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lycoris Cafe
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        formDetails();
        grabData();
    }

    String adminTelegramId;
    String logPath = "C:\\ProgramData\\LycorisCafe\\IMS\\Logs\\";
    String logTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    String year = new SimpleDateFormat("yyyy").format(new Date());
    String month = new SimpleDateFormat("MM").format(new Date());
    String searchFrom;
    String searchWhere;
    String searchLike;
    String cr1x;
    String cr2x;
    String subjectId;
    String teacherId;
    String tableSelection;
    int dayCho;
    DefaultTableModel tableModel;
    Helper.AutomatedMessages tAutomated = new Helper.AutomatedMessages();

    private void formDetails() {
        Helper.MainDetails details = new Helper.MainDetails();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(details.iconPath())));
        setExtendedState(this.MAXIMIZED_BOTH);
    }

    private void grabData() {
        try ( Stream<String> lines = Files.lines(Paths.get("C:\\ProgramData\\LycorisCafe\\IMS\\telegram.lc"))) {
            adminTelegramId = lines.skip(2).findFirst().get();
        } catch (Exception e) {
            System.out.println("#001" + e);
        }
        // grabing data from students table
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(id) "
                    + "FROM students");
            while (rs.next()) {
                int count = rs.getInt(1);
                jTextField1.setText("" + count);
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("#002" + ex);
        }

        // grabing data from teachers table
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(id) "
                    + "FROM teachers");
            while (rs.next()) {
                int count = rs.getInt(1);
                jTextField2.setText("" + count);
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("#003" + ex);
        }

        // grabing data from classes table
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(id) "
                    + "FROM classes");
            while (rs.next()) {
                int count = rs.getInt(1);
                jTextField3.setText("" + count);
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("#004" + ex);
        }
        // grabing and calculating the monthly income from the payments table
        String classId;
        int payment;
        int count;
        int total = 0;
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM classes");
            while (rs.next()) {
                classId = rs.getString("id");
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT * "
                        + "FROM classes "
                        + "WHERE id='" + classId + "'");
                while (rs2.next()) {
                    payment = rs2.getInt("payment");
                    Statement stmt3 = con.createStatement();
                    ResultSet rs3 = stmt3.executeQuery("SELECT * "
                            + "FROM regclass "
                            + "WHERE classId='" + classId + "'");
                    while (rs3.next()) {
                        Statement stmt4 = con.createStatement();
                        ResultSet rs4 = stmt4.executeQuery("SELECT COUNT(id) "
                                + "FROM payments "
                                + "WHERE studentId='" + rs3.getString("studentId") + "' "
                                + "AND classId='" + classId + "' "
                                + "AND year='" + year + "' "
                                + "AND month='" + month + "'");
                        while (rs4.next()) {
                            count = rs4.getInt(1);
                            total = payment * count;
                            jTextField4.setText("" + total);
                        }
                    }
                }
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("#005" + ex);
        }
    }

    private void loadTeachers() {
        jTabbedPane2.setSelectedIndex(0);
        jTextField10.setText("");
        jTextField11.setText("");
        jTextField12.setText("");
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM teachers");
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
            while (rs2.next()) {
                Object[] row = {rs2.getString("id"),
                    rs2.getString("name"), rs2.getString("nic")};
                model.addRow(row);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("#006" + e);
        }
        jButton7.setEnabled(false);
        jButton6.setEnabled(false);
        Component[] com1 = jPanel7.getComponents();
        for (int a = 0; a < com1.length; a++) {
            com1[a].setEnabled(false);
        }
        Component[] com2 = jPanel24.getComponents();
        for (int a = 0; a < com2.length; a++) {
            com2[a].setEnabled(false);
        }
    }

    private void loadStudents() {
        jTextField9.setText("");
        jTextField13.setText("");
        jComboBox4.setSelectedIndex(0);
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM students");
            DefaultTableModel model2 = (DefaultTableModel) jTable3.getModel();
            model2.setRowCount(0);
            while (rs2.next()) {
                Object[] row2 = {rs2.getString("id"),
                    rs2.getString("firstName") + " " + rs2.getString("lastName"),
                    rs2.getString("grade")};
                model2.addRow(row2);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("#007" + e);
        }
        Component[] com0 = jPanel11.getComponents();
        for (int a = 0; a < com0.length; a++) {
            com0[a].setEnabled(false);
        }
        Component[] com1 = jPanel18.getComponents();
        for (int a = 0; a < com1.length; a++) {
            com1[a].setEnabled(false);
        }
        Component[] com2 = jPanel20.getComponents();
        for (int a = 0; a < com2.length; a++) {
            com2[a].setEnabled(false);
        }
        Component[] com3 = jPanel23.getComponents();
        for (int a = 0; a < com3.length; a++) {
            com3[a].setEnabled(false);
        }
    }

    private void loadGroups() {
        DefaultTableModel model = (DefaultTableModel) jTable6.getModel();
        model.setRowCount(0);
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM classes");
            while (rs.next()) {
                String classId = rs.getString("id");
                int day = rs.getInt("day");
                String subjectId = rs.getString("subjectId");
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT * "
                        + "FROM teachers "
                        + "WHERE id='" + rs.getString("teacherId") + "'");
                while (rs2.next()) {
                    String teacher = rs2.getString("name");
                    Statement stmt3 = con.createStatement();
                    ResultSet rs3 = stmt3.executeQuery("SELECT * "
                            + "FROM subjects "
                            + "WHERE id='" + subjectId + "'");
                    while (rs3.next()) {
                        String convertedDay = null;
                        switch (day) {
                            case 1:
                                convertedDay = "Monday";
                                break;
                            case 2:
                                convertedDay = "Tuesday";
                                break;
                            case 3:
                                convertedDay = "Wednesday";
                                break;
                            case 4:
                                convertedDay = "Thursday";
                                break;
                            case 5:
                                convertedDay = "Friday";
                                break;
                            case 6:
                                convertedDay = "Saturday";
                                break;
                            case 7:
                                convertedDay = "Sunday";
                                break;
                        }
                        Object[] row = {classId, rs3.getString("grade"),
                            rs3.getString("subject"), teacher, convertedDay};
                        model.addRow(row);
                    }
                }

            }
            con.close();
        } catch (SQLException e) {
            System.out.println("#008" + e);
        }
        jComboBox8.removeAllItems();
        jComboBox8.addItem("Please Select...");
        jComboBox8.setSelectedIndex(0);
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM subjects");
            while (rs.next()) {
                jComboBox8.addItem(rs.getString("grade")
                        + " - " + rs.getString("subject"));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("#009" + e);
        }
        jComboBox9.removeAllItems();
        jComboBox9.addItem("Please Select...");
        jComboBox9.setSelectedIndex(0);
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM teachers");
            while (rs.next()) {
                jComboBox9.addItem(rs.getString("id")
                        + " - " + rs.getString("name"));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("#010" + e);
        }
        Component[] com1 = jPanel26.getComponents();
        for (int a = 0; a < com1.length; a++) {
            com1[a].setEnabled(false);
        }
        Component[] com2 = jPanel27.getComponents();
        for (int a = 0; a < com2.length; a++) {
            com2[a].setEnabled(false);
        }
    }

    private void loadExams() {
        jTextField19.setText("");
        jComboBox3.setSelectedIndex(0);
        jTextField20.setText("");
        examsReset();
        DefaultTableModel model5 = (DefaultTableModel) jTable8.getModel();
        model5.setRowCount(0);
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM exams");
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String date = rs.getString("date");
                String time = rs.getString("time");
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT * "
                        + "FROM classes "
                        + "WHERE id='" + rs.getString("classId") + "'");
                while (rs2.next()) {
                    Statement stmt3 = con.createStatement();
                    ResultSet rs3 = stmt3.executeQuery("SELECT * "
                            + "FROM subjects "
                            + "WHERE id='" + rs2.getString("subjectId") + "'");
                    while (rs3.next()) {
                        Object[] row5 = {id, name, rs3.getString("grade")
                            + " - " + rs3.getString("subject"), date, time};
                        model5.addRow(row5);
                    }
                }
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("#011" + e);
        }
        Component[] com1 = jPanel38.getComponents();
        for (int a = 0; a < com1.length; a++) {
            com1[a].setEnabled(false);
        }
        Component[] com2 = jPanel39.getComponents();
        for (int a = 0; a < com2.length; a++) {
            com2[a].setEnabled(false);
        }
    }

    private void loadAccounts() {
        jComboBox13.setSelectedIndex(0);
        jTextField26.setText("");
        DefaultTableModel model2 = (DefaultTableModel) jTable7.getModel();
        model2.setRowCount(0);
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM login");
            while (rs.next()) {
                Object[] row2 = {rs.getString("id"),
                    rs.getString("type"), rs.getString("user"),
                    rs.getString("lastLogin")};
                model2.addRow(row2);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("#012" + e);
        }
        jLabel29.setText("");
        jRadioButton1.setSelected(false);
        jRadioButton2.setEnabled(false);
        jRadioButton3.setEnabled(false);
        Component[] com1 = jPanel33.getComponents();
        for (int a = 0; a < com1.length; a++) {
            com1[a].setEnabled(false);
        }
        Component[] com2 = jPanel34.getComponents();
        for (int a = 0; a < com2.length; a++) {
            com2[a].setEnabled(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        telegramId = new javax.swing.JLabel();
        examId = new javax.swing.JLabel();
        tGroupId = new javax.swing.JLabel();
        type = new javax.swing.JLabel();
        success = new javax.swing.JLabel();
        returnMethod = new javax.swing.JLabel();
        fakeNumber = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel52 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jPanel29 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jComboBox11 = new javax.swing.JComboBox<>();
        jScrollPane10 = new javax.swing.JScrollPane();
        broadcastMessage = new javax.swing.JTextArea();
        jButton27 = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        jButton28 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jPanel12 = new javax.swing.JPanel();
        jButton13 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jLabel54 = new javax.swing.JLabel();
        jTextField25 = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        jComboBox14 = new javax.swing.JComboBox<>();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton21 = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        jButton22 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jTextField9 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jComboBox4 = new javax.swing.JComboBox<>();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel26 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        tupdatelabel = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jLabel53 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jComboBox6 = new javax.swing.JComboBox<>();
        jButton17 = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jTextField23 = new javax.swing.JTextField();
        jTextField24 = new javax.swing.JTextField();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton19 = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        jButton20 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jPanel25 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jComboBox8 = new javax.swing.JComboBox<>();
        jComboBox9 = new javax.swing.JComboBox<>();
        jComboBox10 = new javax.swing.JComboBox<>();
        jButton23 = new javax.swing.JButton();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel26 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jButton24 = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        jButton25 = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        jButton26 = new javax.swing.JButton();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        cr1 = new javax.swing.JComboBox<>();
        cr2 = new javax.swing.JComboBox<>();
        cr3 = new javax.swing.JComboBox<>();
        cr4 = new javax.swing.JComboBox<>();
        jTextField22 = new javax.swing.JTextField();
        jTextField27 = new javax.swing.JTextField();
        jButton12 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jLabel62 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jTextField32 = new javax.swing.JTextField();
        jComboBox15 = new javax.swing.JComboBox<>();
        jPanel38 = new javax.swing.JPanel();
        jButton18 = new javax.swing.JButton();
        jPanel39 = new javax.swing.JPanel();
        jButton33 = new javax.swing.JButton();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        jPanel36 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        jTextField20 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jPanel28 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jPanel30 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jTextField26 = new javax.swing.JTextField();
        jButton29 = new javax.swing.JButton();
        jComboBox13 = new javax.swing.JComboBox<>();
        jPanel31 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jTextField28 = new javax.swing.JTextField();
        jButton30 = new javax.swing.JButton();
        jComboBox12 = new javax.swing.JComboBox<>();
        jTextField29 = new javax.swing.JPasswordField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jPanel33 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jButton31 = new javax.swing.JButton();
        jTextField30 = new javax.swing.JPasswordField();
        jTextField31 = new javax.swing.JPasswordField();
        jRadioButton3 = new javax.swing.JRadioButton();
        jPanel34 = new javax.swing.JPanel();
        jButton32 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();

        telegramId.setText("jLabel53");

        examId.setText("jLabel63");

        tGroupId.setText("jLabel64");

        type.setText("jLabel65");

        success.setText("jLabel65");

        returnMethod.setText("jLabel65");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Administrator");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton1.setText("Logout");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/Loding(100x25).gif"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(148, 100));
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Total Students :");

        jLabel2.setText("Total Teachers :");

        jLabel3.setText("Total Classes :");

        jTextField1.setEditable(false);

        jTextField2.setEditable(false);

        jTextField3.setEditable(false);

        jLabel4.setText("Target Income :");

        jTextField4.setEditable(false);

        jPanel29.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Broadcast Message :"));

        jLabel41.setText("To :");

        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Students", "All Teachers", "All Groups" }));

        broadcastMessage.setColumns(20);
        broadcastMessage.setRows(5);
        jScrollPane10.setViewportView(broadcastMessage);

        jButton27.setText("Send");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel42.setText("[ Messages will send via Telegram Bot ]");

        jButton28.setText("Log");
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox11, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 679, Short.MAX_VALUE)
                        .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2)
                            .addComponent(jTextField3)
                            .addComponent(jTextField4))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Summary", jPanel2);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "NIC"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTable1MouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jTabbedPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane2MouseClicked(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Editing Panel :"));

        jLabel5.setText("Name :");

        jLabel6.setText("NIC :");

        jLabel7.setText("Contact :");

        jLabel8.setText("Address :");

        jLabel10.setText("Status :");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Active", "Inactive" }));

        jLabel13.setText("Telegram ID :");

        jCheckBox1.setText("Update Now");

        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel66.setText("-");

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel67.setText("-");
        jLabel67.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jLabel67PropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField5))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField6))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField7))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField8))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, 0, 322, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jCheckBox1)
                    .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel67))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jButton5.setText("Reset");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Delete");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Update");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Add");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Personal", jPanel6);

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Grade", "Subject"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTable2MouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Editing Panel :"));

        jLabel11.setText("Class :");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select..." }));

        jPanel12.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jButton13.setText("Add");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton15.setText("Delete");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setText("Reset");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(119, 119, 119)
                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel54.setText("Payment :");

        jLabel55.setText("Day :");

        jComboBox14.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select...", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" }));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel55, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel54, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField25)
                            .addComponent(jComboBox14, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel70.setText("-");

        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel71.setText("-");

        jLabel72.setText("-");
        jLabel72.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jLabel72PropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel70)
                    .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel72))
                .addContainerGap(94, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Classes", jPanel7);

        jPanel24.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane7.setViewportView(jTextArea2);

        jButton21.setText("Send");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel36.setText("[ Messages will send via Telegram Bot ]");

        jButton22.setText("Log");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                        .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Message", jPanel24);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Search :"));

        jLabel14.setText("by ID :");

        jLabel15.setText("by Name :");

        jLabel16.setText("by NIC :");

        jTextField10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField10FocusGained(evt);
            }
        });
        jTextField10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField10ActionPerformed(evt);
            }
        });

        jTextField11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField11FocusGained(evt);
            }
        });
        jTextField11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField11ActionPerformed(evt);
            }
        });

        jTextField12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField12FocusGained(evt);
            }
        });
        jTextField12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField12ActionPerformed(evt);
            }
        });

        jButton2.setText("Reset");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField10))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField12))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField11)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Teachers", jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Grade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Search :"));

        jLabel9.setText("by ID :");

        jLabel17.setText("by Name :");

        jLabel18.setText("by Grade :");

        jButton3.setText("Reset");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField9ActionPerformed(evt);
            }
        });

        jTextField13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField13ActionPerformed(evt);
            }
        });

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select...", "5", "6", "7", "8", "9", "10", "11" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField9))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField13)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane3MouseClicked(evt);
            }
        });

        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Editing Panel :"));

        jLabel19.setText("First Name :");

        jLabel20.setText("Last Name :");

        jLabel21.setText("Guardian's Name :");

        jLabel22.setText("Guardian's Contact :");

        jLabel23.setText("Address :");

        jLabel24.setText("Grade :");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select...", "5", "6", "7", "8", "9", "10", "11" }));

        jLabel25.setText("Telegram ID :");

        jCheckBox2.setText("Update Now");

        jLabel26.setText("Photo Graph :");

        jPanel17.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("-");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel63.setText("Status :");

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Active", "Inactive" }));

        tupdatelabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        tupdatelabel.setText("-");
        tupdatelabel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tupdatelabelPropertyChange(evt);
            }
        });

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel65.setText("-");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField14))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField15))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField16))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField17))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField18))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel63, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox5, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox7, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jCheckBox2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tupdatelabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel15Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jCheckBox2)
                    .addComponent(tupdatelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel63)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel16.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jButton9.setText("Update");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("Delete");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("Reset");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap(120, Short.MAX_VALUE)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel53.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel53.setText("[ Login as a Moderator to add new students ]");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel53)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Personal", jPanel11);

        jPanel18.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Grade", "Subject", "Teacher", "Day"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTable4);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel12.setText("[ Login as a Moderator to edit classes ]");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addGap(129, 129, 129))
        );

        jTabbedPane3.addTab("Classes", jPanel18);

        jPanel20.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Class", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable5MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTable5);

        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Search :"));

        jLabel30.setText("by Class :");

        jLabel31.setText("by Date :");

        jTextField21.setToolTipText("(YYYY-MM-DD)");
        jTextField21.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField21FocusGained(evt);
            }
        });
        jTextField21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField21ActionPerformed(evt);
            }
        });

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select..." }));
        jComboBox6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboBox6FocusGained(evt);
            }
        });
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });

        jButton17.setText("Reset");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox6, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField21)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Results :"));

        jLabel33.setText("Marks :");

        jLabel34.setText("Rank :");

        jTextField23.setEditable(false);

        jTextField24.setEditable(false);
        jTextField24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField24ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField24))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField23)))
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(132, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Results", jPanel20);

        jPanel23.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane6.setViewportView(jTextArea1);

        jButton19.setText("Send");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel35.setText("[ Messages will send via Telegram Bot ]");

        jButton20.setText("Log");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                        .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Message", jPanel23);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTabbedPane3))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Students", jPanel4);

        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Grade", "Subject", "Teacher", "Day"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable6MouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(jTable6);

        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Search :"));

        jLabel37.setText("by Class :");

        jLabel38.setText("by Teacher :");

        jLabel39.setText("by Day :");

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select..." }));
        jComboBox8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox8ActionPerformed(evt);
            }
        });

        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select..." }));
        jComboBox9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox9ActionPerformed(evt);
            }
        });

        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select...", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" }));
        jComboBox10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox10ActionPerformed(evt);
            }
        });

        jButton23.setText("Reset");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox8, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox9, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox10, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37)
                            .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel39)
                            .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane4MouseClicked(evt);
            }
        });

        jPanel26.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane9.setViewportView(jTextArea3);

        jButton24.setText("Send");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel40.setText("[ Messages will send via Telegram Bot ]");

        jButton25.setText("Log");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                        .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Message", jPanel26);

        jPanel27.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton26.setText("Update Group via Telegram");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });

        jLabel68.setText("-");
        jLabel68.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jLabel68PropertyChange(evt);
            }
        });

        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel69.setText("-");

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton26, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addComponent(jLabel68, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel68))
                .addContainerGap(483, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Update", jPanel27);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jScrollPane8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTabbedPane4))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Groups", jPanel14);

        jPanel19.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel35.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel37.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Add New :"));

        jLabel56.setText("Grade :");

        jLabel57.setText("Subject :");

        jLabel58.setText("Teacher :");

        jLabel59.setText("Day :");

        jLabel60.setText("Exam Name :");

        jLabel61.setText("Exam Date :");

        cr1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select...", "5", "6", "7", "8", "9", "10", "11" }));
        cr1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cr1ActionPerformed(evt);
            }
        });

        cr2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select..." }));
        cr2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cr2ActionPerformed(evt);
            }
        });

        cr3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select..." }));
        cr3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cr3ActionPerformed(evt);
            }
        });

        cr4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select..." }));
        cr4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cr4ActionPerformed(evt);
            }
        });

        jTextField27.setToolTipText("(YYYY-MM-DD)");

        jButton12.setText("Add");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton14.setText("Reset");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jLabel62.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel62.setText("[ Students will inforrm via Telegram Bot ]");

        jLabel64.setText("Exam Time :");

        jComboBox15.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel62, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cr1, 0, 322, Short.MAX_VALUE))
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cr2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cr3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cr4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                        .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel64, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel61, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField27)
                            .addGroup(jPanel37Layout.createSequentialGroup()
                                .addComponent(jTextField32)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(cr1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(cr2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(cr3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59)
                    .addComponent(cr4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61)
                    .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel62)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel38.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Delete :"));

        jButton18.setText("Delete Selected");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel39.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton33.setText("Add/Update Results");
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTable8.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Class", "Date", "Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable8MouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(jTable8);

        jPanel36.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Search :"));

        jLabel32.setText("by Name :");

        jLabel50.setText("by Class :");

        jLabel51.setText("by Date :");

        jTextField19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField19ActionPerformed(evt);
            }
        });

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select..." }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jTextField20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField20ActionPerformed(evt);
            }
        });

        jButton4.setText("Reset");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField19))
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField20))
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addContainerGap())
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel50)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel51)
                            .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                    .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Exams", jPanel19);

        jPanel28.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTable7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Type", "Username", "Last Login"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable7MouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(jTable7);

        jPanel30.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Search :"));

        jLabel43.setText("by Type :");

        jLabel44.setText("by Username :");

        jTextField26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField26ActionPerformed(evt);
            }
        });

        jButton29.setText("Reset");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jComboBox13.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Moderator", "Administrator" }));
        jComboBox13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox13, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField26, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton29)
                .addContainerGap())
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton29, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel43)
                            .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel44)
                            .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel31.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jPanel32.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel45.setText("Type :");

        jLabel46.setText("Username :");

        jLabel47.setText("Password :");

        jButton30.setText("Add");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select...", "Moderator", "Administrator" }));
        jComboBox12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox12, 0, 332, Short.MAX_VALUE))
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField29))
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Add New User");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Reset Password");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jPanel33.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel48.setText("New Password :");

        jLabel49.setText("Confirm Password :");

        jButton31.setText("Apply");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField31))
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField30)))
                .addContainerGap())
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Delete User");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        jPanel34.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton32.setText("Delete");
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel28.setText("Account ID :");

        jLabel29.setText("---");

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                    .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Accounts", jPanel28);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        MainPkg.Welcome logout = new MainPkg.Welcome();
        logout.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField24ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField24ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        // getting the admin's message and send the message
        if (broadcastMessage.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter message to broadcast!");
        } else {
            TelegramReports reports = new TelegramReports();
            reports.setVisible(true);
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
            String message = broadcastMessage.getText();
            try {
                Writer output = new BufferedWriter(
                        new FileWriter("C:\\ProgramData\\LycorisCafe\\IMS\\Logs\\broadcastMessage.log", true));
                int waitingCount = 0;
                int successCount = 0;
                int unsuccessCount = 0;
                String to = jComboBox11.getSelectedItem().toString();
                Helper.TelegramBot telegram = new Helper.TelegramBot();
                String table = null;
                switch (to) {
                    case "All Students":
                        table = "students";
                        break;
                    case "All Teachers":
                        table = "teachers";
                        break;
                    case "All Groups":
                        table = "classes";
                        break;
                }
                output.append("\nStarting Broadcast...\n");
                output.append(time + "\n");
                TelegramReports.jTextArea1.append("Starting Broadcast...\n");
                try {
                    Connection con = Helper.DB.connect();
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT COUNT(id) "
                            + "FROM " + table + "");
                    while (rs.next()) {
                        waitingCount = rs.getInt(1);
                        TelegramReports.jLabel6.setText("" + waitingCount);
                        Statement stmt2 = con.createStatement();
                        ResultSet rs2 = stmt2.executeQuery("SELECT * "
                                + "FROM " + table + "");
                        while (rs2.next()) {
                            String telegramId = rs2.getString("telegramId");
                            String sName = null;
                            if (table.equals("students")) {
                                sName = rs2.getString("id") + " - "
                                        + rs2.getString("firstName") + " "
                                        + rs2.getString("lastName");
                            }
                            if (table.equals("teachers")) {
                                sName = rs2.getString("id") + " - "
                                        + rs2.getString("name");
                            }
                            if (table.equals("classes")) {
                                sName = rs2.getString("id");
                            }
                            SendMessage sm = new SendMessage();
                            sm.setText(message);
                            sm.setChatId(telegramId);
                            try {
                                telegram.execute(sm);
                                TelegramReports.jTextArea1.append("Message sent success to : " + sName + "\n");
                                output.append("Message sent success to : " + sName + "\n");
                                successCount = successCount + 1;
                                TelegramReports.jLabel3.setText("" + successCount);
                            } catch (TelegramApiException e) {
                                System.out.println("#013" + e);
                                TelegramReports.jTextArea1.append("Message sent unsuccess to : " + sName + "\n");
                                output.append("Message sent unsuccess to : " + sName + "\n");
                                unsuccessCount = unsuccessCount + 1;
                                TelegramReports.jLabel4.setText("" + unsuccessCount);
                            }
                        }

                    }
                    con.close();
                } catch (SQLException e) {
                    System.out.println("#014" + e);
                }
                TelegramReports.jTextArea1.append("Ending Broadcast...\n");
                output.append("Ending Broadcast...\n\n");
                broadcastMessage.setText("");
                output.close();
            } catch (IOException e) {
                System.out.println("#015" + e);
            }
        }
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        // to open the log file
        try {
            ProcessBuilder processBuilder
                    = new ProcessBuilder("cmd.exe", "/c",
                            "C:\\ProgramData\\LycorisCafe\\IMS\\Logs\\broadcastMessage.log");
            processBuilder.redirectErrorStream(true);
            processBuilder.start();
        } catch (IOException e) {
            System.out.println("#016" + e);
        }
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // grabing data from selected row
        int r = jTable1.getSelectedRow();
        String id = jTable1.getValueAt(r, 0).toString();
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT  * "
                    + "FROM teachers "
                    + "WHERE id='" + id + "'");
            while (rs.next()) {
                jTextField5.setText(rs.getString("name"));
                jTextField6.setText(rs.getString("nic"));
                jTextField7.setText(rs.getString("contact"));
                jTextField8.setText(rs.getString("address"));
                if (rs.getString("status").equals("0")) {
                    jComboBox1.setSelectedIndex(0);
                } else {
                    jComboBox1.setSelectedIndex(1);
                }
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("#017" + e);
        }
        jButton7.setEnabled(true);
        jButton6.setEnabled(true);
        Component[] com1 = jPanel7.getComponents();
        for (int a = 0; a < com1.length; a++) {
            com1[a].setEnabled(true);
        }
        Component[] com2 = jPanel24.getComponents();
        for (int a = 0; a < com2.length; a++) {
            com2[a].setEnabled(true);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // adding data to teachers table
        jCheckBox1.setSelected(true);
        jLabel66.setText("");
        jLabel66.setIcon(new ImageIcon(getClass().getResource("/Media/Loding(100x25).gif")));
        Faker faker = new Faker();
        jLabel67.setText(faker.number().digits(5));
        fakeNumber.setText(jLabel67.getText());
        type.setText("private");
        returnMethod.setText("teacherAdd");
    }//GEN-LAST:event_jButton8ActionPerformed

    public void teacherAdd() {
        String name = jTextField5.getText();
        String nic = jTextField6.getText();
        String contact = jTextField7.getText();
        String address = jTextField8.getText();
        int status = jComboBox1.getSelectedIndex();
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = (Statement) con.createStatement();
            stmt.executeUpdate("INSERT INTO teachers(name, nic, address, "
                    + "telegramId, contact, status) "
                    + "VALUES "
                    + "('" + name + "', '" + nic + "', '" + address + "',"
                    + " '" + telegramId.getText() + "', '" + contact + "', '" + status + "')");
            JOptionPane.showMessageDialog(this, "Success!");
            con.close();
            loadTeachers();
        } catch (SQLException e) {
            System.out.println("#018" + e);
        }
    }

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // updating data in teachers table
        int r = jTable1.getSelectedRow();
        tableSelection = jTable1.getValueAt(r, 0).toString();
        if (jCheckBox1.isSelected()) {
            jLabel66.setText("");
            jLabel66.setIcon(new ImageIcon(getClass().getResource("/Media/Loding(100x25).gif")));
            Faker faker = new Faker();
            jLabel67.setText(faker.number().digits(5));
            fakeNumber.setText(jLabel67.getText());
            type.setText("private");
            returnMethod.setText("teacherUpdate");
        } else {
            String name = jTextField5.getName();
            String nic = jTextField6.getName();
            String contact = jTextField7.getName();
            String address = jTextField8.getName();
            int status = jComboBox1.getSelectedIndex();
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = (Statement) con.createStatement();
                stmt.executeUpdate("UPDATE teachers SET "
                        + "name='" + name + "', "
                        + "nic='" + nic + "', "
                        + "address='" + address + "', "
                        + "contact='" + contact + "', "
                        + "status='" + status + "' "
                        + "WHERE id='" + tableSelection + "'");
                JOptionPane.showMessageDialog(this, "Success!");
                con.close();
                loadTeachers();
            } catch (SQLException e) {
                System.out.println("#019" + e);
            }
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    public void teacherUpdate() {
        String name = jTextField5.getText();
        String nic = jTextField6.getText();
        String contact = jTextField7.getText();
        String address = jTextField8.getText();
        int status = jComboBox1.getSelectedIndex();
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = (Statement) con.createStatement();
            stmt.executeUpdate("UPDATE teachers SET "
                    + "name='" + name + "', "
                    + "nic='" + nic + "', "
                    + "address='" + address + "', "
                    + "telegramId='" + telegramId.getText() + "', "
                    + "contact='" + contact + "', "
                    + "status='" + status + "' "
                    + "WHERE id='" + tableSelection + "'");
            JOptionPane.showMessageDialog(this, "Success!");
            con.close();
            loadTeachers();
        } catch (SQLException e) {
            System.out.println("#020" + e);
        }
    }

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // delete selected row
        int r = jTable1.getSelectedRow();
        String id = jTable1.getValueAt(r, 0).toString();
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = (Statement) con.createStatement();
            stmt.executeUpdate("DELETE FROM teachers WHERE id='" + id + "'");
            con.close();
            JOptionPane.showMessageDialog(this, "Success!");
        } catch (SQLException e) {
            System.out.println("#021" + e);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // to update data of the selected row
        int r = jTable3.getSelectedRow();
        tableSelection = jTable3.getValueAt(r, 0).toString();
        if (jCheckBox2.isSelected()) {
            jLabel65.setText("");
            jLabel65.setIcon(new ImageIcon(getClass().getResource("/Media/Loding(100x25).gif")));
            Faker faker = new Faker();
            tupdatelabel.setText(faker.number().digits(5));
            fakeNumber.setText(tupdatelabel.getText());
            type.setText("private");
            returnMethod.setText("studentUpdate");
        } else {
            String fname = jTextField14.getText();
            String lname = jTextField15.getText();
            String gname = jTextField16.getText();
            String gPhone = jTextField17.getText();
            String address = jTextField18.getText();
            String grade = jComboBox5.getSelectedItem().toString();

            try {
                Connection con = Helper.DB.connect();
                Statement stmt = (Statement) con.createStatement();
                String sql = "UPDATE students SET "
                        + "firstName='" + fname + "', "
                        + "lastName='" + lname + "', "
                        + "guardianName='" + gname + "', "
                        + "guardianPhone='" + gPhone + "', "
                        + "address='" + address + "', "
                        + "grade='" + grade + "', "
                        + "status='" + jComboBox7.getSelectedIndex() + "' "
                        + "WHERE id='" + tableSelection + "'";
                stmt.executeUpdate(sql);
                JOptionPane.showMessageDialog(this, "Success!");
                loadStudents();
                con.close();
            } catch (SQLException e) {
                System.out.println("#022" + e);
            }
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    public void studentUpdate() {
        String fname = jTextField14.getText();
        String lname = jTextField15.getText();
        String gname = jTextField16.getText();
        String gPhone = jTextField17.getText();
        String address = jTextField18.getText();
        String grade = jComboBox5.getSelectedItem().toString();
        String status = String.valueOf(jComboBox7.getSelectedIndex());
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = (Statement) con.createStatement();
            stmt.executeUpdate("UPDATE students "
                    + "SET firstName='" + fname + "', "
                    + "lastName='" + lname + "', "
                    + "guardianName='" + gname + "', "
                    + "guardianPhone='" + gPhone + "', "
                    + "address='" + address + "', "
                    + "grade='" + grade + "', "
                    + "telegramId='" + telegramId.getText() + "', "
                    + "status='" + status + "' "
                    + "WHERE id='" + tableSelection + "'");
            System.out.println("oook");
            con.close();
            JOptionPane.showMessageDialog(this, "Success!");
            loadStudents();
        } catch (SQLException e) {
            System.out.println("#023" + e);
        }
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // resetting all the textfields
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jTextField8.setText("");
        jCheckBox1.setSelected(false);
        jComboBox1.setSelectedIndex(0);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // send message from teacher panel to telegram
        if (jTextArea2 == null) {
            JOptionPane.showMessageDialog(this, "Please enter message to send!");
        } else {
            int r = jTable1.getSelectedRow();
            String name2 = null;
            String id = jTable1.getValueAt(r, 0).toString();
            Helper.TelegramBot telegram = new Helper.TelegramBot();
            SendMessage sm = new SendMessage();
            try {
                Writer output = new BufferedWriter(
                        new FileWriter(logPath + "teachersMessage.log", true));

                try {
                    Connection con = Helper.DB.connect();
                    Statement stmt = con.createStatement();

                    ResultSet rs = stmt.executeQuery("SELECT * "
                            + "FROM teachers "
                            + "WHERE id='" + id + "'");
                    while (rs.next()) {
                        String teacherId = rs.getString("id");
                        String Teleid = rs.getString("telegramId");
                        name2 = rs.getString("name");
                        sm.setText(jTextArea2.getText());
                        sm.setChatId(Teleid);
                        try {
                            telegram.execute(sm);
                            output.append("\n" + logTime + " - " + teacherId + " -\n" + jTextArea2.getText());
                            JOptionPane.showMessageDialog(this, "Message sent success!");
                            jTextArea2.setText("");
                        } catch (TelegramApiException e) {
                            System.out.println("#024" + e);
                            JOptionPane.showMessageDialog(this, "Error while sending message!");
                        }
                    }
                    con.close();
                } catch (SQLException e) {
                    System.out.println("#025" + e);
                }
                output.close();
            } catch (IOException ex) {
                System.out.println("#026" + ex);
            }
        }
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
        // to filter selected data by ID
        if (!"".equals(jTextField10.getText())) {
            searchFrom = "teachers";
            searchWhere = "id";
            searchLike = jTextField10.getText();
            tableModel = (DefaultTableModel) jTable1.getModel();
            searchQueryTeachers();
        }
    }//GEN-LAST:event_jTextField10ActionPerformed

    private void searchQueryTeachers() {
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM " + searchFrom + " "
                    + "WHERE " + searchWhere + " "
                    + "LIKE '%" + searchLike + "%'");
            tableModel.setRowCount(0);
            while (rs.next()) {
                Object[] row = {rs.getString("id"),
                    rs.getString("name"), rs.getString("nic")};
                tableModel.addRow(row);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("#027" + e);
        }
    }

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField11ActionPerformed
        // to filter selected data by name
        if (!"".equals(jTextField11.getText())) {
            searchFrom = "teachers";
            searchWhere = "name";
            searchLike = jTextField11.getText();
            tableModel = (DefaultTableModel) jTable1.getModel();
            searchQueryTeachers();
        }
    }//GEN-LAST:event_jTextField11ActionPerformed

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField12ActionPerformed
        // to filter selected data by nic
        if (!"".equals(jTextField12.getText())) {
            searchFrom = "teachers";
            searchWhere = "nic";
            searchLike = jTextField12.getText();
            tableModel = (DefaultTableModel) jTable1.getModel();
            searchQueryTeachers();
        }
    }//GEN-LAST:event_jTextField12ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // to reset all texts of text fields in Search panel
        loadTeachers();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // to display the log file
        try {
            ProcessBuilder processBuilder
                    = new ProcessBuilder("cmd.exe", "/c",
                            "C:/ProgramData/LycorisCafe/IMS/Logs/teachersMessage.log");
            processBuilder.redirectErrorStream(true);
            processBuilder.start();
        } catch (IOException e) {
            System.out.println("#028" + e);
        }
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // delete a student from students table
        int r = jTable3.getSelectedRow();
        String id = jTable3.getValueAt(r, 0).toString();
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = (Statement) con.createStatement();
            stmt.executeUpdate("DELETE FROM regclass "
                    + "WHERE studentId='" + id + "'");
            con.close();
        } catch (SQLException e) {
            System.out.println("#029" + e);
        }
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = (Statement) con.createStatement();
            stmt.executeUpdate("DELETE FROM students "
                    + "WHERE id='" + id + "'");
            JOptionPane.showMessageDialog(this, "Success!");
            con.close();
            loadStudents();
        } catch (SQLException e) {
            System.out.println("#030" + e);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // grabing data from selected row
        Component[] com0 = jPanel11.getComponents();
        for (int a = 0; a < com0.length; a++) {
            com0[a].setEnabled(true);
        }
        jLabel27.setIcon(null);
        jLabel27.setText("-");
        int r = jTable3.getSelectedRow();
        String id = jTable3.getValueAt(r, 0).toString();
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM students "
                    + "WHERE id='" + id + "'");
            while (rs.next()) {
                jTextField14.setText(rs.getString("firstName"));
                jTextField15.setText(rs.getString("lastName"));
                jTextField16.setText(rs.getString("guardianName"));
                jTextField17.setText(rs.getString("guardianPhone"));
                jTextField18.setText(rs.getString("address"));
                jComboBox5.setSelectedItem(rs.getString("grade"));
                if (rs.getString("status").equals("0")) {
                    jComboBox7.setSelectedIndex(0);
                } else {
                    jComboBox7.setSelectedIndex(1);
                }
                jLabel27.setText("");
                ImageIcon myImage = new ImageIcon("C:\\ProgramData\\LycorisCafe\\IMS\\StudentImgs\\" + id + ".png");
                Image newImage = myImage.getImage().getScaledInstance(136, 136, Image.SCALE_DEFAULT);
                jLabel27.setIcon(new ImageIcon(newImage));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("#031" + e);
        }
    }//GEN-LAST:event_jTable3MouseClicked

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // reset all the text fields in students panel
        jTextField14.setText("");
        jTextField15.setText("");
        jTextField16.setText("");
        jTextField17.setText("");
        jTextField18.setText("");
        jLabel27.setIcon(null);
        jLabel27.setText("-");
        jComboBox5.setSelectedIndex(0);
        jCheckBox2.setSelected(false);
        jComboBox7.setSelectedIndex(0);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // to send private message to selected student via telegram bot
        if (jTextArea1 == null) {
            JOptionPane.showMessageDialog(this, "Please enter message to send!");
        } else {
            int r = jTable3.getSelectedRow();
            String id = jTable3.getValueAt(r, 0).toString();
            Helper.TelegramBot telegram = new Helper.TelegramBot();
            SendMessage sm = new SendMessage();
            try {
                Writer output = new BufferedWriter(
                        new FileWriter(logPath + "studentsMessage.log", true));
                try {
                    Connection con = Helper.DB.connect();
                    Statement stmt = con.createStatement();

                    ResultSet rs = stmt.executeQuery("SELECT * "
                            + "FROM students "
                            + "WHERE id='" + id + "'");
                    while (rs.next()) {
                        String studentId = rs.getString("id");
                        String Teleid = rs.getString("telegramId");
                        sm.setText(jTextArea1.getText());
                        sm.setChatId(Teleid);
                        try {
                            telegram.execute(sm);
                            output.append("\n" + logTime + " - " + studentId + " -\n" + jTextArea1.getText());
                            JOptionPane.showMessageDialog(this, "Message sent success!");
                            jTextArea1.setText("");
                        } catch (TelegramApiException e) {
                            System.out.println("#032" + e);
                            JOptionPane.showMessageDialog(this, "Error while sending message!");
                        }
                    }
                    con.close();
                } catch (SQLException e) {
                    System.out.println("#033" + e);
                }
                output.close();
            } catch (IOException ex) {
                System.out.println("#034" + ex);
            }
        }
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // to display the log file
        try {
            ProcessBuilder processBuilder
                    = new ProcessBuilder("cmd.exe", "/c",
                            logPath + "studentsMessage.log");
            processBuilder.redirectErrorStream(true);
            processBuilder.start();
        } catch (IOException e) {
            System.out.println("#035" + e);
        }
    }//GEN-LAST:event_jButton20ActionPerformed

    private void searchQueryStudents() {
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM " + searchFrom + " "
                    + "WHERE " + searchWhere + " "
                    + "LIKE '%" + searchLike + "%'");
            tableModel.setRowCount(0);
            while (rs.next()) {
                Object[] row = {rs.getString("id"),
                    rs.getString("firstName") + " " + rs.getString("lastName"), rs.getString("grade")};
                tableModel.addRow(row);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("#036" + e);
        }
    }

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // search for students by id
        if (!"".equals(jTextField9.getText())) {
            searchFrom = "students";
            searchWhere = "id";
            searchLike = jTextField9.getText();
            tableModel = (DefaultTableModel) jTable3.getModel();
            searchQueryStudents();
        }
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void jTextField13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField13ActionPerformed
        // search for students by name
        if (!"".equals(jTextField13.getText())) {
            searchFrom = "students";
            searchWhere = "firstName";
            searchLike = jTextField13.getText();
            tableModel = (DefaultTableModel) jTable3.getModel();
            searchQueryStudents();
        }
    }//GEN-LAST:event_jTextField13ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // search for students by grade
        if (jComboBox4.getSelectedIndex() != 0) {
            searchFrom = "students";
            searchWhere = "grade";
            searchLike = jComboBox4.getSelectedItem().toString();
            tableModel = (DefaultTableModel) jTable3.getModel();
            searchQueryStudents();
        }
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // reset all the text fields in search panel - students
        loadStudents();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseEntered

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        // TODO add your handling code here:
        if (jTextArea1 == null) {
            JOptionPane.showMessageDialog(this, "Please enter message to send!");
        } else {
            int r = jTable6.getSelectedRow();
            String id = jTable6.getValueAt(r, 0).toString();
            Helper.TelegramBot telegram = new Helper.TelegramBot();
            SendMessage sm = new SendMessage();
            try {
                Writer output = new BufferedWriter(
                        new FileWriter(logPath + "groupsMessage.log", true));

                try {
                    Connection con = Helper.DB.connect();
                    Statement stmt = con.createStatement();

                    ResultSet rs = stmt.executeQuery("SELECT id,telegramId "
                            + "FROM classes "
                            + "WHERE id='" + id + "'");
                    while (rs.next()) {
                        String classId = rs.getString("id");
                        String Teleid = rs.getString("telegramId");
                        sm.setText(jTextArea3.getText());
                        sm.setChatId(Teleid);
                        try {
                            telegram.execute(sm);
                            output.append("\n" + logTime + " - " + classId + " -\n" + jTextArea3.getText());
                            JOptionPane.showMessageDialog(this, "Message sent success!");
                        } catch (TelegramApiException e) {
                            System.out.println("#037" + e);
                            JOptionPane.showMessageDialog(this, "Error while sending message!");
                        }
                    }
                    con.close();
                } catch (SQLException e) {
                    System.out.println("#038" + e);
                }
                output.close();
            } catch (IOException ex) {
                System.out.println("#039" + ex);
            }
            jTextArea3.setText("");
        }
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        // TODO add your handling code here:
        try {
            ProcessBuilder processBuilder
                    = new ProcessBuilder("cmd.exe", "/c",
                            logPath + "groupsMessage.log");
            processBuilder.redirectErrorStream(true);
            processBuilder.start();
        } catch (IOException e) {
            System.out.println("#040" + e);
        }
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:
        if (jTabbedPane1.getSelectedIndex() == 1) {
            loadTeachers();
        }
        if (jTabbedPane1.getSelectedIndex() == 2) {
            loadStudents();
        }
        if (jTabbedPane1.getSelectedIndex() == 3) {
            loadGroups();
        }
        if (jTabbedPane1.getSelectedIndex() == 4) {
            loadExams();
        }
        if (jTabbedPane1.getSelectedIndex() == 5) {
            loadAccounts();
        }
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        // TODO add your handling code here:
        int r = jTable6.getSelectedRow();
        tableSelection = jTable6.getValueAt(r, 0).toString();
        jLabel69.setText("");
        jLabel69.setIcon(new ImageIcon(getClass().getResource("/Media/Loding(100x25).gif")));
        Faker faker = new Faker();
        jLabel68.setText(faker.number().digits(5));
        fakeNumber.setText(jLabel68.getText());
        type.setText("supergroup");
        returnMethod.setText("groupTIdUpdate");
    }//GEN-LAST:event_jButton26ActionPerformed

    public void groupTIdUpdate() {
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE classes SET "
                    + "telegramId='" + telegramId.getText() + "' "
                    + "WHERE id='" + tableSelection + "'");
            JOptionPane.showMessageDialog(this, "Success!");
            loadGroups();
        } catch (HeadlessException | SQLException e) {
            System.out.println("#041" + e);
        }
    }

    private void jTabbedPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane2MouseClicked
        // TODO add your handling code here:
        teacherClassesTable();
    }//GEN-LAST:event_jTabbedPane2MouseClicked

    private void teacherClassesTable() {
        if (jTable1.getSelectedRowCount() == 1) {
            if (jTabbedPane2.getSelectedIndex() == 1) {
                Component[] com1 = jPanel7.getComponents();
                for (int a = 0; a < com1.length; a++) {
                    com1[a].setEnabled(true);
                }
                jButton15.setEnabled(false);
                DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                model.setRowCount(0);
                int r = jTable1.getSelectedRow();
                String id = jTable1.getValueAt(r, 0).toString();
                try {
                    Connection con = Helper.DB.connect();
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT  * "
                            + "FROM classes "
                            + "WHERE teacherId='" + id + "'");
                    while (rs.next()) {
                        Statement stmt2 = con.createStatement();
                        ResultSet rs2 = stmt2.executeQuery("SELECT * "
                                + "FROM subjects "
                                + "WHERE id='" + rs.getString("subjectId") + "'");
                        while (rs2.next()) {
                            Object[] row = {rs2.getString("id"),
                                rs2.getString("grade"), rs2.getString("subject")};
                            model.addRow(row);
                        }
                    }
                    con.close();
                } catch (SQLException e) {
                    System.out.println("#042" + e);
                }
                jComboBox2.removeAllItems();
                jComboBox2.addItem("Please Select...");
                try {
                    Connection con = Helper.DB.connect();
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT  * "
                            + "FROM subjects "
                            + "ORDER BY grade,subject");
                    while (rs.next()) {
                        String settingClass = rs.getString("grade")
                                + " - " + rs.getString("subject");
                        jComboBox2.addItem(settingClass);
                    }
                    con.close();
                } catch (SQLException e) {
                    System.out.println("#043" + e);
                }
                jComboBox2.setSelectedIndex(0);
                jButton15.setEnabled(false);
            }
            if (jTabbedPane2.getSelectedIndex() == 2) {
                Component[] com1 = jPanel24.getComponents();
                for (int a = 0; a < com1.length; a++) {
                    if (!com1[a].isEnabled()) {
                        com1[a].setEnabled(true);
                    }
                }
                jTextArea2.setText("");
            }
        }
    }

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        jComboBox2.setSelectedIndex(0);
        jTextField25.setText("");
        jComboBox14.setSelectedIndex(0);
        jTable2.clearSelection();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        jLabel70.setText("Telegram Verification :");
        jLabel71.setText("");
        jLabel71.setIcon(new ImageIcon(getClass().getResource("/Media/Loding(100x25).gif")));
        Faker faker = new Faker();
        jLabel72.setText(faker.number().digits(5));
        fakeNumber.setText(jLabel72.getText());
        type.setText("supergroup");
        returnMethod.setText("groupAdd");
    }//GEN-LAST:event_jButton13ActionPerformed

    public void groupAdd() {
        int r = jTable1.getSelectedRow();
        String id = jTable1.getValueAt(r, 0).toString();
        String gotClass = jComboBox2.getSelectedItem().toString();
        String[] parts = gotClass.split(" - ");
        String grade = parts[0];
        String subject = parts[1];
        String payment = jTextField25.getText();
        int day = jComboBox14.getSelectedIndex();
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM subjects "
                    + "WHERE grade='" + grade + "' AND subject='" + subject + "'");
            while (rs.next()) {
                Statement stmt2 = con.createStatement();
                stmt2.executeUpdate("INSERT INTO classes "
                        + "(subjectId,teacherId,payment,day,time,duration,telegramId) "
                        + "VALUES "
                        + "('" + rs.getString("id") + "','" + id + "',"
                        + "'" + payment + "','" + day + "','0','0','" + telegramId.getText() + "')");
                JOptionPane.showMessageDialog(this, "Success!");
                teacherClassesTable();
            }
        } catch (SQLException e) {
            System.out.println("#044" + e);
        }
    }

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        jButton15.setEnabled(true);
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        int r = jTable1.getSelectedRow();
        String id = jTable1.getValueAt(r, 0).toString();
        int r1 = jTable1.getSelectedRow();
        String id1 = jTable2.getValueAt(r, 0).toString();
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM classes "
                    + "WHERE subjectId='" + id1 + "' AND teacherId='" + id + "'");
            JOptionPane.showMessageDialog(this, "Success!");
        } catch (SQLException e) {
            System.out.println("#046" + e);
        }
        teacherClassesTable();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jTextField10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField10FocusGained
        // TODO add your handling code here:
        jTextField11.setText("");
        jTextField12.setText("");
    }//GEN-LAST:event_jTextField10FocusGained

    private void jTextField11FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField11FocusGained
        // TODO add your handling code here:
        jTextField10.setText("");
        jTextField12.setText("");
    }//GEN-LAST:event_jTextField11FocusGained

    private void jTextField12FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField12FocusGained
        // TODO add your handling code here:
        jTextField10.setText("");
        jTextField11.setText("");
    }//GEN-LAST:event_jTextField12FocusGained

    private void jTabbedPane3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane3MouseClicked
        // TODO add your handling code here:
        if (jTable3.getSelectedRowCount() == 1) {
            if (jTabbedPane3.getSelectedIndex() == 1) {
                Component[] com1 = jPanel18.getComponents();
                for (int a = 0; a < com1.length; a++) {
                    com1[a].setEnabled(true);
                }
                int r = jTable3.getSelectedRow();
                String id = jTable3.getValueAt(r, 0).toString();
                DefaultTableModel model1 = (DefaultTableModel) jTable4.getModel();
                model1.setRowCount(0);
                String convertedDay = null;
                try {
                    Connection con = Helper.DB.connect();
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * "
                            + "FROM regclass "
                            + "WHERE studentId='" + id + "'");
                    while (rs.next()) {
                        Statement stmt2 = con.createStatement();
                        ResultSet rs2 = stmt2.executeQuery("SELECT * "
                                + "FROM classes "
                                + "WHERE id='" + rs.getString("classId") + "'");
                        while (rs2.next()) {
                            int day = rs2.getInt("day");
                            String teacher = rs2.getString("teacherId");
                            String subject = rs2.getString("subjectId");
                            Statement stmt3 = con.createStatement();
                            ResultSet rs3 = stmt3.executeQuery("SELECT * "
                                    + "FROM subjects "
                                    + "WHERE id='" + subject + "'");
                            while (rs3.next()) {
                                String grade = rs3.getString("grade");
                                Statement stmt4 = con.createStatement();
                                ResultSet rs4 = stmt4.executeQuery("SELECT * "
                                        + "FROM teachers "
                                        + "WHERE id='" + teacher + "'");
                                while (rs4.next()) {
                                    switch (day) {
                                        case 1 ->
                                            convertedDay = "Monday";
                                        case 2 ->
                                            convertedDay = "Tuesday";
                                        case 3 ->
                                            convertedDay = "Wednesday";
                                        case 4 ->
                                            convertedDay = "Thursday";
                                        case 5 ->
                                            convertedDay = "Friday";
                                        case 6 ->
                                            convertedDay = "Saturday";
                                        case 7 ->
                                            convertedDay = "Sunday";
                                    }
                                    Object[] row = {grade, subject,
                                        rs4.getString("name"), convertedDay};
                                    model1.addRow(row);
                                }
                            }
                        }
                    }
                    con.close();
                } catch (SQLException ex) {
                    System.out.println("#047" + ex);
                }
            }
            if (jTabbedPane3.getSelectedIndex() == 2) {
                Component[] com2 = jPanel20.getComponents();
                for (int a = 0; a < com2.length; a++) {
                    com2[a].setEnabled(true);
                }

                DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
                int r = jTable3.getSelectedRow();
                String id = jTable3.getValueAt(r, 0).toString();
                DefaultTableModel model1 = (DefaultTableModel) jTable5.getModel();
                model1.setRowCount(0);
                try {
                    Connection con = Helper.DB.connect();
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * "
                            + "FROM results "
                            + "WHERE studentId='" + id + "'");
                    while (rs.next()) {
                        String resultId = rs.getString("id");
                        int marks = rs.getInt("marks");
                        Statement stmt2 = con.createStatement();
                        ResultSet rs2 = stmt2.executeQuery("SELECT * "
                                + "FROM exams "
                                + "WHERE id='" + rs.getString("examId") + "'");
                        while (rs2.next()) {
                            String examName = rs2.getString("name");
                            String examDate = rs2.getString("date");
                            Statement stmt3 = con.createStatement();
                            ResultSet rs3 = stmt3.executeQuery("SELECT * "
                                    + "FROM classes "
                                    + "WHERE id='" + rs2.getString("classId") + "'");
                            while (rs3.next()) {
                                Statement stmt4 = con.createStatement();
                                ResultSet rs4 = stmt4.executeQuery("SELECT * "
                                        + "FROM subjects "
                                        + "WHERE id='" + rs3.getString("subjectId") + "'");
                                while (rs4.next()) {
                                    Object[] row = {resultId, examName,
                                        rs4.getString("grade") + " - " + rs4.getString("subject"),
                                        examDate};
                                    model1.addRow(row);
                                }
                            }
                        }
                    }
                    con.close();
                } catch (SQLException ex) {
                    System.out.println("#048" + ex);
                }
                jComboBox6.removeAllItems();
                jComboBox6.addItem("Please Select...");
                jComboBox6.setSelectedIndex(0);
                try {
                    Connection con = Helper.DB.connect();
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * "
                            + "FROM regclass "
                            + "WHERE studentId='" + id + "'");
                    while (rs.next()) {
                        Statement stmt2 = con.createStatement();
                        ResultSet rs2 = stmt2.executeQuery("SELECT * "
                                + "FROM subjects "
                                + "WHERE id='" + rs.getString("subjectId") + "'");
                        while (rs2.next()) {
                            jComboBox6.addItem(rs2.getString("grade")
                                    + " - " + rs2.getString("subject"));
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("#049" + e);
                }
            }
            if (jTabbedPane3.getSelectedIndex() == 3) {
                Component[] com3 = jPanel23.getComponents();
                for (int a = 0; a < com3.length; a++) {
                    com3[a].setEnabled(true);
                }
            }
        }
    }//GEN-LAST:event_jTabbedPane3MouseClicked

    private void jTable2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable2MouseEntered

    private void jTable5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) jTable5.getModel();
        int r = jTable5.getSelectedRow();
        String id = jTable5.getValueAt(r, 0).toString();
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM results "
                    + "WHERE id='" + id + "'");
            while (rs.next()) {
                int marks = rs.getInt("marks");
                jTextField23.setText("" + marks);
                if (marks >= 75) {
                    jTextField24.setText("A");
                    jTextField24.setForeground(Color.green);
                } else if (marks >= 65) {
                    jTextField24.setText("B");
                    jTextField24.setForeground(Color.green);
                } else if (marks >= 55) {
                    jTextField24.setText("C");
                    jTextField24.setForeground(Color.yellow);
                } else if (marks >= 35) {
                    jTextField24.setText("S");
                    jTextField24.setForeground(Color.yellow);
                } else {
                    jTextField24.setText("W");
                    jTextField24.setForeground(Color.red);
                }
            }
        } catch (SQLException e) {
            System.out.println("#050" + e);
        }
    }//GEN-LAST:event_jTable5MouseClicked

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        jComboBox6.setSelectedIndex(0);
        jTextField21.setText("");
        jTextField23.setText("");
        jTextField24.setText("");
        jTable5.clearSelection();
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
        // TODO add your handling code here:
        if (jComboBox6.getSelectedIndex() == 0 || jComboBox6.getSelectedItem() == null) {
            jTextField21.setText("");
            jTextField23.setText("");
            jTextField24.setText("");
            jTable5.clearSelection();
        } else {
            int r = jTable3.getSelectedRow();
            String id = jTable3.getValueAt(r, 0).toString();
            DefaultTableModel model2 = (DefaultTableModel) jTable5.getModel();
            model2.setRowCount(0);
            String[] parts = jComboBox6.getSelectedItem().toString().split(" - ");
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * "
                        + "FROM subjects "
                        + "WHERE grade='" + parts[0] + "'"
                        + "AND subject='" + parts[1] + "'");
                while (rs.next()) {
                    Statement stmt2 = con.createStatement();
                    ResultSet rs2 = stmt2.executeQuery("SELECT * "
                            + "FROM classes "
                            + "WHERE subjectId='" + rs.getString("id") + "'");
                    while (rs2.next()) {
                        Statement stmt3 = con.createStatement();
                        ResultSet rs3 = stmt3.executeQuery("SELECT * "
                                + "FROM exams "
                                + "WHERE classId='" + rs2.getString("id") + "'");
                        while (rs3.next()) {
                            Statement stmt4 = con.createStatement();
                            ResultSet rs4 = stmt4.executeQuery("SELECT * "
                                    + "FROM results "
                                    + "WHERE examId='" + rs3.getString("id") + "'"
                                    + "AND studentId='" + id + "'");
                            while (rs4.next()) {
                                String resultId = rs4.getString("id");
                                Statement stmt5 = con.createStatement();
                                ResultSet rs5 = stmt5.executeQuery("SELECT * "
                                        + "FROM exams "
                                        + "WHERE id='" + rs4.getString("examId") + "'");
                                while (rs5.next()) {
                                    Object[] row = {resultId, rs5.getString("name"),
                                        jComboBox6.getSelectedItem().toString(), rs5.getString("date")};
                                    model2.addRow(row);
                                }
                            }
                        }

                    }
                }
            } catch (SQLException e) {
            }
        }
    }//GEN-LAST:event_jComboBox6ActionPerformed

    private void jTextField21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField21ActionPerformed
        // TODO add your handling code here:
        if (jTextField21.getText().equals("")) {
            jTextField23.setText("");
            jTextField24.setText("");
            jTable5.clearSelection();
        } else {
            int r = jTable3.getSelectedRow();
            String id = jTable3.getValueAt(r, 0).toString();
            DefaultTableModel model2 = (DefaultTableModel) jTable5.getModel();
            model2.setRowCount(0);
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * "
                        + "FROM exams "
                        + "WHERE date='" + jTextField21.getText() + "'");
                while (rs.next()) {
                    Statement stmt2 = con.createStatement();
                    ResultSet rs2 = stmt2.executeQuery("SELECT * "
                            + "FROM results "
                            + "WHERE examId='" + rs.getString("id") + "'"
                            + "AND studentId='" + id + "'");
                    while (rs2.next()) {
                        String resultId = rs2.getString("id");
                        Statement stmt3 = con.createStatement();
                        ResultSet rs3 = stmt3.executeQuery("SELECT * "
                                + "FROM exams "
                                + "WHERE id='" + rs2.getString("examId") + "'");
                        while (rs3.next()) {
                            String examName = rs3.getString("name");
                            Statement stmt4 = con.createStatement();
                            ResultSet rs4 = stmt4.executeQuery("SELECT * "
                                    + "FROM classes "
                                    + "WHERE id='" + rs3.getString("classId") + "'");
                            while (rs4.next()) {
                                Statement stmt5 = con.createStatement();
                                ResultSet rs5 = stmt5.executeQuery("SELECT * "
                                        + "FROM subjects "
                                        + "WHERE id='" + rs4.getString("subjectId") + "'");
                                while (rs5.next()) {
                                    Object[] row = {resultId, examName,
                                        rs5.getString("grade") + " - " + rs5.getString("subject"),
                                        jTextField21.getText()};
                                    model2.addRow(row);
                                }
                            }

                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("#051" + e);
            }
        }
    }//GEN-LAST:event_jTextField21ActionPerformed

    private void jComboBox6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox6FocusGained
        // TODO add your handling code here:
        jTextField21.setText("");
    }//GEN-LAST:event_jComboBox6FocusGained

    private void jTextField21FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField21FocusGained
        // TODO add your handling code here:
        jComboBox6.setSelectedIndex(0);
    }//GEN-LAST:event_jTextField21FocusGained

    private void jTabbedPane4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane4MouseClicked

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        // TODO add your handling code here:
        jComboBox8.setSelectedIndex(0);
        jComboBox9.setSelectedIndex(0);
        jComboBox10.setSelectedIndex(0);
        loadGroups();
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jTable6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable6MouseClicked
        // TODO add your handling code here:
        Component[] com1 = jPanel26.getComponents();
        for (int a = 0; a < com1.length; a++) {
            com1[a].setEnabled(true);
        }
        Component[] com2 = jPanel27.getComponents();
        for (int a = 0; a < com2.length; a++) {
            com2[a].setEnabled(true);
        }
    }//GEN-LAST:event_jTable6MouseClicked

    private void jComboBox8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox8ActionPerformed
        // TODO add your handling code here:
        if (jComboBox8.getSelectedIndex() != 0 || jComboBox8.getSelectedItem() != null) {
            jComboBox9.setSelectedIndex(0);
            jComboBox10.setSelectedIndex(0);
            DefaultTableModel model = (DefaultTableModel) jTable6.getModel();
            model.setRowCount(0);
            String convertedDay = null;
            String[] parts = jComboBox8.getSelectedItem().toString().split(" - ");
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * "
                        + "FROM subjects "
                        + "WHERE grade='" + parts[0] + "'"
                        + "AND subject='" + parts[1] + "'");
                while (rs.next()) {
                    Statement stmt2 = con.createStatement();
                    ResultSet rs2 = stmt2.executeQuery("SELECT * "
                            + "FROM classes "
                            + "WHERE subjectId='" + rs.getString("id") + "'");
                    while (rs2.next()) {
                        String classId = rs2.getString("id");
                        int day = rs2.getInt("day");
                        Statement stmt3 = con.createStatement();
                        ResultSet rs3 = stmt3.executeQuery("SELECT * "
                                + "FROM teachers "
                                + "WHERE id='" + rs2.getString("teacherId") + "'");
                        while (rs3.next()) {
                            switch (day) {
                                case 1:
                                    convertedDay = "Monday";
                                    break;
                                case 2:
                                    convertedDay = "Tuesday";
                                    break;
                                case 3:
                                    convertedDay = "Wednesday";
                                    break;
                                case 4:
                                    convertedDay = "Thursday";
                                    break;
                                case 5:
                                    convertedDay = "Friday";
                                    break;
                                case 6:
                                    convertedDay = "Saturday";
                                    break;
                                case 7:
                                    convertedDay = "Sunday";
                                    break;
                            }
                            Object[] row = {classId, parts[0], parts[1],
                                rs3.getString("name"), convertedDay};
                            model.addRow(row);
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("#052" + e);
            }
        }
    }//GEN-LAST:event_jComboBox8ActionPerformed

    private void jComboBox9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox9ActionPerformed
        // TODO add your handling code here:
        if (jComboBox9.getSelectedIndex() != 0 || jComboBox9.getSelectedItem() != null) {
            jComboBox8.setSelectedIndex(0);
            jComboBox10.setSelectedIndex(0);
            DefaultTableModel model = (DefaultTableModel) jTable6.getModel();
            model.setRowCount(0);
            String convertedDay = null;
            String[] parts = jComboBox9.getSelectedItem().toString().split(" - ");
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * "
                        + "FROM classes "
                        + "WHERE teacherId='" + parts[0] + "'");
                while (rs.next()) {
                    String classId = rs.getString("id");
                    int day = rs.getInt("day");
                    Statement stmt2 = con.createStatement();
                    ResultSet rs2 = stmt2.executeQuery("SELECT * "
                            + "FROM subjects "
                            + "WHERE id='" + rs.getString("subjectId") + "'");
                    while (rs2.next()) {
                        switch (day) {
                            case 1:
                                convertedDay = "Monday";
                                break;
                            case 2:
                                convertedDay = "Tuesday";
                                break;
                            case 3:
                                convertedDay = "Wednesday";
                                break;
                            case 4:
                                convertedDay = "Thursday";
                                break;
                            case 5:
                                convertedDay = "Friday";
                                break;
                            case 6:
                                convertedDay = "Saturday";
                                break;
                            case 7:
                                convertedDay = "Sunday";
                                break;
                        }
                        Object[] row = {classId, rs2.getString("grade"),
                            rs2.getString("subject"), parts[1], convertedDay};
                        model.addRow(row);
                    }
                }
            } catch (SQLException e) {
                System.out.println("#053" + e);
            }
        }
    }//GEN-LAST:event_jComboBox9ActionPerformed

    private void jComboBox10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox10ActionPerformed
        // TODO add your handling code here:
        if (jComboBox10.getSelectedIndex() != 0 || jComboBox10.getSelectedItem() != null) {
            jComboBox8.setSelectedIndex(0);
            jComboBox9.setSelectedIndex(0);
            DefaultTableModel model = (DefaultTableModel) jTable6.getModel();
            model.setRowCount(0);
            int day = jComboBox10.getSelectedIndex();
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * "
                        + "FROM classes "
                        + "WHERE day='" + day + "'");
                while (rs.next()) {
                    String classId = rs.getString("id");
                    subjectId = rs.getString("subjectId");
                    teacherId = rs.getString("teacherId");
                    Statement stmt2 = con.createStatement();
                    ResultSet rs2 = stmt2.executeQuery("SELECT * "
                            + "FROM teachers "
                            + "WHERE id='" + teacherId + "'");
                    while (rs2.next()) {
                        String teacher = rs2.getString("name");
                        Statement stmt3 = con.createStatement();
                        ResultSet rs3 = stmt3.executeQuery("SELECT * "
                                + "FROM subjects "
                                + "WHERE id='" + subjectId + "'");
                        while (rs3.next()) {
                            Object[] row = {classId, rs3.getString("grade"),
                                rs3.getString("subject"), teacher,
                                jComboBox10.getSelectedItem().toString()};
                            model.addRow(row);
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("#054" + e);
            }
        }
    }//GEN-LAST:event_jComboBox10ActionPerformed

    private void jTable7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable7MouseClicked
        // TODO add your handling code here:
        int r = jTable7.getSelectedRow();
        jLabel29.setText(jTable7.getValueAt(r, 0).toString());
        jRadioButton2.setEnabled(true);
        jRadioButton3.setEnabled(true);
    }//GEN-LAST:event_jTable7MouseClicked

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
        if (jRadioButton1.isSelected()) {
            accountsReset();
            Component[] com1 = jPanel32.getComponents();
            for (int a = 0; a < com1.length; a++) {
                com1[a].setEnabled(true);
            }
            Component[] com2 = jPanel33.getComponents();
            for (int a = 0; a < com2.length; a++) {
                com2[a].setEnabled(false);
            }
            Component[] com3 = jPanel34.getComponents();
            for (int a = 0; a < com3.length; a++) {
                com3[a].setEnabled(false);
            }
        }
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
        if (jRadioButton2.isSelected()) {
            accountsReset();
            Component[] com1 = jPanel32.getComponents();
            for (int a = 0; a < com1.length; a++) {
                com1[a].setEnabled(false);
            }
            Component[] com2 = jPanel33.getComponents();
            for (int a = 0; a < com2.length; a++) {
                com2[a].setEnabled(true);
            }
            Component[] com3 = jPanel34.getComponents();
            for (int a = 0; a < com3.length; a++) {
                com3[a].setEnabled(false);
            }
        }
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        // TODO add your handling code here:
        if (jRadioButton3.isSelected()) {
            accountsReset();
            Component[] com1 = jPanel32.getComponents();
            for (int a = 0; a < com1.length; a++) {
                com1[a].setEnabled(false);
            }
            Component[] com2 = jPanel33.getComponents();
            for (int a = 0; a < com2.length; a++) {
                com2[a].setEnabled(false);
            }
            Component[] com3 = jPanel34.getComponents();
            for (int a = 0; a < com3.length; a++) {
                com3[a].setEnabled(true);
            }
        }
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        // TODO add your handling code here:
        loadAccounts();
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jComboBox13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox13ActionPerformed
        // TODO add your handling code here:
        if (jComboBox13.getSelectedIndex() != 0 || jComboBox13.getSelectedItem() != null) {
            DefaultTableModel model = (DefaultTableModel) jTable7.getModel();
            model.setRowCount(0);
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = (Statement) con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * "
                        + "FROM login "
                        + "WHERE type='" + jComboBox13.getSelectedItem() + "'");
                while (rs.next()) {
                    Object[] row2 = {rs.getString("id"),
                        rs.getString("type"), rs.getString("user"),
                        rs.getString("lastLogin")};
                    model.addRow(row2);
                }
                con.close();
            } catch (SQLException e) {
                System.out.println("#055" + e);
            }
        }
    }//GEN-LAST:event_jComboBox13ActionPerformed

    private void jTextField26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField26ActionPerformed
        // TODO add your handling code here:
        if (!jTextField26.getText().equals("")) {
            DefaultTableModel model = (DefaultTableModel) jTable7.getModel();
            model.setRowCount(0);
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = (Statement) con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * "
                        + "FROM login "
                        + "WHERE user='" + jTextField26.getText() + "'");
                while (rs.next()) {
                    Object[] row2 = {rs.getString("id"),
                        rs.getString("type"), rs.getString("user"),
                        rs.getString("lastLogin")};
                    model.addRow(row2);
                }
                con.close();
            } catch (SQLException e) {
                System.out.println("#056" + e);
            }
        }
    }//GEN-LAST:event_jTextField26ActionPerformed

    private void jComboBox12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox12ActionPerformed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        // TODO add your handling code here:
        if (jComboBox12.getSelectedIndex() == 0
                || jTextField28.getText().equals("") || jTextField29.getPassword().equals("")) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!");
        } else {
            String psw = String.valueOf(jTextField29.getPassword());
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                stmt.executeUpdate("INSERT INTO login "
                        + "(user,pass,type,lastLogin,status) "
                        + "VALUES "
                        + "('" + jTextField28.getText() + "','" + psw + "',"
                        + "'" + jComboBox12.getSelectedItem().toString() + "','0','0')");

                JOptionPane.showMessageDialog(this, "Success!");
            } catch (SQLException e) {
            }
            loadAccounts();
        }
    }//GEN-LAST:event_jButton30ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        // TODO add your handling code here:
        if (jTextField30.getPassword().equals("") || jTextField31.getPassword().equals("")) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!");
        } else {
            String psw1 = String.valueOf(jTextField30.getPassword());
            String psw2 = String.valueOf(jTextField31.getPassword());
            if (!psw1.equals(psw2)) {
                JOptionPane.showMessageDialog(this, "Passwords not matched!");
            } else {
                int r = jTable7.getSelectedRow();
                String id = jTable7.getValueAt(r, 0).toString();
                try {
                    Connection con = Helper.DB.connect();
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate("UPDATE login SET "
                            + "pass='" + psw1 + "' "
                            + "WHERE id='" + id + "'");
                    JOptionPane.showMessageDialog(this, "Success!");
                    loadAccounts();
                } catch (HeadlessException | SQLException e) {
                    System.out.println("#057" + e);
                }
            }
        }
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        // TODO add your handling code here:
        int r = jTable7.getSelectedRow();
        String id = jTable7.getValueAt(r, 0).toString();
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE "
                    + "FROM login "
                    + "WHERE id='" + id + "'");
            JOptionPane.showMessageDialog(this, "Success!");
            loadAccounts();
        } catch (HeadlessException | SQLException e) {
            System.out.println("#058" + e);
        }
    }//GEN-LAST:event_jButton32ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        examsReset();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        // TODO add your handling code here:
        ExamResults results = new ExamResults();
        results.setVisible(true);
    }//GEN-LAST:event_jButton33ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        loadExams();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        int r = jTable8.getSelectedRow();
        String id = jTable8.getValueAt(r, 0).toString();
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM exams "
                    + "WHERE id='" + id + "'");
            JOptionPane.showMessageDialog(this, "Success!");
            loadExams();
            con.close();
        } catch (HeadlessException | SQLException e) {
            System.out.println("#059" + e);
        }
    }//GEN-LAST:event_jButton18ActionPerformed

    private void cr1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cr1ActionPerformed
        // TODO add your handling code here:
        if (cr1.getSelectedIndex() == 0 || cr1.getSelectedItem() == null) {
            cr2.setEnabled(false);
            cr3.setEnabled(false);
            cr4.setEnabled(false);
            jTextField22.setText("");
            jTextField27.setText("");
            jTextField22.setEnabled(false);
            jTextField27.setEnabled(false);
        } else {
            cr1x = cr1.getSelectedItem().toString();
            cr2.setEnabled(true);
            cr2.removeAllItems();
            cr2.addItem("Please Select...");
            cr2.setSelectedIndex(0);
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * "
                        + "FROM subjects "
                        + "WHERE grade='" + cr1x + "'");
                while (rs.next()) {
                    cr2.addItem(rs.getString("subject"));
                }
                con.close();
            } catch (SQLException ex) {
                System.out.println("#060" + ex);
            }
        }
    }//GEN-LAST:event_cr1ActionPerformed

    private void cr2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cr2ActionPerformed
        // TODO add your handling code here:
        if (cr2.getSelectedIndex() == 0 || cr2.getSelectedItem() == null) {
            cr3.setEnabled(false);
            cr4.setEnabled(false);
            jTextField22.setText("");
            jTextField27.setText("");
            jTextField22.setEnabled(false);
            jTextField27.setEnabled(false);
        } else {
            cr2x = cr2.getSelectedItem().toString();
            cr3.setEnabled(true);
            cr3.removeAllItems();
            cr3.addItem("Please Select...");
            cr3.setSelectedIndex(0);
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * "
                        + "FROM subjects "
                        + "WHERE grade='" + cr1x + "' AND subject='" + cr2x + "'");
                while (rs.next()) {
                    subjectId = rs.getString("id");
                    Statement stmt2 = con.createStatement();
                    ResultSet rs2 = stmt2.executeQuery("SELECT * "
                            + "FROM classes "
                            + "WHERE subjectId='" + subjectId + "'");
                    while (rs2.next()) {
                        teacherId = rs2.getString("teacherId");
                        Statement stmt3 = con.createStatement();
                        ResultSet rs3 = stmt3.executeQuery("SELECT * "
                                + "FROM teachers "
                                + "WHERE id='" + teacherId + "'");
                        while (rs3.next()) {
                            cr3.addItem(rs3.getString("name"));
                        }
                    }
                }
                con.close();
            } catch (SQLException ex) {
                System.out.println("#061" + ex);
            }
        }
    }//GEN-LAST:event_cr2ActionPerformed

    private void cr3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cr3ActionPerformed
        // TODO add your handling code here:
        if (cr3.getSelectedIndex() == 0 || cr3.getSelectedItem() == null) {
            cr4.setEnabled(false);
            jTextField22.setText("");
            jTextField27.setText("");
            jTextField22.setEnabled(false);
            jTextField27.setEnabled(false);
        } else {
            cr4.setEnabled(true);
            cr4.removeAllItems();
            cr4.addItem("Please Select...");
            cr4.setSelectedIndex(0);
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * "
                        + "FROM classes "
                        + "WHERE subjectId='" + subjectId + "' AND teacherId='" + teacherId + "'");
                while (rs.next()) {
                    dayCho = Integer.parseInt(rs.getString("day"));
                    String convertDay = null;
                    switch (dayCho) {
                        case 1:
                            convertDay = "Monday";
                            break;
                        case 2:
                            convertDay = "Tuesday";
                            break;
                        case 3:
                            convertDay = "Wednesday";
                            break;
                        case 4:
                            convertDay = "Thursday";
                            break;
                        case 5:
                            convertDay = "Friday";
                            break;
                        case 6:
                            convertDay = "Saturday";
                            break;
                        case 7:
                            convertDay = "Sunday";
                            break;
                    }
                    cr4.addItem(convertDay);
                }
                con.close();
            } catch (SQLException ex) {
                System.out.println("#062" + ex);
            }
        }
    }//GEN-LAST:event_cr3ActionPerformed

    private void cr4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cr4ActionPerformed
        // TODO add your handling code here:
        if (cr4.getSelectedIndex() == 0) {
            jTextField22.setText("");
            jTextField27.setText("");
            jTextField32.setText("");
            jTextField22.setEnabled(false);
            jTextField27.setEnabled(false);
            jTextField32.setEnabled(false);
        } else {
            jTextField22.setEnabled(true);
            jTextField27.setEnabled(true);
            jTextField32.setEnabled(true);
        }


    }//GEN-LAST:event_cr4ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        if (jTextField22.getText().equals("") || jTextField27.getText().equals("")
                || jTextField32.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!");
        } else {
            String classId;
            String examTime = jTextField32.getText() + " " + jComboBox15.getSelectedItem().toString();
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * "
                        + "FROM classes "
                        + "WHERE subjectId='" + subjectId + "' "
                        + "AND teacherId='" + teacherId + "'"
                        + "AND day='" + dayCho + "'");
                while (rs.next()) {
                    classId = rs.getString("id");
                    Statement stmt2 = con.createStatement();
                    stmt2.executeUpdate("INSERT INTO exams "
                            + "(name,classId,date,time,duration) "
                            + "VALUES "
                            + "('" + jTextField22.getText() + "',"
                            + "'" + classId + "',"
                            + "'" + jTextField27.getText() + "',"
                            + "'" + examTime + "','0')");
                    tGroupId.setText(rs.getString("telegramId"));
                    tAutomated.newExamAddedGroup();
                    Statement stmt3 = con.createStatement();
                    ResultSet rs2 = stmt3.executeQuery("SELECT * "
                            + "FROM regclass "
                            + "WHERE classId='" + classId + "'");
                    while (rs2.next()) {
                        String studentId = rs2.getString("studentId");
                        Statement stmt4 = con.createStatement();
                        ResultSet rs3 = stmt4.executeQuery("SELECT * "
                                + "FROM students "
                                + "WHERE id='" + studentId + "'");
                        while (rs3.next()) {
                            telegramId.setText(rs3.getString("telegramId"));
                            Statement stmt5 = con.createStatement();
                            ResultSet rs5 = stmt5.executeQuery("SELECT * "
                                    + "FROM exams "
                                    + "ORDER BY id DESC LIMIT 1");
                            while (rs5.next()) {
                                int examIdx = rs5.getInt("id");
                                Statement stmt6 = con.createStatement();
                                stmt6.executeUpdate("INSERT INTO results "
                                        + "(examId,studentId,marks) "
                                        + "VALUES "
                                        + "('" + examIdx + "','" + studentId + "','N/A')");
                                tAutomated.newExamAddedStudent();
                            }
                        }
                    }
                }
                JOptionPane.showMessageDialog(this, "Success!");
                loadExams();
            } catch (SQLException e) {
                System.out.println("#063" + e);
            }
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jTable8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable8MouseClicked
        // TODO add your handling code here:
        Component[] com1 = jPanel38.getComponents();
        for (int a = 0; a < com1.length; a++) {
            com1[a].setEnabled(true);
        }
        Component[] com2 = jPanel39.getComponents();
        for (int a = 0; a < com2.length; a++) {
            com2[a].setEnabled(true);
        }
        int r = jTable8.getSelectedRow();
        String id = jTable8.getValueAt(r, 0).toString();
        examId.setText(id);
    }//GEN-LAST:event_jTable8MouseClicked

    private void jTextField19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField19ActionPerformed
        // TODO add your handling code here:
        if (!jTextField19.getText().equals("")) {
            DefaultTableModel model = (DefaultTableModel) jTable8.getModel();
            model.setRowCount(0);
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * "
                        + "FROM exams "
                        + "WHERE name='" + jTextField19.getText() + "'");
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String date = rs.getString("date");
                    Statement stmt2 = con.createStatement();
                    ResultSet rs2 = stmt2.executeQuery("SELECT * "
                            + "FROM classes "
                            + "WHERE id='" + rs.getString("classId") + "'");
                    while (rs2.next()) {
                        Statement stmt3 = con.createStatement();
                        ResultSet rs3 = stmt3.executeQuery("SELECT * "
                                + "FROM subjects "
                                + "WHERE id='" + rs2.getString("subjectId") + "'");
                        {
                            while (rs3.next()) {
                                Object[] row = {id, name, rs3.getString("grade")
                                    + " - " + rs3.getString("subject"), date};
                                model.addRow(row);
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("#064" + e);
            }
        }
    }//GEN-LAST:event_jTextField19ActionPerformed

    private void jTextField20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField20ActionPerformed
        // TODO add your handling code here:
        if (!jTextField20.getText().equals("")) {
            DefaultTableModel model = (DefaultTableModel) jTable8.getModel();
            model.setRowCount(0);
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * "
                        + "FROM exams "
                        + "WHERE date='" + jTextField20.getText() + "'");
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String date = rs.getString("date");
                    Statement stmt2 = con.createStatement();
                    ResultSet rs2 = stmt2.executeQuery("SELECT * "
                            + "FROM classes "
                            + "WHERE id='" + rs.getString("classId") + "'");
                    while (rs2.next()) {
                        Statement stmt3 = con.createStatement();
                        ResultSet rs3 = stmt3.executeQuery("SELECT * "
                                + "FROM subjects "
                                + "WHERE id='" + rs2.getString("subjectId") + "'");
                        while (rs3.next()) {
                            Object[] row = {id, name, rs3.getString("grade")
                                + " - " + rs3.getString("subject"), date};
                            model.addRow(row);
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("#065" + e);
            }
        }
    }//GEN-LAST:event_jTextField20ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
        if (jComboBox3.getSelectedIndex() != 0 && jComboBox3.getSelectedItem() != null) {
            DefaultTableModel model = (DefaultTableModel) jTable8.getModel();
            model.setRowCount(0);
            String[] parts = jComboBox3.getSelectedItem().toString().split(" - ");
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * "
                        + "FROM subjects "
                        + "WHERE grade='" + parts[0] + "' "
                        + "AND subject='" + parts[1] + "'");
                while (rs.next()) {
                    Statement stmt2 = con.createStatement();
                    ResultSet rs2 = stmt2.executeQuery("SELECT * "
                            + "FROM exams "
                            + "WHERE subjectId='" + rs.getString("id") + "'");
                    while (rs2.next()) {
                        Object[] row = {rs2.getString("id"), rs2.getString("name"),
                            jComboBox3.getSelectedItem().toString(), rs2.getString("date")};
                        model.addRow(row);
                    }
                }
            } catch (SQLException e) {
                System.out.println("#066" + e);
            }
        }
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void tupdatelabelPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tupdatelabelPropertyChange
        // TODO add your handling code here:
        if (success.getText().equals("studentUpdate")) {
            studentUpdate();
            success.setText("");
        }
    }//GEN-LAST:event_tupdatelabelPropertyChange

    private void jLabel67PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jLabel67PropertyChange
        // TODO add your handling code here:
        if (success.getText().equals("teacherAdd")) {
            teacherAdd();
            success.setText("");
        }
        if (success.getText().equals("teacherUpdate")) {
            teacherUpdate();
            success.setText("");
        }
    }//GEN-LAST:event_jLabel67PropertyChange

    private void jLabel72PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jLabel72PropertyChange
        // TODO add your handling code here:
        if (success.getText().equals("groupAdd")) {
            groupAdd();
            success.setText("");
        }
    }//GEN-LAST:event_jLabel72PropertyChange

    private void jLabel68PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jLabel68PropertyChange
        // TODO add your handling code here:
        if (success.getText().equals("groupTIdUpdate")) {
            success.setText("");
            groupTIdUpdate();
        }
    }//GEN-LAST:event_jLabel68PropertyChange

    private void examsReset() {
        cr2.setSelectedIndex(0);
        cr3.setSelectedIndex(0);
        cr4.setSelectedIndex(0);
        jTextField22.setText("");
        jTextField27.setText("");
        jTextField32.setText("");
        cr2.setEnabled(false);
        cr3.setEnabled(false);
        cr4.setEnabled(false);
        jTextField22.setEnabled(false);
        jTextField27.setEnabled(false);
        jTextField32.setEnabled(false);
    }

    private void accountsReset() {
        jComboBox12.setSelectedIndex(0);
        jTextField28.setText("");
        jTextField29.setText("");
        jTextField30.setText("");
        jTextField31.setText("");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea broadcastMessage;
    private javax.swing.ButtonGroup buttonGroup1;
    public static javax.swing.JComboBox<String> cr1;
    public static javax.swing.JComboBox<String> cr2;
    public static javax.swing.JComboBox<String> cr3;
    public static javax.swing.JComboBox<String> cr4;
    public static javax.swing.JLabel examId;
    public static javax.swing.JLabel fakeNumber;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox10;
    public static javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JComboBox<String> jComboBox12;
    private javax.swing.JComboBox<String> jComboBox13;
    private javax.swing.JComboBox<String> jComboBox14;
    public static javax.swing.JComboBox<String> jComboBox15;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    public static javax.swing.JLabel jLabel65;
    public static javax.swing.JLabel jLabel66;
    public static javax.swing.JLabel jLabel67;
    public static javax.swing.JLabel jLabel68;
    public static javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    public static javax.swing.JLabel jLabel71;
    public static javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTable jTable8;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    public static javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    public static javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JPasswordField jTextField29;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JPasswordField jTextField30;
    private javax.swing.JPasswordField jTextField31;
    public static javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    public static javax.swing.JLabel returnMethod;
    public static javax.swing.JLabel success;
    public static javax.swing.JLabel tGroupId;
    public static javax.swing.JLabel telegramId;
    public static javax.swing.JLabel tupdatelabel;
    public static javax.swing.JLabel type;
    // End of variables declaration//GEN-END:variables
}
