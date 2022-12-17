/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Moderator;

import java.awt.Toolkit;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lycoris Cafe
 */
public class EditClasses extends javax.swing.JFrame {

    /**
     * Creates new form EditClasses
     */
    public EditClasses() {
        initComponents();
        formDetails();
        getData();
    }

    String cr1x;
    String cr2x;
    String subjectId;
    String teacherId;
    String classId;

    private void getData() {
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        cr2.setEnabled(false);
        cr2.addItem("Please Select...");
        cr2.setSelectedIndex(0);
        cr3.setEnabled(false);
        cr3.addItem("Please Select...");
        cr3.setSelectedIndex(0);
        cr4.setEnabled(false);
        cr4.addItem("Please Select...");
        cr4.setSelectedIndex(0);
        try ( Stream<String> lines = Files.lines(
                Paths.get("C:\\ProgramData\\LycorisCafe\\IMS\\Temp\\StudentId.lc"))) {
            jTextField1.setText(lines.skip(0).findFirst().get());
        } catch (IOException ex) {
            System.out.println("#000"+ex);
        }
        String subject;
        String grade;
        String day;
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM regclass "
                    + "WHERE studentId='" + jTextField1.getText() + "'");
            while (rs.next()) {
                classId = rs.getString("classId");
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT * "
                        + "FROM classes "
                        + "WHERE id='" + classId + "'");
                while (rs2.next()) {
                    day = rs2.getString("day");
                    teacherId = rs2.getString("teacherId");
                    subjectId = rs2.getString("subjectId");
                    Statement stmt3 = con.createStatement();
                    ResultSet rs3 = stmt3.executeQuery("SELECT * "
                            + "FROM subjects "
                            + "WHERE id='" + subjectId + "'");
                    while (rs3.next()) {
                        grade = rs3.getString("grade");
                        subject = rs3.getString("subject");
                        Statement stmt4 = con.createStatement();
                        ResultSet rs4 = stmt4.executeQuery("SELECT * "
                                + "FROM teachers "
                                + "WHERE id='" + teacherId + "'");
                        while (rs4.next()) {
                            Object[] row = {classId, grade, subject,
                                rs4.getString("name"), day};
                            model.addRow(row);
                        }
                    }
                }
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("#001"+e);
        }
    }

    private void formDetails() {
        Helper.MainDetails details = new Helper.MainDetails();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(details.iconPath())));
        setExtendedState(this.MAXIMIZED_BOTH);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        cr1 = new javax.swing.JComboBox<>();
        cr2 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cr3 = new javax.swing.JComboBox<>();
        cr4 = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Edit Classes");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(762, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Registered Classes :"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton3.setText("Unregister");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 848, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Register to New Class :"));

        jLabel9.setText("Grade :");

        cr1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please Select...", "6", "7", "8", "9", "10", "11" }));
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

        jLabel10.setText("Subject :");

        jLabel11.setText("Teacher :");

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

        jLabel14.setText("Day :");

        jButton2.setText("Register");
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
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cr4, 0, 742, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cr1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cr2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cr3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cr1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cr2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cr3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(cr4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel6.setText("Student ID :");

        jTextField1.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cr1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cr1ActionPerformed
        // TODO add your handling code here:
        if (cr1.getSelectedIndex() == 0 || cr1.getSelectedItem() == null) {
            cr2.setEnabled(false);
            cr3.setEnabled(false);
            cr4.setEnabled(false);
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
                System.out.println("#002"+ex);
            }
        }
    }//GEN-LAST:event_cr1ActionPerformed

    private void cr2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cr2ActionPerformed
        // TODO add your handling code here:
        if (cr2.getSelectedIndex() == 0 || cr2.getSelectedItem() == null) {
            cr3.setEnabled(false);
            cr4.setEnabled(false);
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
                System.out.println("#003"+ex);
            }
        }
    }//GEN-LAST:event_cr2ActionPerformed

    private void cr3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cr3ActionPerformed
        // TODO add your handling code here:
        if (cr3.getSelectedIndex() == 0 || cr3.getSelectedItem() == null) {
            cr4.setEnabled(false);
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
                    int dayCho = Integer.parseInt(rs.getString("day"));
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
                    classId = rs.getString("id");
                }
                con.close();
            } catch (SQLException ex) {
                System.out.println("#004"+ex);
            }
        }
    }//GEN-LAST:event_cr3ActionPerformed

    private void cr4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cr4ActionPerformed
        // TODO add your handling code here:
        if (cr4.getSelectedIndex() == 0 || cr4.getSelectedItem() == null) {
            jButton2.setEnabled(false);
        } else {
            jButton2.setEnabled(true);
        }
    }//GEN-LAST:event_cr4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String year = new SimpleDateFormat("yyyy").format(new Date());
        String month = new SimpleDateFormat("MM").format(new Date());
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO regclass "
                    + "(studentId,classId) "
                    + "VALUES "
                    + "('" + jTextField1.getText() + "','" + classId + "')");
            con.close();
        } catch (SQLException ex) {
            System.out.println("#005"+ex);
        }
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO payments "
                    + "(studentId,classId,year,month,status,paymentDate) "
                    + "VALUES ('" + jTextField1.getText() + "',"
                    + "'" + classId + "',"
                    + "'" + year + "',"
                    + "'" + month + "','0','" + today + "')");
            con.close();
        } catch (SQLException ex) {
            System.out.println("#006"+ex);
        }
        getData();
        JOptionPane.showMessageDialog(this, "Success!");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        jButton3.setEnabled(true);
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int selectedrow = jTable1.getSelectedRow();
        String value = jTable1.getValueAt(selectedrow, 0).toString();
        int deleteitem = JOptionPane.showConfirmDialog(null, "Are you sure!?",
                "Warning", JOptionPane.YES_NO_OPTION);
        if (deleteitem == JOptionPane.YES_OPTION) {
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                stmt.executeUpdate("DELETE "
                        + "FROM regclass "
                        + "WHERE studentId='" + jTextField1.getText() + "' AND "
                        + "classId='" + value + "'");
                con.close();
            } catch (SQLException e) {
                System.out.println("#007"+e);
            }
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                stmt.executeUpdate("DELETE "
                        + "FROM payments "
                        + "WHERE studentId='" + jTextField1.getText() + "' AND "
                        + "classId='" + value + "'");
                con.close();
            } catch (SQLException e) {
                System.out.println("#008"+e);
            }
            model.removeRow(selectedrow);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Main main = new Main();
        main.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(EditClasses.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditClasses.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditClasses.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditClasses.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditClasses().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cr1;
    private javax.swing.JComboBox<String> cr2;
    private javax.swing.JComboBox<String> cr3;
    private javax.swing.JComboBox<String> cr4;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
