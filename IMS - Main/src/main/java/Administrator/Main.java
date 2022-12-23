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
import java.io.File;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Stream;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.io.FileUtils;

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
    String cr1x;
    String cr2x;
    String subjectId;
    String teacherId;
    String tableSelection;
    String examId;
    int dayCho;
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
        int paymentCount = 0;
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM classes");
            while (rs.next()) {
                String classId = rs.getString("id");
                int payment = rs.getInt("payment");
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT COUNT(id) "
                        + "FROM payments "
                        + "WHERE classId='" + classId + "' "
                        + "AND year='" + year + "' "
                        + "AND month='" + month + "'");
                while (rs2.next()) {
                    int count = rs2.getInt(1);
                    paymentCount = paymentCount + (payment * count);
                }
            }
        } catch (SQLException e) {
            System.out.println("#005" + e);
        }
        jTextField4.setText("" + paymentCount);
    }

    private void loadTeachers() {
        jTextField10.setText("");
        jTextField11.setText("");
        jTextField12.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jTextField8.setText("");
        jCheckBox1.setSelected(false);
        jButton7.setEnabled(false);
        jButton6.setEnabled(false);
        jComboBox2.setSelectedIndex(0);
        jTextField25.setText("");
        jComboBox14.setSelectedIndex(0);
        jTextField36.setText("");
        jTextField41.setText("");
        jTextField37.setText("");
        jTextField38.setText("");
        jButton13.setEnabled(false);
        jButton37.setEnabled(false);
        jButton15.setEnabled(false);
        jTextArea2.setText("");
        jButton21.setEnabled(false);
        DefaultTableModel model2 = (DefaultTableModel) jTable2.getModel();
        model2.setRowCount(0);

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
    }

    private void loadStudents() {
        jTextField9.setText("");
        jTextField13.setText("");
        jComboBox4.setSelectedIndex(0);
        jTextField14.setText("");
        jTextField15.setText("");
        jTextField16.setText("");
        jTextField17.setText("");
        jTextField18.setText("");
        jComboBox5.setSelectedIndex(0);
        jCheckBox2.setSelected(false);
        jLabel27.setIcon(null);
        jLabel27.setText("-");
        jButton9.setEnabled(false);
        jButton10.setEnabled(false);
        jButton43.setEnabled(false);
        DefaultTableModel model4 = (DefaultTableModel) jTable4.getModel();
        model4.setRowCount(0);
        DefaultTableModel model5 = (DefaultTableModel) jTable5.getModel();
        model5.setRowCount(0);
        jTextField23.setText("");
        jTextField24.setText("");
        jComboBox6.removeAllItems();
        jComboBox6.addItem("Please Select...");
        jComboBox6.setSelectedIndex(0);
        jTextField21.setText("");
        jTextArea1.setText("");
        jButton19.setEnabled(false);

        try {
            Connection con = Helper.DB.connect();
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM students");
            DefaultTableModel model3 = (DefaultTableModel) jTable3.getModel();
            model3.setRowCount(0);
            while (rs2.next()) {
                Object[] row2 = {rs2.getString("id"),
                    rs2.getString("firstName") + " " + rs2.getString("lastName"),
                    rs2.getString("grade")};
                model3.addRow(row2);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("#007" + e);
        }
    }

    private void loadClasses() {
        jButton24.setEnabled(false);
        jButton26.setEnabled(false);
        jTextArea3.setText("");

        DefaultTableModel model6 = (DefaultTableModel) jTable6.getModel();
        model6.setRowCount(0);
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM classes");
            while (rs.next()) {
                String classId = rs.getString("id");
                String time = rs.getString("time");
                String duration = rs.getString("duration");
                subjectId = rs.getString("subjectId");
                int day = rs.getInt("day");
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
                        Object[] row = {classId, rs3.getString("grade"), rs3.getString("subject"),
                            teacher, convertedDay, time, duration};
                        model6.addRow(row);
                    }
                }

            }
            con.close();
        } catch (SQLException e) {
            System.out.println("#008" + e);
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
                jComboBox9.addItem(rs.getString("name"));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("#010" + e);
        }
    }

    private void loadExams() {
        jTextField19.setText("");
        jTextField20.setText("");

        cr1.setSelectedIndex(0);
        cr2.setSelectedIndex(0);
        cr2.setEnabled(false);
        cr3.setSelectedIndex(0);
        cr3.setEnabled(false);
        cr4.setSelectedIndex(0);
        cr4.setEnabled(false);
        cr5.setSelectedIndex(0);
        cr5.setEnabled(false);
        jTextField22.setText("");
        jTextField27.setText("");
        jTextField32.setText("");
        jTextField42.setText("");
        jTextField39.setText("");
        jTextField40.setText("");
        jTextField22.setEnabled(false);
        jTextField27.setEnabled(false);
        jTextField32.setEnabled(false);
        jTextField42.setEnabled(false);
        jTextField39.setEnabled(false);
        jTextField40.setEnabled(false);
        jComboBox15.setEnabled(false);
        jButton42.setEnabled(false);
        jButton18.setEnabled(false);
        jButton33.setEnabled(false);

        DefaultTableModel model8 = (DefaultTableModel) jTable8.getModel();
        model8.setRowCount(0);
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
                String duration = rs.getString("duration");
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT * "
                        + "FROM classes "
                        + "WHERE id='" + rs.getString("classId") + "'");
                while (rs2.next()) {
                    String subjectId = rs2.getString("subjectId");
                    Statement stmt3 = con.createStatement();
                    ResultSet rs3 = stmt3.executeQuery("SELECT * "
                            + "FROM teachers "
                            + "WHERE id='" + rs2.getString("teacherId") + "'");
                    while (rs3.next()) {
                        String teacherName = rs3.getString("name");
                        Statement stmt4 = con.createStatement();
                        ResultSet rs4 = stmt4.executeQuery("SELECT * "
                                + "FROM subjects "
                                + "WHERE id='" + subjectId + "'");
                        while (rs4.next()) {
                            Object[] row = {id, name, rs4.getString("grade")
                                + " - " + rs4.getString("subject"), teacherName,
                                date, time, duration};
                            model8.addRow(row);
                        }
                    }
                }
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("#011" + e);
        }
        jComboBox3.removeAllItems();
        jComboBox3.addItem("Please Select...");
        jComboBox3.setSelectedIndex(0);
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM subjects");
            while (rs.next()) {
                jComboBox3.addItem(rs.getString("grade")
                        + " - " + rs.getString("subject"));
            }
        } catch (SQLException e) {
            System.out.println("#70" + e);
        }
    }

    private void loadSubjects() {
        jTextField34.setText("");
        jComboBox17.setSelectedIndex(0);
        jTextField35.setText("");
        jButton35.setEnabled(false);
        jComboBox16.setSelectedIndex(0);
        jTextField33.setText("");
        DefaultTableModel model9 = (DefaultTableModel) jTable9.getModel();
        model9.setRowCount(0);
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM subjects");
            while (rs.next()) {
                Object[] row = {rs.getString("id"),
                    rs.getString("grade"), rs.getString("subject")};
                model9.addRow(row);
            }
        } catch (SQLException e) {
            System.out.println("#082" + e);
        }
    }

    private void loadAccounts() {
        jComboBox13.setSelectedIndex(0);
        jTextField26.setText("");
        jComboBox12.setSelectedIndex(0);
        jTextField28.setText("");
        jTextField29.setText("");
        jTextField30.setText("");
        jTextField31.setText("");

        DefaultTableModel model7 = (DefaultTableModel) jTable7.getModel();
        model7.setRowCount(0);
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM login");
            while (rs.next()) {
                Object[] row = {rs.getString("id"),
                    rs.getString("type"), rs.getString("user"),
                    rs.getString("lastLogin")};
                model7.addRow(row);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("#012" + e);
        }
        jRadioButton1.setSelected(true);
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
        type = new javax.swing.JLabel();
        success = new javax.swing.JLabel();
        returnMethod = new javax.swing.JLabel();
        longDetails = new javax.swing.JLabel();
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
        jButton37 = new javax.swing.JButton();
        jLabel54 = new javax.swing.JLabel();
        jTextField25 = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        jComboBox14 = new javax.swing.JComboBox<>();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jComboBox18 = new javax.swing.JComboBox<>();
        jTextField36 = new javax.swing.JTextField();
        jTextField37 = new javax.swing.JTextField();
        jLabel80 = new javax.swing.JLabel();
        jTextField38 = new javax.swing.JTextField();
        jLabel81 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jTextField41 = new javax.swing.JTextField();
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
        jPanel16 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jLabel53 = new javax.swing.JLabel();
        jButton43 = new javax.swing.JButton();
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
        jPanel45 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTable10 = new javax.swing.JTable();
        jPanel46 = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jTextField43 = new javax.swing.JTextField();
        jTextField44 = new javax.swing.JTextField();
        jTextField45 = new javax.swing.JTextField();
        jTextField46 = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        jTextField47 = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jTextField48 = new javax.swing.JTextField();
        jComboBox19 = new javax.swing.JComboBox<>();
        jPanel48 = new javax.swing.JPanel();
        jButton38 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        jLabel72 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jButton24 = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        jButton25 = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        jButton26 = new javax.swing.JButton();
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
        jLabel82 = new javax.swing.JLabel();
        cr5 = new javax.swing.JComboBox<>();
        jLabel83 = new javax.swing.JLabel();
        jTextField39 = new javax.swing.JTextField();
        jLabel84 = new javax.swing.JLabel();
        jTextField40 = new javax.swing.JTextField();
        jLabel85 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jTextField42 = new javax.swing.JTextField();
        jButton42 = new javax.swing.JButton();
        jCheckBox3 = new javax.swing.JCheckBox();
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
        jPanel40 = new javax.swing.JPanel();
        jPanel41 = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jComboBox16 = new javax.swing.JComboBox<>();
        jTextField33 = new javax.swing.JTextField();
        jButton34 = new javax.swing.JButton();
        jPanel43 = new javax.swing.JPanel();
        jButton35 = new javax.swing.JButton();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTable9 = new javax.swing.JTable();
        jPanel44 = new javax.swing.JPanel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jTextField34 = new javax.swing.JTextField();
        jComboBox17 = new javax.swing.JComboBox<>();
        jTextField35 = new javax.swing.JTextField();
        jButton36 = new javax.swing.JButton();
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
        jPanel47 = new javax.swing.JPanel();

        telegramId.setText("jLabel53");

        type.setText("jLabel65");

        success.setText("jLabel65");
        success.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                successPropertyChange(evt);
            }
        });

        returnMethod.setText("jLabel65");

        longDetails.setText("jLabel82");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
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
        });
        jScrollPane1.setViewportView(jTable1);

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
                        .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                    .addComponent(jCheckBox1))
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
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                "Class ID", "Grade", "Subject"
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

        jButton37.setText("Update");
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel54.setText("Payment :");

        jLabel55.setText("Day :");

        jComboBox14.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select...", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" }));

        jLabel78.setText("Time :");

        jLabel79.setText("Duration :");

        jComboBox18.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));

        jTextField37.setToolTipText("(3H 30MIN)");

        jLabel80.setText("(h)");

        jLabel81.setText("(min)");

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel86.setText(":");

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
                            .addComponent(jLabel79, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel78, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel55, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel54, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField25)
                            .addComponent(jComboBox14, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField36)
                                    .addComponent(jTextField37, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel86, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField41)
                                    .addComponent(jTextField38))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel81)
                                    .addComponent(jComboBox18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel78)
                    .addComponent(jComboBox18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel86)
                    .addComponent(jTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel79)
                    .addComponent(jTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel80)
                    .addComponent(jTextField38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel81))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
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

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select...", "6", "7", "8", "9", "10", "11" }));
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
                        .addComponent(jTextField14, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE))
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
                                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jCheckBox2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                    .addComponent(jCheckBox2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        jButton43.setText("Get a copy of student admission card");
        jButton43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton43ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jButton43, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
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
                .addContainerGap(120, Short.MAX_VALUE))
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
                .addContainerGap(101, Short.MAX_VALUE))
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
                "ID", "Grade", "Subject", "Teacher", "Day", "Time", "Duration"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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

        jLabel37.setText("by Grade :");

        jLabel38.setText("by Teacher :");

        jLabel39.setText("by Day :");

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select...", "6", "7", "8", "9", "10", "11" }));
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

        jPanel45.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTable10.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Date", "Time", "Duration"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable10MouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(jTable10);

        jPanel46.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Editing Panel :"));

        jLabel65.setText("Name :");

        jLabel66.setText("Date :");

        jLabel67.setText("Time :");

        jLabel68.setText("Duration :");

        jLabel69.setText("(h)");

        jLabel70.setText("(min)");

        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel71.setText(":");

        jComboBox19.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));

        jPanel48.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jButton38.setText("Add");
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });

        jButton39.setText("Delete");
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });

        jButton40.setText("Reset");
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });

        jButton41.setText("Update");
        jButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton41ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel48Layout.createSequentialGroup()
                        .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton41, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel72.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel72.setText("[ Students will inform via Telegram Bot ]");

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField43))
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField44))
                    .addComponent(jPanel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel46Layout.createSequentialGroup()
                                .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel71, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel46Layout.createSequentialGroup()
                                .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel69)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel70)
                            .addComponent(jComboBox19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel65)
                    .addComponent(jTextField43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(jTextField44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel67)
                    .addComponent(jTextField45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel71)
                    .addComponent(jTextField48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel68)
                    .addComponent(jTextField46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel69)
                    .addComponent(jTextField47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel70))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel72)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane14)
                    .addComponent(jPanel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Specials", jPanel45);

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
                .addContainerGap(101, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Message", jPanel26);

        jPanel27.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton26.setText("Update Group via Telegram");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton26, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(508, Short.MAX_VALUE))
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

        jTabbedPane1.addTab("Classes", jPanel14);

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

        jLabel82.setText("Time :");

        cr5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select..." }));
        cr5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cr5ActionPerformed(evt);
            }
        });

        jLabel83.setText("Exam Duration :");

        jLabel84.setText("(h)");

        jLabel85.setText("(min)");

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel87.setText(":");

        jButton42.setText("Update");
        jButton42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton42ActionPerformed(evt);
            }
        });

        jCheckBox3.setText("Use class time");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });

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
                        .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton42, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel82, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel59, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cr4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cr5, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel83, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel64, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabel61, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField27)
                            .addGroup(jPanel37Layout.createSequentialGroup()
                                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField32)
                                    .addComponent(jTextField39))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel84)
                                    .addComponent(jLabel87, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField42)
                                    .addComponent(jTextField40))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel85)
                                    .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jCheckBox3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                    .addComponent(jLabel82)
                    .addComponent(cr5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(jCheckBox3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel87)
                    .addComponent(jTextField42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel83)
                    .addComponent(jTextField39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel84)
                    .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel85))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel62)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton42, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                "ID", "Name", "Class", "Teacher", "Date", "Time", "Duration"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Exams", jPanel19);

        jPanel40.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel41.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel42.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Add New :")));

        jLabel73.setText("Grade :");

        jLabel74.setText("Subject Name :");

        jComboBox16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select...", "5", "6", "7", "8", "9", "10", "11" }));

        jButton34.setText("Add");
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox16, 0, 316, Short.MAX_VALUE))
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel42Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel73)
                    .addComponent(jComboBox16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel74)
                    .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel43.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Delete :"));

        jButton35.setText("Delete Selected Subject");
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable9.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Grade", "Subject Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable9MouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(jTable9);

        jPanel44.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Search :"));

        jLabel75.setText("by ID :");

        jLabel76.setText("by Grade :");

        jLabel77.setText("by Name :");

        jTextField34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField34ActionPerformed(evt);
            }
        });

        jComboBox17.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select...", "5", "6", "7", "8", "9", "10", "11" }));
        jComboBox17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox17ActionPerformed(evt);
            }
        });

        jTextField35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField35ActionPerformed(evt);
            }
        });

        jButton36.setText("Reset");
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField34))
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addComponent(jLabel76, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox17, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField35)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton36)
                .addContainerGap())
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton36, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel75)
                            .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel76)
                            .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel77)
                            .addComponent(jTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                    .addComponent(jPanel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Subjects", jPanel40);

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
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Accounts", jPanel28);

        jPanel47.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1020, Short.MAX_VALUE)
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 591, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("App", jPanel47);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (ExamResults.disposeText != null) {
            ExamResults.disposeText.setText("0");
        }
        if (TelegramReports.disposeText != null) {
            TelegramReports.disposeText.setText("0");
        }
        if (TelegramVerify.disposeText != null) {
            TelegramVerify.disposeText.setText("0");
        }
        MainPkg.Welcome logout = new MainPkg.Welcome();
        logout.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        // getting the admin's message and send the message
        if (broadcastMessage.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter message to broadcast!");
        } else {
            TelegramReports reports = new TelegramReports();
            reports.setVisible(true);
            int totalCount;
            String to = jComboBox11.getSelectedItem().toString();
            String table = null;
            switch (to) {
                case "All Students" ->
                    table = "students";
                case "All Teachers" ->
                    table = "teachers";
                case "All Groups" ->
                    table = "classes";
            }
            DefaultTableModel model = (DefaultTableModel) TelegramReports.jTable1.getModel();
            model.setRowCount(0);
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(id) "
                        + "FROM " + table + "");
                while (rs.next()) {
                    totalCount = rs.getInt(1);
                    TelegramReports.jLabel6.setText("" + totalCount);
                    Statement stmt2 = con.createStatement();
                    ResultSet rs2 = stmt2.executeQuery("SELECT * "
                            + "FROM " + table + "");
                    while (rs2.next()) {
                        String telegramIdx = rs2.getString("telegramId");
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
                        Object[] row = {telegramIdx, sName};
                        model.addRow(row);
                    }
                }
                con.close();
            } catch (SQLException e) {
                System.out.println("#014" + e);
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

        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);
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
        jButton13.setEnabled(true);
        jButton21.setEnabled(true);

        //============================================
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
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if (jTextField5.getText().equals("") || jTextField6.getText().equals("")
                || jTextField7.getText().equals("") || jTextField8.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!");
        } else {
            jCheckBox1.setSelected(true);
            TelegramVerify tVerify = new TelegramVerify();
            tVerify.setVisible(true);
            Faker faker = new Faker();
            TelegramVerify.jTextField1.setText(faker.number().digits(5));
            type.setText("private");
            returnMethod.setText("teacherAdd");
        }
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
            con.close();
            loadTeachers();
            JOptionPane.showMessageDialog(this, "Success!");
        } catch (SQLException e) {
            System.out.println("#018" + e);
        }
    }

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // updating data in teachers table
        if (jTextField5.getText().equals("") || jTextField6.getText().equals("")
                || jTextField7.getText().equals("") || jTextField8.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!");
        } else {
            int r = jTable1.getSelectedRow();
            tableSelection = jTable1.getValueAt(r, 0).toString();
            if (jCheckBox1.isSelected()) {
                TelegramVerify tVerify = new TelegramVerify();
                tVerify.setVisible(true);
                Faker faker = new Faker();
                TelegramVerify.jTextField1.setText(faker.number().digits(5));
                type.setText("private");
                returnMethod.setText("teacherUpdate");
            } else {
                String name = jTextField5.getText();
                String nic = jTextField6.getText();
                String contact = jTextField7.getText();
                String address = jTextField8.getText();
                int status = jComboBox1.getSelectedIndex();
                try {
                    Connection con = Helper.DB.connect();
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate("UPDATE teachers SET "
                            + "name='" + name + "', "
                            + "nic='" + nic + "', "
                            + "address='" + address + "', "
                            + "contact='" + contact + "', "
                            + "status='" + status + "' "
                            + "WHERE id='" + tableSelection + "'");
                    con.close();
                    loadTeachers();
                    JOptionPane.showMessageDialog(this, "Success!");
                } catch (SQLException e) {
                    System.out.println("#019" + e);
                }
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
            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE teachers SET "
                    + "name='" + name + "', "
                    + "nic='" + nic + "', "
                    + "address='" + address + "', "
                    + "telegramId='" + telegramId.getText() + "', "
                    + "contact='" + contact + "', "
                    + "status='" + status + "' "
                    + "WHERE id='" + tableSelection + "'");
            con.close();
            loadTeachers();
            JOptionPane.showMessageDialog(this, "Success!");
        } catch (SQLException e) {
            System.out.println("#020" + e);
        }
    }

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int r = jTable1.getSelectedRow();
        String id = jTable1.getValueAt(r, 0).toString();
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = (Statement) con.createStatement();
            stmt.executeUpdate("DELETE FROM teachers WHERE id='" + id + "'");
            con.close();
            loadTeachers();
            JOptionPane.showMessageDialog(this, "Success!");
        } catch (SQLException e) {
            System.out.println("#021" + e);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if (jTextField14.getText().equals("") || jTextField15.getText().equals("")
                || jTextField16.getText().equals("") || jTextField17.getText().equals("")
                || jTextField18.getText().equals("") || jComboBox5.getSelectedIndex() == 0) {
            JOptionPane.showConfirmDialog(this, "All fields must be filled!");
        } else {
            if (jCheckBox2.isSelected()) {
                TelegramVerify tVerify = new TelegramVerify();
                tVerify.setVisible(true);
                Faker faker = new Faker();
                TelegramVerify.jTextField1.setText(faker.number().digits(5));
                type.setText("private");
                returnMethod.setText("studentUpdate");
            } else {
                int r = jTable3.getSelectedRow();
                try {
                    Connection con = Helper.DB.connect();
                    Statement stmt = (Statement) con.createStatement();
                    stmt.executeUpdate("UPDATE students SET "
                            + "firstName='" + jTextField14.getText() + "', "
                            + "lastName='" + jTextField15.getText() + "', "
                            + "guardianName='" + jTextField16.getText() + "', "
                            + "guardianPhone='" + jTextField17.getText() + "', "
                            + "address='" + jTextField18.getText() + "', "
                            + "grade='" + jComboBox5.getSelectedItem().toString() + "', "
                            + "status='" + jComboBox7.getSelectedIndex() + "' "
                            + "WHERE id='" + jTable3.getValueAt(r, 0).toString() + "'");
                    con.close();
                    loadStudents();
                    JOptionPane.showMessageDialog(this, "Success!");
                } catch (SQLException e) {
                    System.out.println("#022" + e);
                }
            }
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    public void studentUpdate() {
        int r = jTable3.getSelectedRow();
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = (Statement) con.createStatement();
            stmt.executeUpdate("UPDATE students "
                    + "SET firstName='" + jTextField14.getText() + "', "
                    + "lastName='" + jTextField15.getText() + "', "
                    + "guardianName='" + jTextField16.getText() + "', "
                    + "guardianPhone='" + jTextField17.getText() + "', "
                    + "address='" + jTextField18.getText() + "', "
                    + "grade='" + jComboBox5.getSelectedItem().toString() + "', "
                    + "telegramId='" + telegramId.getText() + "', "
                    + "status='" + String.valueOf(jComboBox7.getSelectedIndex()) + "' "
                    + "WHERE id='" + jTable3.getValueAt(r, 0).toString() + "'");
            con.close();
            loadStudents();
            JOptionPane.showMessageDialog(this, "Success!");
        } catch (SQLException e) {
            System.out.println("#023" + e);
        }
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        loadTeachers();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // send message from teacher panel to telegram
        if (jTextArea2.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter message to send!");
        } else {
            int r = jTable1.getSelectedRow();
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
                        String teacherIdn = rs.getString("id");
                        String Teleid = rs.getString("telegramId");
                        String name2 = rs.getString("name");
                        sm.setText(jTextArea2.getText());
                        sm.setChatId(Teleid);
                        try {
                            telegram.execute(sm);
                            output.append("\n" + logTime + " - " + teacherIdn + " -\n" + jTextArea2.getText());
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
        if (!jTextField10.getText().equals("")) {
            ;
            String teacherIdx = jTextField10.getText();
            ArrayList<String> teacherIdn = new ArrayList<>();
            ArrayList<String> teacherName = new ArrayList<>();
            ArrayList<String> teacherNIC = new ArrayList<>();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            for (int count = 0; count < model.getRowCount(); count++) {
                teacherIdn.add(model.getValueAt(count, 0).toString());
                teacherName.add(model.getValueAt(count, 1).toString());
                teacherNIC.add(model.getValueAt(count, 2).toString());
            }
            model.setRowCount(0);
            for (int count = 0; count < teacherIdn.size(); count++) {
                if (teacherIdx.equals(teacherIdn.get(count))) {
                    Object[] row = {teacherIdn.get(count), teacherName.get(count),
                        teacherNIC.get(count)};
                    model.addRow(row);
                }
            }
        }
    }//GEN-LAST:event_jTextField10ActionPerformed

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField11ActionPerformed
        if (!jTextField11.getText().equals("")) {
            loadTeachers();
            String teacherNamex = jTextField11.getText();
            ArrayList<String> teacherIdn = new ArrayList<>();
            ArrayList<String> teacherName = new ArrayList<>();
            ArrayList<String> teacherNIC = new ArrayList<>();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            for (int count = 0; count < model.getRowCount(); count++) {
                teacherIdn.add(model.getValueAt(count, 0).toString());
                teacherName.add(model.getValueAt(count, 1).toString());
                teacherNIC.add(model.getValueAt(count, 2).toString());
            }
            model.setRowCount(0);
            for (int count = 0; count < teacherIdn.size(); count++) {
                if (teacherNamex.equals(teacherName.get(count))) {
                    Object[] row = {teacherIdn.get(count), teacherName.get(count),
                        teacherNIC.get(count)};
                    model.addRow(row);
                }
            }
        }
    }//GEN-LAST:event_jTextField11ActionPerformed

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField12ActionPerformed
        if (!jTextField12.getText().equals("")) {
            loadTeachers();
            String teacherNICx = jTextField12.getText();
            ArrayList<String> teacherIdn = new ArrayList<>();
            ArrayList<String> teacherName = new ArrayList<>();
            ArrayList<String> teacherNIC = new ArrayList<>();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            for (int count = 0; count < model.getRowCount(); count++) {
                teacherIdn.add(model.getValueAt(count, 0).toString());
                teacherName.add(model.getValueAt(count, 1).toString());
                teacherNIC.add(model.getValueAt(count, 2).toString());
            }
            model.setRowCount(0);
            for (int count = 0; count < teacherIdn.size(); count++) {
                if (teacherNICx.equals(teacherNIC.get(count))) {
                    Object[] row = {teacherIdn.get(count), teacherName.get(count),
                        teacherNIC.get(count)};
                    model.addRow(row);
                }
            }
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

        DefaultTableModel model4 = (DefaultTableModel) jTable4.getModel();
        model4.setRowCount(0);
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
                    String subjectId = rs2.getString("subjectId");
                    Statement stmt3 = con.createStatement();
                    ResultSet rs3 = stmt3.executeQuery("SELECT * "
                            + "FROM subjects "
                            + "WHERE id='" + subjectId + "'");
                    while (rs3.next()) {
                        String grade = rs3.getString("grade");
                        String subject = rs3.getString("subject");
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
                            model4.addRow(row);
                        }
                    }
                }
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("#047" + ex);
        }

        DefaultTableModel model5 = (DefaultTableModel) jTable5.getModel();
        model5.setRowCount(0);
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM results "
                    + "WHERE studentId='" + id + "'");
            while (rs.next()) {
                String resultId = rs.getString("id");
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
                            model5.addRow(row);
                        }
                    }
                }
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("#048" + ex);
        }

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
                    Statement stmt3 = con.createStatement();
                    ResultSet rs3 = stmt3.executeQuery("SELECT * "
                            + "FROM subjects "
                            + "WHERE id='" + rs2.getString("subjectId") + "'");
                    while (rs3.next()) {
                        jComboBox6.addItem(rs3.getString("grade")
                                + " - " + rs3.getString("subject"));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("#049" + e);
        }

        jButton9.setEnabled(true);
        jButton10.setEnabled(true);
        jButton19.setEnabled(true);
        jButton43.setEnabled(true);
    }//GEN-LAST:event_jTable3MouseClicked

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        loadStudents();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // to send private message to selected student via telegram bot
        if (jTextArea1.getText().equals("")) {
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
            processBuilder.start();
        } catch (IOException e) {
            System.out.println("#035" + e);
        }
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        if (!jTextField9.getText().equals("")) {
            String studentIdx = jTextField12.getText();
            ArrayList<String> studentId = new ArrayList<>();
            ArrayList<String> studentName = new ArrayList<>();
            ArrayList<String> studentGrade = new ArrayList<>();
            DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
            for (int count = 0; count < model.getRowCount(); count++) {
                studentId.add(model.getValueAt(count, 0).toString());
                studentName.add(model.getValueAt(count, 1).toString());
                studentGrade.add(model.getValueAt(count, 2).toString());
            }
            model.setRowCount(0);
            for (int count = 0; count < studentId.size(); count++) {
                if (studentIdx.equals(studentId.get(count))) {
                    Object[] row = {studentId.get(count), studentName.get(count),
                        studentGrade.get(count)};
                    model.addRow(row);
                }
            }
        }
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void jTextField13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField13ActionPerformed
        if (!jTextField13.getText().equals("")) {
            String studentNamex = jTextField13.getText();
            ArrayList<String> studentId = new ArrayList<>();
            ArrayList<String> studentName = new ArrayList<>();
            ArrayList<String> studentGrade = new ArrayList<>();
            DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
            for (int count = 0; count < model.getRowCount(); count++) {
                studentId.add(model.getValueAt(count, 0).toString());
                studentName.add(model.getValueAt(count, 1).toString());
                studentGrade.add(model.getValueAt(count, 2).toString());
            }
            model.setRowCount(0);
            for (int count = 0; count < studentId.size(); count++) {
                if (studentNamex.equals(studentName.get(count))) {
                    Object[] row = {studentId.get(count), studentName.get(count),
                        studentGrade.get(count)};
                    model.addRow(row);
                }
            }
        }
    }//GEN-LAST:event_jTextField13ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        if (jComboBox4.getSelectedIndex() != 0 && jComboBox4.getSelectedItem() != null) {
            String studentGradex = jComboBox4.getSelectedItem().toString();
            ArrayList<String> studentId = new ArrayList<>();
            ArrayList<String> studentName = new ArrayList<>();
            ArrayList<String> studentGrade = new ArrayList<>();
            DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
            for (int count = 0; count < model.getRowCount(); count++) {
                studentId.add(model.getValueAt(count, 0).toString());
                studentName.add(model.getValueAt(count, 1).toString());
                studentGrade.add(model.getValueAt(count, 2).toString());
            }
            model.setRowCount(0);
            for (int count = 0; count < studentId.size(); count++) {
                if (studentGradex.equals(studentGrade.get(count))) {
                    Object[] row = {studentId.get(count), studentName.get(count),
                        studentGrade.get(count)};
                    model.addRow(row);
                }
            }
        }
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        loadStudents();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        // TODO add your handling code here:
        if (jTextArea3.getText().equals("")) {
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
                            jTextArea3.setText("");
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
        }
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        // TODO add your handling code here:
        try {
            ProcessBuilder processBuilder
                    = new ProcessBuilder("cmd.exe", "/c",
                            logPath + "groupsMessage.log");
            processBuilder.start();
        } catch (IOException e) {
            System.out.println("#040" + e);
        }
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:
        if (jTabbedPane1.getSelectedIndex() == 0) {
            grabData();
        }
        if (jTabbedPane1.getSelectedIndex() == 1) {
            loadTeachers();
        }
        if (jTabbedPane1.getSelectedIndex() == 2) {
            loadStudents();
        }
        if (jTabbedPane1.getSelectedIndex() == 3) {
            loadClasses();
        }
        if (jTabbedPane1.getSelectedIndex() == 4) {
            loadExams();
        }
        if (jTabbedPane1.getSelectedIndex() == 5) {
            loadSubjects();
        }
        if (jTabbedPane1.getSelectedIndex() == 6) {
            loadAccounts();
        }
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        TelegramVerify tVerify = new TelegramVerify();
        tVerify.setVisible(true);
        Faker faker = new Faker();
        TelegramVerify.jTextField1.setText(faker.number().digits(5));
        type.setText("supergroup");
        returnMethod.setText("groupTIdUpdate");
    }//GEN-LAST:event_jButton26ActionPerformed

    public void groupTIdUpdate() {
        int r = jTable6.getSelectedRow();
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE classes SET "
                    + "telegramId='" + telegramId.getText() + "' "
                    + "WHERE id='" + jTable6.getValueAt(r, 0).toString() + "'");
            loadClasses();
            JOptionPane.showMessageDialog(this, "Success!");
        } catch (HeadlessException | SQLException e) {
            System.out.println("#041" + e);
        }
    }

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        jComboBox2.setEnabled(true);
        loadTeachers();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        if (jComboBox2.getSelectedIndex() == 0 || jTextField25.getText().equals("")
                || jComboBox14.getSelectedIndex() == 0 || jTextField36.getText().equals("")
                || jTextField41.getText().equals("") || jTextField37.getText().equals("")
                || jTextField38.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!");
        } else {
            TelegramVerify tVerify = new TelegramVerify();
            tVerify.setVisible(true);
            Faker faker = new Faker();
            TelegramVerify.jTextField1.setText(faker.number().digits(5));
            type.setText("supergroup");
            returnMethod.setText("groupAdd");
        }
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
        String time = jTextField36.getText() + ":" + jTextField41.getText() + " " + jComboBox18.getSelectedItem().toString();
        String duration = jTextField37.getText() + "(h) " + jTextField38.getText() + "(min)";
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
                        + "'" + payment + "','" + day + "','" + time + "','" + duration + "',"
                        + "'" + telegramId.getText() + "')");
                loadTeachers();
                JOptionPane.showMessageDialog(this, "Success!");
            }
        } catch (SQLException e) {
            System.out.println("#044" + e);
        }
    }

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        jButton37.setEnabled(true);
        jButton15.setEnabled(true);
        int r = jTable2.getSelectedRow();
        String id = jTable2.getValueAt(r, 0).toString();
        String grade = jTable2.getValueAt(r, 1).toString();
        String subject = jTable2.getValueAt(r, 2).toString();
        jComboBox2.setSelectedItem(grade + " - " + subject);
        jComboBox2.setEnabled(false);
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM classes "
                    + "WHERE id='" + id + "'");
            while (rs.next()) {
                jTextField25.setText(rs.getString("payment"));
                jComboBox14.setSelectedIndex(rs.getInt("day"));
                String[] time = rs.getString("time").split(" ");
                String[] timex = time[0].split(":");
                jTextField36.setText(timex[0]);
                jTextField41.setText(timex[1]);
                if (time[1].equals("AM")) {
                    jComboBox18.setSelectedIndex(0);
                } else {
                    jComboBox18.setSelectedIndex(1);
                }
                String[] duration = rs.getString("duration").split(" ");
                String[] hours = duration[0].split("H");
                String[] minutes = duration[1].split("MIN");
                jTextField37.setText(hours[0]);
                jTextField38.setText(minutes[0]);
            }
        } catch (SQLException e) {
            System.out.println("#67" + e);
        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        int r = jTable1.getSelectedRow();
        String id = jTable1.getValueAt(r, 0).toString();
        int r1 = jTable2.getSelectedRow();
        String id1 = jTable2.getValueAt(r1, 0).toString();
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM classes "
                    + "WHERE subjectId='" + id1 + "' "
                    + "AND teacherId='" + id + "'");
            jComboBox2.setEnabled(true);
            loadTeachers();
            JOptionPane.showMessageDialog(this, "Success!");
        } catch (SQLException e) {
            System.out.println("#046" + e);
        }
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

    private void jTable5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MouseClicked
        // TODO add your handling code here:
        int r = jTable5.getSelectedRow();
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM results "
                    + "WHERE id='" + jTable5.getValueAt(r, 0).toString() + "'");
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
            String sByClass = jComboBox6.getSelectedItem().toString();
            ArrayList<String> id = new ArrayList<>();
            ArrayList<String> name = new ArrayList<>();
            ArrayList<String> classx = new ArrayList<>();
            ArrayList<String> date = new ArrayList<>();
            DefaultTableModel model = (DefaultTableModel) jTable5.getModel();
            for (int count = 0; count < model.getRowCount(); count++) {
                id.add(model.getValueAt(count, 0).toString());
                name.add(model.getValueAt(count, 1).toString());
                classx.add(model.getValueAt(count, 2).toString());
                date.add(model.getValueAt(count, 3).toString());
            }
            model.setRowCount(0);
            for (int count = 0; count < id.size(); count++) {
                if (sByClass.equals(id.get(count))) {
                    Object[] row = {id.get(count), name.get(count),
                        classx.get(count), date.get(count)};
                    model.addRow(row);
                }
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

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        // TODO add your handling code here:
        jComboBox8.setSelectedIndex(0);
        jComboBox10.setSelectedIndex(0);
        loadClasses();
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jTable6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable6MouseClicked
        jButton24.setEnabled(true);
        jButton26.setEnabled(true);

        int r = jTable6.getSelectedRow();
        String id = jTable6.getValueAt(r, 0).toString();
        DefaultTableModel model10 = (DefaultTableModel) jTable10.getModel();
        model10.setRowCount(0);
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM specialclasses "
                    + "WHERE classId='" + id + "'");
            while (rs.next()) {
                Object[] row = {rs.getString("id"), rs.getString("name"),
                    rs.getString("date"), rs.getString("time"), rs.getString("duration")};
                model10.addRow(row);
            }
        } catch (SQLException e) {
            System.out.println("#091" + e);
        }
    }//GEN-LAST:event_jTable6MouseClicked

    private void jComboBox8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox8ActionPerformed
        // TODO add your handling code here:
        if (jComboBox8.getSelectedIndex() != 0 && jComboBox8.getSelectedItem() != null) {
            String gradex = jComboBox8.getSelectedItem().toString();
            ArrayList<String> id = new ArrayList<>();
            ArrayList<String> grade = new ArrayList<>();
            ArrayList<String> subject = new ArrayList<>();
            ArrayList<String> teacher = new ArrayList<>();
            ArrayList<String> day = new ArrayList<>();
            DefaultTableModel model = (DefaultTableModel) jTable6.getModel();
            for (int count = 0; count < model.getRowCount(); count++) {
                id.add(model.getValueAt(count, 0).toString());
                grade.add(model.getValueAt(count, 1).toString());
                subject.add(model.getValueAt(count, 2).toString());
                teacher.add(model.getValueAt(count, 3).toString());
                day.add(model.getValueAt(count, 4).toString());
            }
            model.setRowCount(0);
            for (int count = 0; count < id.size(); count++) {
                if (gradex.equals(grade.get(count))) {
                    Object[] row = {id.get(count), grade.get(count),
                        subject.get(count), teacher.get(count), day.get(count)};
                    model.addRow(row);
                }
            }
        }
    }//GEN-LAST:event_jComboBox8ActionPerformed

    private void jComboBox9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox9ActionPerformed
        // TODO add your handling code here:
        if (jComboBox9.getSelectedIndex() != 0 && jComboBox9.getSelectedItem() != null) {
            String teacherx = jComboBox9.getSelectedItem().toString();
            ArrayList<String> id = new ArrayList<>();
            ArrayList<String> grade = new ArrayList<>();
            ArrayList<String> subject = new ArrayList<>();
            ArrayList<String> teacher = new ArrayList<>();
            ArrayList<String> day = new ArrayList<>();
            DefaultTableModel model = (DefaultTableModel) jTable6.getModel();
            for (int count = 0; count < model.getRowCount(); count++) {
                id.add(model.getValueAt(count, 0).toString());
                grade.add(model.getValueAt(count, 1).toString());
                subject.add(model.getValueAt(count, 2).toString());
                teacher.add(model.getValueAt(count, 3).toString());
                day.add(model.getValueAt(count, 4).toString());
            }
            model.setRowCount(0);
            for (int count = 0; count < id.size(); count++) {
                if (teacherx.equals(teacher.get(count))) {
                    Object[] row = {id.get(count), grade.get(count),
                        subject.get(count), teacher.get(count), day.get(count)};
                    model.addRow(row);
                }
            }
        }
    }//GEN-LAST:event_jComboBox9ActionPerformed

    private void jComboBox10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox10ActionPerformed
        // TODO add your handling code here:
        if (jComboBox10.getSelectedIndex() != 0 && jComboBox10.getSelectedItem() != null) {
            String dayx = jComboBox10.getSelectedItem().toString();
            ArrayList<String> id = new ArrayList<>();
            ArrayList<String> grade = new ArrayList<>();
            ArrayList<String> subject = new ArrayList<>();
            ArrayList<String> teacher = new ArrayList<>();
            ArrayList<String> day = new ArrayList<>();
            DefaultTableModel model = (DefaultTableModel) jTable6.getModel();
            for (int count = 0; count < model.getRowCount(); count++) {
                id.add(model.getValueAt(count, 0).toString());
                grade.add(model.getValueAt(count, 1).toString());
                subject.add(model.getValueAt(count, 2).toString());
                teacher.add(model.getValueAt(count, 3).toString());
                day.add(model.getValueAt(count, 4).toString());
            }
            model.setRowCount(0);
            for (int count = 0; count < id.size(); count++) {
                if (dayx.equals(day.get(count))) {
                    Object[] row = {id.get(count), grade.get(count),
                        subject.get(count), teacher.get(count), day.get(count)};
                    model.addRow(row);
                }
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

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        // TODO add your handling code here:
        if (jComboBox12.getSelectedIndex() == 0 || jTextField28.getText().equals("")
                || jTextField29.getPassword().equals("")) {
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
                loadAccounts();
                JOptionPane.showMessageDialog(this, "Success!");
            } catch (SQLException e) {
                System.out.println("#083" + e);
            }
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
                    loadAccounts();
                    JOptionPane.showMessageDialog(this, "Success!");
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
            loadAccounts();
            JOptionPane.showMessageDialog(this, "Success!");
        } catch (HeadlessException | SQLException e) {
            System.out.println("#058" + e);
        }
    }//GEN-LAST:event_jButton32ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        jButton12.setEnabled(true);
        cr1.setEnabled(true);
        cr1.setSelectedIndex(0);
        loadExams();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
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
        String examId = jTable8.getValueAt(r, 0).toString();
        Helper.AutomatedMessages msg = new Helper.AutomatedMessages();
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM exams "
                    + "WHERE id='" + examId + "'");
            while (rs.next()) {
                String classId = rs.getString("classId");
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT * "
                        + "FROM classes "
                        + "WHERE id='" + classId + "'");
                while (rs2.next()) {
                    Statement stmt3 = con.createStatement();
                    stmt3.executeUpdate("DELETE FROM exams "
                            + "WHERE id='" + examId + "'");
                    telegramId.setText(rs2.getString("telegramId"));
                    msg.examDelete();
                    Statement stmt4 = con.createStatement();
                    ResultSet rs4 = stmt4.executeQuery("SELECT * "
                            + "FROM regclass "
                            + "WHERE classId='" + classId + "'");
                    while (rs4.next()) {
                        Statement stmt5 = con.createStatement();
                        ResultSet rs5 = stmt5.executeQuery("SELECT * "
                                + "FROM students "
                                + "WHERE id='" + rs4.getString("studentId") + "'");
                        while (rs5.next()) {
                            telegramId.setText(rs5.getString("telegramId"));
                            msg.examDelete();
                        }
                    }
                }
            }
            con.close();
            loadExams();
            JOptionPane.showMessageDialog(this, "Success!");
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
            cr5.setEnabled(false);
            jTextField22.setText("");
            jTextField27.setText("");
            jTextField32.setText("");
            jTextField42.setText("");
            jTextField39.setText("");
            jTextField40.setText("");
            jCheckBox3.setSelected(false);
            jTextField22.setEnabled(false);
            jTextField27.setEnabled(false);
            jTextField32.setEnabled(false);
            jTextField42.setEnabled(false);
            jTextField39.setEnabled(false);
            jTextField40.setEnabled(false);
            jComboBox15.setEnabled(false);
            jCheckBox3.setEnabled(false);
            jButton12.setEnabled(false);
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
            cr5.setEnabled(false);
            jTextField22.setText("");
            jTextField27.setText("");
            jTextField32.setText("");
            jTextField42.setText("");
            jTextField39.setText("");
            jTextField40.setText("");
            jCheckBox3.setSelected(false);
            jTextField22.setEnabled(false);
            jTextField27.setEnabled(false);
            jTextField32.setEnabled(false);
            jTextField42.setEnabled(false);
            jTextField39.setEnabled(false);
            jTextField40.setEnabled(false);
            jComboBox15.setEnabled(false);
            jCheckBox3.setEnabled(false);
            jButton12.setEnabled(false);
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
            cr5.setEnabled(false);
            jTextField22.setText("");
            jTextField27.setText("");
            jTextField32.setText("");
            jTextField42.setText("");
            jTextField39.setText("");
            jTextField40.setText("");
            jCheckBox3.setSelected(false);
            jTextField22.setEnabled(false);
            jTextField27.setEnabled(false);
            jTextField32.setEnabled(false);
            jTextField42.setEnabled(false);
            jTextField39.setEnabled(false);
            jTextField40.setEnabled(false);
            jComboBox15.setEnabled(false);
            jCheckBox3.setEnabled(false);
            jButton12.setEnabled(false);
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
                        case 1 ->
                            convertDay = "Monday";
                        case 2 ->
                            convertDay = "Tuesday";
                        case 3 ->
                            convertDay = "Wednesday";
                        case 4 ->
                            convertDay = "Thursday";
                        case 5 ->
                            convertDay = "Friday";
                        case 6 ->
                            convertDay = "Saturday";
                        case 7 ->
                            convertDay = "Sunday";
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
            cr5.setEnabled(false);
            jTextField22.setText("");
            jTextField27.setText("");
            jTextField32.setText("");
            jTextField42.setText("");
            jTextField39.setText("");
            jTextField40.setText("");
            jCheckBox3.setSelected(false);
            jTextField22.setEnabled(false);
            jTextField27.setEnabled(false);
            jTextField32.setEnabled(false);
            jTextField42.setEnabled(false);
            jTextField39.setEnabled(false);
            jTextField40.setEnabled(false);
            jComboBox15.setEnabled(false);
            jCheckBox3.setEnabled(false);
            jButton12.setEnabled(false);
        } else {
            cr5.setEnabled(true);
            cr5.removeAllItems();
            cr5.addItem("Please Select...");
            cr5.setSelectedIndex(0);
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * "
                        + "FROM classes "
                        + "WHERE subjectId='" + subjectId + "' "
                        + "AND teacherId='" + teacherId + "' "
                        + "AND day='" + dayCho + "'");
                while (rs.next()) {
                    cr5.addItem(rs.getString("time"));
                }
            } catch (SQLException e) {
                System.out.println("#81" + e);
            }
        }
    }//GEN-LAST:event_cr4ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        if (jTextField22.getText().equals("") || jTextField27.getText().equals("")
                || jTextField32.getText().equals("") || jTextField39.getText().equals("")
                || jTextField40.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!");
        } else {
            String classId;
            String examTime = jTextField32.getText() + ":" + jTextField42.getText() + " "
                    + jComboBox15.getSelectedItem().toString();
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * "
                        + "FROM classes "
                        + "WHERE subjectId='" + subjectId + "' "
                        + "AND teacherId='" + teacherId + "'"
                        + "AND day='" + dayCho + "' "
                        + "AND time='" + cr5.getSelectedItem().toString() + "'");
                while (rs.next()) {
                    classId = rs.getString("id");
                    Statement stmt2 = con.createStatement();
                    stmt2.executeUpdate("INSERT INTO exams "
                            + "(name,classId,date,time,duration) "
                            + "VALUES "
                            + "('" + jTextField22.getText() + "',"
                            + "'" + classId + "',"
                            + "'" + jTextField27.getText() + "',"
                            + "'" + examTime + "',"
                            + "'" + jTextField39.getText() + "(h) " + jTextField40.getText() + "(min)" + "')");
                    telegramId.setText(rs.getString("telegramId"));
                    tAutomated.newExamAdded();
                    examIdGrab();
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
                            Statement stmt6 = con.createStatement();
                            stmt6.executeUpdate("INSERT INTO results "
                                    + "(examId,studentId,marks) "
                                    + "VALUES "
                                    + "('" + examId + "','" + studentId + "','N/A')");
                            tAutomated.newExamAdded();
                        }
                    }
                }
                loadExams();
                JOptionPane.showMessageDialog(this, "Success!");
            } catch (SQLException e) {
                System.out.println("#063" + e);
            }
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void examIdGrab() {
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM exams "
                    + "ORDER BY id DESC LIMIT 1");
            while (rs.next()) {
                examId = String.valueOf(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println("#064" + e);
        }
    }

    private void jTable8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable8MouseClicked
        cr1.setEnabled(false);
        cr2.setEnabled(false);
        cr3.setEnabled(false);
        cr4.setEnabled(false);
        cr5.setEnabled(false);

        int r = jTable8.getSelectedRow();
        String id = jTable8.getValueAt(r, 0).toString();

        String classx = jTable8.getValueAt(r, 2).toString();
        String[] classSplit = classx.split(" - ");
        cr1.removeAllItems();
        cr1.addItem(classSplit[0]);
        cr1.setSelectedIndex(0);
        cr2.removeAllItems();
        cr2.addItem(classSplit[1]);
        cr2.setSelectedIndex(0);
        cr3.removeAllItems();
        cr3.addItem(jTable8.getValueAt(r, 3).toString());
        cr3.setSelectedIndex(0);

        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM exams "
                    + "WHERE id='" + id + "'");
            while (rs.next()) {
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT * "
                        + "FROM classes "
                        + "WHERE id='" + rs.getString("classId") + "'");
                while (rs2.next()) {
                    cr5.removeAllItems();
                    cr5.addItem(rs2.getString("time"));
                    cr5.setSelectedIndex(0);
                    cr4.removeAllItems();
                    switch (rs.getInt("day")) {
                        case 1 -> {
                            cr4.addItem("Monday");
                            cr4.setSelectedItem("Monday");
                        }
                        case 2 -> {
                            cr4.addItem("Tuesday");
                            cr4.setSelectedItem("Tuesday");
                        }
                        case 3 -> {
                            cr4.addItem("Wednesday");
                            cr4.setSelectedItem("Wednesday");
                        }
                        case 4 -> {
                            cr4.addItem("Thursday");
                            cr4.setSelectedItem("Thursday");
                        }
                        case 5 -> {
                            cr4.addItem("Friday");
                            cr4.setSelectedItem("Friday");
                        }
                        case 6 -> {
                            cr4.addItem("Saturday");
                            cr4.setSelectedItem("Saturday");
                        }
                        case 7 -> {
                            cr4.addItem("Sunday");
                            cr4.setSelectedItem("Sunday");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("#092" + e);
        }

        jTextField22.setText(jTable8.getValueAt(r, 1).toString());
        jTextField27.setText(jTable8.getValueAt(r, 4).toString());
        String[] examTime = jTable8.getValueAt(r, 5).toString().split(" ");
        String[] examTimeRe = examTime[0].split(":");
        jTextField32.setText(examTimeRe[0]);
        jTextField42.setText(examTimeRe[1]);
        jComboBox15.setSelectedItem(examTime[1]);
        String[] examDuration = jTable8.getValueAt(r, 6).toString().split("(h) ");
        jTextField39.setText(examDuration[0]);
        String[] examDurationRe = examDuration[1].split("(min)");
        jTextField40.setText(examDurationRe[0]);

        jButton12.setEnabled(false);
        jButton42.setEnabled(true);

        jButton18.setEnabled(true);
        jButton33.setEnabled(true);
    }//GEN-LAST:event_jTable8MouseClicked

    private void jTextField19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField19ActionPerformed
        // TODO add your handling code here:
        if (!jTextField19.getText().equals("")) {
            String namex = jTextField19.getText();
            ArrayList<String> id = new ArrayList<>();
            ArrayList<String> name = new ArrayList<>();
            ArrayList<String> classn = new ArrayList<>();
            ArrayList<String> date = new ArrayList<>();
            ArrayList<String> time = new ArrayList<>();
            DefaultTableModel model = (DefaultTableModel) jTable8.getModel();
            for (int count = 0; count < model.getRowCount(); count++) {
                id.add(model.getValueAt(count, 0).toString());
                name.add(model.getValueAt(count, 1).toString());
                classn.add(model.getValueAt(count, 2).toString());
                date.add(model.getValueAt(count, 3).toString());
                time.add(model.getValueAt(count, 4).toString());
            }
            model.setRowCount(0);
            for (int count = 0; count < id.size(); count++) {
                if (namex.equals(name.get(count))) {
                    Object[] row = {id.get(count), name.get(count),
                        classn.get(count), date.get(count), time.get(count)};
                    model.addRow(row);
                }
            }
        }
    }//GEN-LAST:event_jTextField19ActionPerformed

    private void jTextField20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField20ActionPerformed
        // TODO add your handling code here:
        if (!jTextField20.getText().equals("")) {
            String datex = jTextField20.getText();
            ArrayList<String> id = new ArrayList<>();
            ArrayList<String> name = new ArrayList<>();
            ArrayList<String> classn = new ArrayList<>();
            ArrayList<String> date = new ArrayList<>();
            ArrayList<String> time = new ArrayList<>();
            DefaultTableModel model = (DefaultTableModel) jTable8.getModel();
            for (int count = 0; count < model.getRowCount(); count++) {
                id.add(model.getValueAt(count, 0).toString());
                name.add(model.getValueAt(count, 1).toString());
                classn.add(model.getValueAt(count, 2).toString());
                date.add(model.getValueAt(count, 3).toString());
                time.add(model.getValueAt(count, 4).toString());
            }
            model.setRowCount(0);
            for (int count = 0; count < id.size(); count++) {
                if (datex.equals(date.get(count))) {
                    Object[] row = {id.get(count), name.get(count),
                        classn.get(count), date.get(count), time.get(count)};
                    model.addRow(row);
                }
            }
        }
    }//GEN-LAST:event_jTextField20ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
        if (jComboBox3.getSelectedIndex() != 0 && jComboBox3.getSelectedItem() != null) {
            String classx = jComboBox3.getSelectedItem().toString();
            ArrayList<String> id = new ArrayList<>();
            ArrayList<String> name = new ArrayList<>();
            ArrayList<String> classn = new ArrayList<>();
            ArrayList<String> date = new ArrayList<>();
            ArrayList<String> time = new ArrayList<>();
            DefaultTableModel model = (DefaultTableModel) jTable8.getModel();
            for (int count = 0; count < model.getRowCount(); count++) {
                id.add(model.getValueAt(count, 0).toString());
                name.add(model.getValueAt(count, 1).toString());
                classn.add(model.getValueAt(count, 2).toString());
                date.add(model.getValueAt(count, 3).toString());
                time.add(model.getValueAt(count, 4).toString());
            }
            model.setRowCount(0);
            for (int count = 0; count < id.size(); count++) {
                if (classx.equals(classn.get(count))) {
                    Object[] row = {id.get(count), name.get(count),
                        classn.get(count), date.get(count), time.get(count)};
                    model.addRow(row);
                }
            }
        }
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
        // TODO add your handling code here:
        loadSubjects();
    }//GEN-LAST:event_jButton36ActionPerformed

    private void jTextField34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField34ActionPerformed
        // TODO add your handling code here:
        if (!jTextField34.getText().equals("")) {
            String subjectIdx = jTextField34.getText();
            ArrayList<String> subjectId = new ArrayList<>();
            ArrayList<String> grade = new ArrayList<>();
            ArrayList<String> name = new ArrayList<>();
            DefaultTableModel model = (DefaultTableModel) jTable9.getModel();
            for (int count = 0; count < model.getRowCount(); count++) {
                subjectId.add(model.getValueAt(count, 0).toString());
                grade.add(model.getValueAt(count, 1).toString());
                name.add(model.getValueAt(count, 2).toString());
            }
            model.setRowCount(0);
            for (int count = 0; count < subjectId.size(); count++) {
                if (subjectIdx.equals(subjectId.get(count))) {
                    Object[] row = {subjectId.get(count), grade.get(count),
                        name.get(count)};
                    model.addRow(row);
                }
            }
        }
    }//GEN-LAST:event_jTextField34ActionPerformed

    private void jTextField35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField35ActionPerformed
        // TODO add your handling code here:
        if (!jTextField35.getText().equals("")) {
            String subjectNamex = jTextField35.getText();
            ArrayList<String> subjectId = new ArrayList<>();
            ArrayList<String> grade = new ArrayList<>();
            ArrayList<String> name = new ArrayList<>();
            DefaultTableModel model = (DefaultTableModel) jTable9.getModel();
            for (int count = 0; count < model.getRowCount(); count++) {
                subjectId.add(model.getValueAt(count, 0).toString());
                grade.add(model.getValueAt(count, 1).toString());
                name.add(model.getValueAt(count, 2).toString());
            }
            model.setRowCount(0);
            for (int count = 0; count < subjectId.size(); count++) {
                if (subjectNamex.equals(name.get(count))) {
                    Object[] row = {subjectId.get(count), grade.get(count),
                        name.get(count)};
                    model.addRow(row);
                }
            }
        }
    }//GEN-LAST:event_jTextField35ActionPerformed

    private void jComboBox17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox17ActionPerformed
        // TODO add your handling code here:
        if (jComboBox17.getSelectedIndex() != 0 && jComboBox17.getSelectedItem() != null) {
            String gradex = jComboBox17.getSelectedItem().toString();
            ArrayList<String> subjectId = new ArrayList<>();
            ArrayList<String> grade = new ArrayList<>();
            ArrayList<String> name = new ArrayList<>();
            DefaultTableModel model = (DefaultTableModel) jTable9.getModel();
            for (int count = 0; count < model.getRowCount(); count++) {
                subjectId.add(model.getValueAt(count, 0).toString());
                grade.add(model.getValueAt(count, 1).toString());
                name.add(model.getValueAt(count, 2).toString());
            }
            model.setRowCount(0);
            for (int count = 0; count < subjectId.size(); count++) {
                if (gradex.equals(grade.get(count))) {
                    Object[] row = {subjectId.get(count), grade.get(count),
                        name.get(count)};
                    model.addRow(row);
                }
            }
        }
    }//GEN-LAST:event_jComboBox17ActionPerformed

    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        // TODO add your handling code here:
        if (jComboBox16.getSelectedIndex() == 0 || jTextField33.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!");
        } else {
            String grade = jComboBox16.getSelectedItem().toString();
            String name = jTextField33.getText();
            int x = 0;
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * "
                        + "FROM subjects");
                while (rs.next()) {
                    String gradex = rs.getString("grade");
                    String subjectx = rs.getString("subject");
                    if (gradex.equalsIgnoreCase(grade) && subjectx.equalsIgnoreCase(name)) {
                        JOptionPane.showMessageDialog(this, "Subject already registered!");
                    } else {
                        x = 1;
                    }
                }

            } catch (HeadlessException | SQLException e) {
                System.out.println("#065" + e);
            }
            if (x == 1) {
                try {
                    Connection con = Helper.DB.connect();
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate("INSERT INTO subjects "
                            + "(grade,subject) "
                            + "VALUES "
                            + "('" + grade + "','" + name + "')");
                    loadSubjects();
                    JOptionPane.showMessageDialog(this, "Success!");
                } catch (HeadlessException | SQLException e) {
                    System.out.println("#090" + e);
                }
            }
        }
    }//GEN-LAST:event_jButton34ActionPerformed

    private void jTable9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable9MouseClicked
        // TODO add your handling code here:
        jButton35.setEnabled(true);
    }//GEN-LAST:event_jTable9MouseClicked

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
        // TODO add your handling code here:
        int r = jTable9.getSelectedRow();
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM subjects "
                    + "WHERE id='" + jTable9.getValueAt(r, 0).toString() + "'");
            loadSubjects();
            JOptionPane.showMessageDialog(this, "Success!");
        } catch (HeadlessException | SQLException e) {
            System.out.println("#66" + e);
        }
    }//GEN-LAST:event_jButton35ActionPerformed

    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
        if (jTextField25.getText().equals("")
                || jComboBox14.getSelectedIndex() == 0 || jTextField36.getText().equals("")
                || jTextField41.getText().equals("") || jTextField37.getText().equals("")
                || jTextField38.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!");
        } else {
            int r = jTable2.getSelectedRow();
            String id = jTable2.getValueAt(r, 0).toString();
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                stmt.executeUpdate("UPDATE classes "
                        + "(payment,day,time,duration) "
                        + "VALUES "
                        + "('" + jTextField25.getText() + "','" + jComboBox14.getSelectedIndex() + "',"
                        + "'" + jTextField36.getText() + ":" + jTextField41.getText() + " "
                        + jComboBox18.getSelectedItem().toString() + "',"
                        + "'" + jTextField37.getText() + "(h) " + jTextField38.getText() + "(min)" + "'");
            } catch (SQLException e) {
                System.out.println("#068" + e);
            }
            Helper.AutomatedMessages bot = new Helper.AutomatedMessages();
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * "
                        + "FROM classes "
                        + "WHERE id='" + id + "'");
                while (rs.next()) {
                    String classn = jTable2.getValueAt(r, 1).toString() + " - " + jTable2.getValueAt(r, 2).toString();
                    String teacher = rs.getString("name");
                    String day = null;
                    int dayn = rs.getInt("day");
                    switch (dayn) {
                        case 1 ->
                            day = "Monday";
                        case 2 ->
                            day = "Tuesday";
                        case 3 ->
                            day = "Wednesday";
                        case 4 ->
                            day = "Thursday";
                        case 5 ->
                            day = "Friday";
                        case 6 ->
                            day = "Saturday";
                        case 7 ->
                            day = "Sunday";
                    }
                    String payment = rs.getString("payment");
                    String time = rs.getString("time");
                    String duration = rs.getString("duration");
                    longDetails.setText(classn + "@" + teacher + "@" + day + "@" + payment + "@" + time + "@" + duration);
                    telegramId.setText(rs.getString("telegramId"));
                    bot.classDetailsUpdated();
                    Statement stmt2 = con.createStatement();
                    ResultSet rs2 = stmt2.executeQuery("SELECT * "
                            + "FROM regclass "
                            + "WHERE classId='" + id + "'");
                    while (rs2.next()) {
                        Statement stmt3 = con.createStatement();
                        ResultSet rs3 = stmt3.executeQuery("SELECT * "
                                + "FROM students "
                                + "WHERE id='" + rs2.getString("studentId") + "'");
                        while (rs3.next()) {
                            telegramId.setText(rs3.getString("telegramId"));
                            bot.classDetailsUpdated();
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("#069" + e);
            }
            jComboBox2.setEnabled(true);
            loadTeachers();
            JOptionPane.showMessageDialog(this, "Success!");
        }
    }//GEN-LAST:event_jButton37ActionPerformed

    private void cr5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cr5ActionPerformed
        if (cr5.getSelectedIndex() == 0 || cr5.getSelectedItem() == null) {
            jTextField22.setText("");
            jTextField27.setText("");
            jTextField32.setText("");
            jTextField42.setText("");
            jTextField39.setText("");
            jTextField40.setText("");
            jCheckBox3.setSelected(false);
            jTextField22.setEnabled(false);
            jTextField27.setEnabled(false);
            jTextField32.setEnabled(false);
            jTextField42.setEnabled(false);
            jTextField39.setEnabled(false);
            jTextField40.setEnabled(false);
            jComboBox15.setEnabled(false);
            jCheckBox3.setEnabled(false);
            jButton12.setEnabled(false);
        } else {
            jTextField22.setEnabled(true);
            jTextField27.setEnabled(true);
            jTextField32.setEnabled(true);
            jTextField42.setEnabled(true);
            jTextField39.setEnabled(true);
            jTextField40.setEnabled(true);
            jComboBox15.setEnabled(true);
            jCheckBox3.setEnabled(true);
            jButton12.setEnabled(true);
        }
    }//GEN-LAST:event_cr5ActionPerformed

    private void successPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_successPropertyChange
        // TODO add your handling code here:
        String redirect = success.getText();
        switch (redirect) {
            case "teacherAdd" ->
                teacherAdd();
            case "teacherUpdate" ->
                teacherUpdate();
            case "groupAdd" ->
                groupAdd();
            case "studentUpdate" ->
                studentUpdate();
            case "groupTIdUpdate" ->
                groupTIdUpdate();
        }
    }//GEN-LAST:event_successPropertyChange

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        // TODO add your handling code here:
        if (jTextField43.getText().equals("") || jTextField44.getText().equals("")
                || jTextField45.getText().equals("") || jTextField48.getText().equals("")
                || jTextField46.getText().equals("") || jTextField47.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!");
        } else {
            int r = jTable6.getSelectedRow();
            String id = jTable6.getValueAt(r, 0).toString();
            Helper.AutomatedMessages bot = new Helper.AutomatedMessages();
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                stmt.executeUpdate("INSERT INTO specialclasses "
                        + "(classId,name,date,time,duration) "
                        + "VALUES "
                        + "('" + id + "'," + jTextField43.getText() + "','" + jTextField44.getText() + "',"
                        + "'" + jTextField45.getText() + ":" + jTextField48.getText() + " " + jComboBox19.getSelectedItem().toString() + "',"
                        + "'" + jTextField46.getText() + "(h) " + jTextField47.getText() + "(min)" + "'");
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT * "
                        + "FROM classes "
                        + "WHERE id='" + id + "'");
                while (rs2.next()) {
                    telegramId.setText(rs2.getString("telegramId"));
                    bot.specialClassAdd();
                    Statement stmt3 = con.createStatement();
                    ResultSet rs3 = stmt3.executeQuery("SELECT * "
                            + "FROM regclass "
                            + "WHERE classId='" + id + "'");
                    while (rs3.next()) {
                        Statement stmt4 = con.createStatement();
                        ResultSet rs4 = stmt4.executeQuery("SELECT * "
                                + "FROM students "
                                + "WHERE id='" + rs3.getString("studentId") + "'");
                        while (rs4.next()) {
                            telegramId.setText(rs4.getString("telegramId"));
                            bot.specialClassAdd();
                        }
                    }
                }
                con.close();
                loadClasses();
                JOptionPane.showMessageDialog(this, "Success!");
            } catch (SQLException e) {
                System.out.println("#094" + e);
            }
        }
    }//GEN-LAST:event_jButton38ActionPerformed

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed
        // TODO add your handling code here:
        int r = jTable10.getSelectedRow();
        String id = jTable10.getValueAt(r, 0).toString();
        Helper.AutomatedMessages bot = new Helper.AutomatedMessages();
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM specialclasses "
                    + "WHERE id='" + id + "'");
            con.close();
            bot.specialClassDelete();
            loadClasses();
            JOptionPane.showMessageDialog(this, "Success!");
        } catch (Exception e) {
            System.out.println("#097" + e);
        }
    }//GEN-LAST:event_jButton39ActionPerformed

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
        // TODO add your handling code here:
        loadClasses();
    }//GEN-LAST:event_jButton40ActionPerformed

    private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton41ActionPerformed
        // TODO add your handling code here:
        if (jTextField43.getText().equals("") || jTextField44.getText().equals("")
                || jTextField45.getText().equals("") || jTextField48.getText().equals("")
                || jTextField46.getText().equals("") || jTextField47.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!");
        } else {
            int r = jTable6.getSelectedRow();
            String id = jTable6.getValueAt(r, 0).toString();
            int sr = jTable10.getSelectedRow();
            String sid = jTable10.getValueAt(r, 0).toString();
            Helper.AutomatedMessages bot = new Helper.AutomatedMessages();
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                stmt.executeUpdate("UPDATE specialclasses SET "
                        + "name='" + jTextField43.getText() + "',"
                        + "date='" + jTextField44.getText() + "',"
                        + "time='" + jTextField45.getText() + ":" + jTextField48.getText() + " "
                        + jComboBox19.getSelectedItem().toString() + "',"
                        + "duration='" + jTextField46.getText() + "(h) " + jTextField47.getText() + "(min)" + "' "
                        + "WHERE id='" + sid + "'");
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT * "
                        + "FROM classes "
                        + "WHERE id='" + id + "'");
                while (rs2.next()) {
                    telegramId.setText(rs2.getString("telegramId"));
                    bot.specialClassUpdate();
                    Statement stmt3 = con.createStatement();
                    ResultSet rs3 = stmt3.executeQuery("SELECT * "
                            + "FROM regclass "
                            + "WHERE classId='" + id + "'");
                    while (rs3.next()) {
                        Statement stmt4 = con.createStatement();
                        ResultSet rs4 = stmt4.executeQuery("SELECT * "
                                + "FROM students "
                                + "WHERE id='" + rs3.getString("studentId") + "'");
                        while (rs4.next()) {
                            telegramId.setText(rs4.getString("telegramId"));
                            bot.specialClassUpdate();
                        }
                    }
                }
                con.close();
                loadClasses();
                JOptionPane.showMessageDialog(this, "Success!");
            } catch (SQLException e) {
                System.out.println("#095" + e);
            }
        }
    }//GEN-LAST:event_jButton41ActionPerformed

    private void jButton42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton42ActionPerformed
        // TODO add your handling code here:
        int r = jTable8.getSelectedRow();
        String id = jTable8.getValueAt(r, 0).toString();
        Helper.AutomatedMessages bot = new Helper.AutomatedMessages();
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE exams SET "
                    + "name='" + jTextField22.getText() + "',"
                    + "date='" + jTextField27.getText() + "',"
                    + "time='" + jTextField32.getText() + ":" + jTextField42.getText() + " "
                    + jComboBox15.getSelectedItem().toString() + "',"
                    + "duration='" + jTextField39.getText() + "(h) " + jTextField40.getText() + "(min)" + "' "
                    + "WHERE id='" + id + "'");
            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery("SELECT * "
                    + "FROM exams "
                    + "WHERE id='" + id + "'");
            while (rs2.next()) {
                String classId = rs2.getString("classId");
                Statement stmt3 = con.createStatement();
                ResultSet rs3 = stmt3.executeQuery("SELECT * "
                        + "FROM classes "
                        + "WHERE id='" + classId + "'");
                while (rs3.next()) {
                    telegramId.setText(rs3.getString("telegramId"));
                    bot.examUpdate();
                    Statement stmt4 = con.createStatement();
                    ResultSet rs4 = stmt4.executeQuery("SELECT * "
                            + "FROM regclass "
                            + "WHERE classId='" + classId + "'");
                    while (rs4.next()) {
                        Statement stmt5 = con.createStatement();
                        ResultSet rs5 = stmt5.executeQuery("SELECT * "
                                + "FROM students "
                                + "WHERE id='" + rs4.getString("studentId") + "'");
                        while (rs5.next()) {
                            telegramId.setText(rs5.getString("telegramId"));
                            bot.examUpdate();
                        }
                    }
                }
            }
            con.close();
            loadExams();
            JOptionPane.showMessageDialog(this, "Success!");
        } catch (HeadlessException | SQLException e) {
            System.out.println("#092" + e);
        }
    }//GEN-LAST:event_jButton42ActionPerformed

    private void jTable10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable10MouseClicked
        // TODO add your handling code here:
        int r = jTable10.getSelectedRow();
        jTextField43.setText(jTable10.getValueAt(r, 1).toString());
        jTextField44.setText(jTable10.getValueAt(r, 2).toString());
        String[] time = jTable10.getValueAt(r, 3).toString().split(" ");
        String[] timex = time[0].split(":");
        jTextField45.setText(timex[0]);
        jTextField48.setText(timex[1]);
        jComboBox19.setSelectedItem(time[1]);
        String[] duration = jTable10.getValueAt(r, 4).toString().split("(h) ");
        jTextField46.setText(duration[0]);
        String[] durationx = duration[1].split("(min)");
        jTextField47.setText(durationx[0]);
    }//GEN-LAST:event_jTable10MouseClicked

    private void jButton43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton43ActionPerformed
        // TODO add your handling code here:
        int r = jTable3.getSelectedRow();
        String studentId = jTable3.getValueAt(r, 0).toString();
        String defFileLocation = null;
        try ( Stream<String> lines = Files.lines(
                Paths.get("C:\\ProgramData\\LycorisCafe\\IMS\\files.lc"))) {
            defFileLocation = lines.skip(0).findFirst().get();
        } catch (IOException ex) {
            System.out.println("#097" + ex);
        }
        File source = new File("C:\\ProgramData\\LycorisCafe\\IMS\\StudentCards\\" + studentId + ".pdf");
        File to = new File(defFileLocation);
        try {
            FileUtils.copyFile(source, to);
            JOptionPane.showMessageDialog(this, "Success!\nLocation : " + defFileLocation);
        } catch (Exception e) {
            System.out.println("#098" + e);
        }
    }//GEN-LAST:event_jButton43ActionPerformed

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
        // TODO add your handling code here:
        if (jCheckBox3.isSelected()){
            String[] time = cr5.getSelectedItem().toString().split(" ");
            String[] timex = time[1].split(":");
            jTextField32.setText(timex[0]);
            jTextField42.setText(timex[1]);
            jComboBox15.setSelectedItem(time[1]);
            jTextField32.setEnabled(false);
            jTextField42.setEnabled(false);
            jComboBox15.setEnabled(false);
        } else {
            jTextField32.setEnabled(true);
            jTextField42.setEnabled(true);
            jComboBox15.setEnabled(true);
        }
    }//GEN-LAST:event_jCheckBox3ActionPerformed

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
    public static javax.swing.JTextArea broadcastMessage;
    private javax.swing.ButtonGroup buttonGroup1;
    public static javax.swing.JComboBox<String> cr1;
    public static javax.swing.JComboBox<String> cr2;
    public static javax.swing.JComboBox<String> cr3;
    public static javax.swing.JComboBox<String> cr4;
    private javax.swing.JComboBox<String> cr5;
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
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton43;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox10;
    public static javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JComboBox<String> jComboBox12;
    private javax.swing.JComboBox<String> jComboBox13;
    private javax.swing.JComboBox<String> jComboBox14;
    public static javax.swing.JComboBox<String> jComboBox15;
    private javax.swing.JComboBox<String> jComboBox16;
    private javax.swing.JComboBox<String> jComboBox17;
    private javax.swing.JComboBox<String> jComboBox18;
    public static javax.swing.JComboBox<String> jComboBox19;
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
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
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
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
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
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
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
    private javax.swing.JTable jTable10;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    public static javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    public static javax.swing.JTable jTable8;
    private javax.swing.JTable jTable9;
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
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField34;
    private javax.swing.JTextField jTextField35;
    private javax.swing.JTextField jTextField36;
    private javax.swing.JTextField jTextField37;
    private javax.swing.JTextField jTextField38;
    public static javax.swing.JTextField jTextField39;
    private javax.swing.JTextField jTextField4;
    public static javax.swing.JTextField jTextField40;
    private javax.swing.JTextField jTextField41;
    public static javax.swing.JTextField jTextField42;
    public static javax.swing.JTextField jTextField43;
    public static javax.swing.JTextField jTextField44;
    public static javax.swing.JTextField jTextField45;
    public static javax.swing.JTextField jTextField46;
    public static javax.swing.JTextField jTextField47;
    public static javax.swing.JTextField jTextField48;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    public static javax.swing.JLabel longDetails;
    public static javax.swing.JLabel returnMethod;
    public static javax.swing.JLabel success;
    public static javax.swing.JLabel telegramId;
    public static javax.swing.JLabel type;
    // End of variables declaration//GEN-END:variables
}
