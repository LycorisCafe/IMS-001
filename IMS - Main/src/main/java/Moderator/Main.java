/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Moderator;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamException;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lycoris Cafe
 */
public class Main extends javax.swing.JFrame implements Runnable, ThreadFactory {

    private WebcamPanel panel = null;
    private Webcam webcam = null;
    private Executor executor = Executors.newSingleThreadExecutor(this);
    int x = 0;
    String qrResult;
    String classId;
    // https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html
    String today = new SimpleDateFormat("u").format(new Date());
    String todate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    Helper.AutomatedMessages tMessage = new Helper.AutomatedMessages();

    /**
     * Creates new form Operations
     */
    public Main() {
        initComponents();
        formDetails();
        initWebCam();
        disablePanels();
        dataGrab();
    }

    private void dataGrab() {
        try {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM attendance "
                    + "WHERE date='" + todate + "'");
            while (rs.next()) {
                ResultSet rs2 = stmt.executeQuery("SELECT * "
                        + "FROM regclass "
                        + "WHERE id='" + rs.getString("regClassId") + "'");
                while (rs2.next()) {
                    String studentId = rs2.getString("studentId");
                    ResultSet rs3 = stmt.executeQuery("SELECT * "
                            + "FROM classes "
                            + "WHERE id='" + rs2.getString("classId") + "'");
                    while (rs3.next()) {
                        ResultSet rs4 = stmt.executeQuery("SELECT * "
                                + "FROM subjects "
                                + "WHERE id='" + rs3.getString("subjectId") + "'");
                        while (rs4.next()) {
                            String grade = rs4.getString("grade");
                            String subject = rs4.getString("subject");
                            ResultSet rs5 = stmt.executeQuery("SELECT * "
                                    + "FROM students "
                                    + "WHERE id='" + studentId + "'");
                            while (rs5.next()) {
                                Object[] row = {studentId, rs5.getString("firstName")
                                    + " " + rs5.getString("lastName"),
                                    grade + " - " + subject, rs5.getString("guardianPhone")};
                                model.addRow(row);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            jComboBox1.removeAllItems();
            jComboBox1.addItem("Please Select...");
            jComboBox1.setSelectedIndex(0);
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM classes "
                    + "WHERE day='" + today + "'");
            while (rs.next()) {
                ResultSet rs2 = stmt.executeQuery("SELECT * "
                        + "FROM subjects "
                        + "WHERE id='" + rs.getString("subjectId") + "'");
                while (rs2.next()) {
                    jComboBox1.addItem(rs2.getString("grade") + " - "
                            + rs2.getString("subject"));
                }
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void formDetails() {
        Helper.MainDetails details = new Helper.MainDetails();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(details.iconPath())));
        setExtendedState(this.MAXIMIZED_BOTH);
    }

    private void disablePanels() {
        Component[] com1 = jPanel6.getComponents();
        for (int a = 0; a < com1.length; a++) {
            com1[a].setEnabled(false);
        }
        jLabel6.setIcon(null);
        jLabel6.setText("-");
        studentIdLabel.setText("---");
        jTextField3.setText("---");
        jTextField4.setText("---");
        jTextField6.setText("---");
        jTextField6.setForeground(jTextField4.getForeground());
        jTextField5.setText("---");
        jTextField5.setForeground(jTextField4.getForeground());
        jComboBox3.removeAllItems();
        jComboBox3.addItem("Please Select...");
        jComboBox3.setSelectedIndex(0);
        jButton2.setEnabled(false);
        jButton6.setEnabled(false);
    }

    private void webcamClose() {
        try {
            webcam.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void initWebCam() {
        try {
            Dimension size = WebcamResolution.QVGA.getSize();
            webcam = Webcam.getWebcams().get(0);
            webcam.setViewSize(size);

            panel = new WebcamPanel(webcam);
            panel.setPreferredSize(size);
            panel.setFPSDisplayed(true);

            jPanel2.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 250));

            executor.execute(this);
        } catch (WebcamException e) {
            System.out.println(e);
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

        tId = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        studentIdLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        tId.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Institute Name");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Attendance Marking :"));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setToolTipText("QR Code Preview");
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setToolTipText("Student Image");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("-");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        studentIdLabel.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        studentIdLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        studentIdLabel.setText("---");
        studentIdLabel.setToolTipText("Student ID");
        studentIdLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Name :");

        jLabel5.setText("Grade :");

        jLabel10.setText("Status :");

        jTextField3.setEditable(false);
        jTextField3.setText("---");
        jTextField3.setFocusable(false);

        jTextField4.setEditable(false);
        jTextField4.setText("---");
        jTextField4.setFocusable(false);

        jTextField6.setEditable(false);
        jTextField6.setText("---");

        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel7.setText("Select Class :");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select..." }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jLabel4.setText("Payments :");

        jTextField5.setEditable(false);
        jTextField5.setText("---");
        jTextField5.setFocusable(false);

        jButton1.setText("Mark Attendance");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox3, 0, 284, Short.MAX_VALUE)
                            .addComponent(jTextField5))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton7.setText("Reset");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton6.setText("Edit Classes");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton2.setText("Payments");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                            .addComponent(jTextField4)
                            .addComponent(jTextField6)))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Student ID");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(studentIdLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(studentIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Students Count (Today) :"));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student ID", "Name", "Class", "Guardian Contact"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(150);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(150);
            jTable1.getColumnModel().getColumn(3).setMinWidth(150);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(150);
        }

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Filter :"));

        jLabel1.setText("by Student ID :");

        jLabel3.setText("by Name :");

        jLabel8.setText("by Class :");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select..." }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton3.setText("Reset");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                        .addGap(1, 1, 1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(7, 7, 7))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jButton4.setText("Logout");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton5.setText("Register New Student");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/InstituteDesign.png"))); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
                .addGap(42, 42, 42)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/Loding(100x25).gif"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        webcamClose();
        NewStudent newstudent = new NewStudent();
        newstudent.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        webcamClose();
        EditClasses editclasses = new EditClasses();
        editclasses.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        webcamClose();
        MainPkg.Welcome logout = new MainPkg.Welcome();
        logout.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        webcamClose();
        Payments payments = new Payments();
        payments.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
        if (jComboBox3.getSelectedIndex() != 0 && jComboBox3.getSelectedItem() != null) {
            String selectedClass = jComboBox3.getSelectedItem().toString();
            String thisYear = new SimpleDateFormat("yyyy").format(new Date());
            String thisMonth = new SimpleDateFormat("MM").format(new Date());
            String[] parts = selectedClass.split(" - ");
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * "
                        + "FROM subjects "
                        + "WHERE grade='" + parts[0] + "' AND subject='" + parts[1] + "'");
                while (rs.next()) {
                    ResultSet rs2 = stmt.executeQuery("SELECT * "
                            + "FROM classes "
                            + "WHERE subjectId='" + rs.getString("id") + "'");
                    while (rs2.next()) {
                        classId = rs2.getString("id");
                        ResultSet rs3 = stmt.executeQuery("SELECT * "
                                + "FROM payments "
                                + "WHERE studentId='" + studentIdLabel.getText() + "' AND "
                                + "classId='" + classId + "' AND "
                                + "year='" + thisYear + "' AND month='" + thisMonth + "'");
                        while (rs3.next()) {
                            if (rs3.getString("status").equals("1")) {
                                jTextField5.setText("Paid!");
                                jTextField5.setForeground(Color.green);
                            } else {
                                jTextField5.setText("Not Paid!");
                                jTextField5.setForeground(Color.red);
                            }
                        }
                    }
                }
                con.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
            jButton1.setEnabled(true);
        } else {
            jTextField5.setText("---");
            if (jButton1.isEnabled()) {
                jButton1.setEnabled(false);
            }
        }

    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String studentId = studentIdLabel.getText();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM regclass "
                    + "WHERE studentId='" + studentId + "' AND classId='" + classId + "'");
            while (rs.next()) {
                String regClassId = rs.getString("id");
                ResultSet rs2 = stmt.executeQuery("SELECT COUNT(*) "
                        + "FROM attendance "
                        + "WHERE regClassId='" + rs.getString("id") + "' "
                        + "AND date='" + date + "'");
                while (rs2.next()) {
                    int count = rs2.getInt(1);
                    ResultSet rs3 = stmt.executeQuery("SELECT * "
                            + "FROM students "
                            + "WHERE id='" + studentId + "'");
                    while (rs3.next()) {
                        tId.setText(rs3.getString("telegramId"));
                        String logtime = new SimpleDateFormat("HH:mm:ss").format(new Date());
                        if (count == 0) {
                            stmt.executeUpdate("INSERT INTO attendance "
                                    + "(regClassId,date,time) "
                                    + "VALUES "
                                    + "('" + regClassId + "','" + date + "','" + logtime + "')");
                            tMessage.attendanceMarking();
                            JOptionPane.showMessageDialog(this, "Success!");
                            disablePanels();
                        } else {
                            JOptionPane.showMessageDialog(this, "Already marked the attendance!");
                            disablePanels();
                        }
                    }
                }
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        dataGrab();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        disablePanels();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        jTextField1.setText("");
        jTextField2.setText("");
        jComboBox1.setSelectedIndex(0);
        dataGrab();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        if (jComboBox1.getSelectedIndex() != 0 && jComboBox1.getSelectedItem() != null) {
            String studentClassx = jComboBox1.getSelectedItem().toString();
            ArrayList<String> studentId = new ArrayList<>();
            ArrayList<String> studentName = new ArrayList<>();
            ArrayList<String> studentClass = new ArrayList<>();
            ArrayList<String> studentGc = new ArrayList<>();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            for (int count = 0; count < model.getRowCount(); count++) {
                studentId.add(model.getValueAt(count, 0).toString());
                studentName.add(model.getValueAt(count, 1).toString());
                studentClass.add(model.getValueAt(count, 2).toString());
                studentGc.add(model.getValueAt(count, 3).toString());
            }
            model.setRowCount(0);
            for (int count = 0; count < studentId.size(); count++) {
                if (studentClassx.equals(studentClass.get(count))) {
                    Object[] row = {studentId.get(count), studentName.get(count),
                        studentClass.get(count), studentGc.get(count)};
                    model.addRow(row);
                }
            }
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
        String studentIdx = jTextField1.getText();
        ArrayList<String> studentId = new ArrayList<>();
        ArrayList<String> studentName = new ArrayList<>();
        ArrayList<String> studentClass = new ArrayList<>();
        ArrayList<String> studentGc = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        for (int count = 0; count < model.getRowCount(); count++) {
            studentId.add(model.getValueAt(count, 0).toString());
            studentName.add(model.getValueAt(count, 1).toString());
            studentClass.add(model.getValueAt(count, 2).toString());
            studentGc.add(model.getValueAt(count, 3).toString());
        }
        model.setRowCount(0);
        for (int count = 0; count < studentId.size(); count++) {
            if (studentIdx.equals(studentId.get(count))) {
                Object[] row = {studentId.get(count), studentName.get(count),
                    studentClass.get(count), studentGc.get(count)};
                model.addRow(row);
            }
        }
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
        String studentNamex = jTextField2.getText();
        ArrayList<String> studentId = new ArrayList<>();
        ArrayList<String> studentName = new ArrayList<>();
        ArrayList<String> studentClass = new ArrayList<>();
        ArrayList<String> studentGc = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        for (int count = 0; count < model.getRowCount(); count++) {
            studentId.add(model.getValueAt(count, 0).toString());
            studentName.add(model.getValueAt(count, 1).toString());
            studentClass.add(model.getValueAt(count, 2).toString());
            studentGc.add(model.getValueAt(count, 3).toString());
        }
        model.setRowCount(0);
        for (int count = 0; count < studentId.size(); count++) {
            if (studentNamex.equalsIgnoreCase(studentName.get(count))) {
                Object[] row = {studentId.get(count), studentName.get(count),
                    studentClass.get(count), studentGc.get(count)};
                model.addRow(row);
            }
        }
    }//GEN-LAST:event_jTextField2ActionPerformed

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
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
    public static javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    public static javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    public static javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    public static javax.swing.JLabel studentIdLabel;
    public static javax.swing.JLabel tId;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
//                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            Result result = null;
            BufferedImage image = null;
            if (webcam.isOpen()) {
                if ((image = webcam.getImage()) == null) {
                    continue;
                }
            }
            if (image != null) {
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                try {
                    result = new MultiFormatReader().decode(bitmap);
                } catch (NotFoundException ex) {
//                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (result != null) {
                    qrResult = result.getText();
                    qrSlice();
                }
            }
        } while (true);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "My Thread");
        t.setDaemon(true);
        return t;
    }

    private void qrSlice() {
        String instituteName = Helper.MainDetails.instituteName();
        String[] parts = qrResult.split("-");
        if (parts[0].equals(instituteName) && parts[1].equals("STUDENT")) {
            String gotStudentId = parts[2];
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * "
                        + "FROM students "
                        + "WHERE id='" + gotStudentId + "'");
                while (rs.next()) {
                    studentIdLabel.setText(gotStudentId);
                    try ( PrintStream out = new PrintStream(
                            new File("C:\\ProgramData\\LycorisCafe\\IMS\\Temp\\StudentId.lc"))) {
                        out.println(gotStudentId);
                    } catch (FileNotFoundException ex) {
                        System.out.println(ex);
                    }
                    jTextField3.setText(rs.getString("firstName") + " "
                            + rs.getString("lastName"));
                    jTextField4.setText(rs.getString("grade"));
                    jLabel6.setText("");
                    ImageIcon myImage = new ImageIcon("C:\\ProgramData\\LycorisCafe\\IMS\\StudentImgs\\" + parts[2] + ".png");
                    Image newImage = myImage.getImage().getScaledInstance(136, 136, Image.SCALE_DEFAULT);
                    jLabel6.setIcon(new ImageIcon(newImage));
                    if (rs.getString("status").equals("0")) {
                        jTextField6.setText("Active");
                        jTextField6.setForeground(Color.GREEN);
                        Component[] com1 = jPanel6.getComponents();
                        for (int a = 0; a < com1.length; a++) {
                            com1[a].setEnabled(true);
                        }
                        jButton1.setEnabled(false);
                        jButton2.setEnabled(true);
                        jButton6.setEnabled(true);
                        ResultSet rs2 = stmt.executeQuery("SELECT * "
                                + "FROM regclass "
                                + "WHERE studentId='" + studentIdLabel.getText() + "'");
                        while (rs2.next()) {
                            ResultSet rs3 = stmt.executeQuery("SELECT * "
                                    + "FROM classes "
                                    + "WHERE id='" + rs2.getString("classId") + "' AND day='" + today + "'");
                            while (rs3.next()) {
                                ResultSet rs4 = stmt.executeQuery("SELECT * "
                                        + "FROM subjects "
                                        + "WHERE id='" + rs3.getString("subjectId") + "'");
                                while (rs4.next()) {
                                    jComboBox3.removeAllItems();
                                    jComboBox3.addItem("Please Select...");
                                    jComboBox3.setSelectedIndex(0);
                                    jComboBox3.addItem(rs4.getString("grade")
                                            + " - " + rs4.getString("subject"));
                                }
                            }
                        }
                    } else {
                        jTextField6.setText("Suspended!");
                        jTextField6.setForeground(Color.red);
                    }
                }
                con.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
            if (studentIdLabel.getText().equals("---")) {
                JOptionPane.showMessageDialog(this, "User not found!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid QR Code!");
        }
    }
}
