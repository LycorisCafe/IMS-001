/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Moderator;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lycoris Cafe
 */
public class PayNowGate extends javax.swing.JFrame {

    /**
     * Creates new form PayNowGate
     */
    public PayNowGate() {
        initComponents();
        formDetails();
        dataGrab();
    }

    Helper.AutomatedMessages autoMessage = new Helper.AutomatedMessages();

    private void formDetails() {
        Helper.MainDetails details = new Helper.MainDetails();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(details.iconPath())));
    }

    private void dataGrab() {
        String data = Payments.jLabel3.getText();
        String[] parts = data.split("-");
        jTextField1.setText(parts[0]);
        jTextField2.setText(parts[1]);
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM classes "
                    + "WHERE id='" + jTextField2.getText() + "'");
            while (rs.next()) {
                jTextField3.setText(rs.getString("payment"));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        dataRefresh();
    }

    private void dataRefresh() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM payments "
                    + "WHERE studentId='" + jTextField1.getText() + "' "
                    + "AND classId='" + jTextField2.getText() + "' "
                    + "AND status='1'");
            while (rs.next()) {
                Object[] row = {rs.getString("id"), rs.getString("year"),
                    rs.getString("month")};
                model.addRow(row);
            }
            con.close();
        } catch (SQLException e) {
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

        studentName = new javax.swing.JLabel();
        className = new javax.swing.JLabel();
        classTeacher = new javax.swing.JLabel();
        classDay = new javax.swing.JLabel();
        paymentDay = new javax.swing.JLabel();
        paymentId = new javax.swing.JLabel();
        telegramId = new javax.swing.JLabel();
        paymentValue = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();

        studentName.setText("jLabel5");

        className.setText("jLabel6");

        classTeacher.setText("jLabel7");

        classDay.setText("jLabel8");

        paymentDay.setText("jLabel9");

        paymentId.setText("jLabel4");

        telegramId.setText("jLabel4");

        paymentValue.setText("jLabel4");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Payment Gateway");
        setResizable(false);

        jLabel1.setText("Student ID :");

        jTextField1.setEditable(false);

        jLabel2.setText("Class ID :");

        jTextField2.setEditable(false);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Year", "Month"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Pay Now!");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Amount :");

        jTextField3.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1)
                            .addComponent(jTextField2)
                            .addComponent(jTextField3))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int year;
        int month;
        int status;
        int id;
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM payments "
                    + "WHERE studentId='" + jTextField1.getText() + "' "
                    + "AND classId='" + jTextField2.getText() + "' "
                    + "ORDER BY year DESC LIMIT 1");
            while (rs.next()) {
                status = rs.getInt("status");
                year = rs.getInt("year");
                ResultSet rs2 = stmt.executeQuery("SELECT * "
                        + "FROM payments "
                        + "WHERE studentId='" + jTextField1.getText() + "' "
                        + "AND classId='" + jTextField2.getText() + "' "
                        + "AND year='" + year + "' "
                        + "ORDER BY month DESC LIMIT 1");
                while (rs2.next()) {
                    month = rs2.getInt("month");
                    id = rs2.getInt("id");
                    if (status == 1) {
                        month = month + 1;
                        if (month == 13) {
                            year = year + 1;
                            month = 1;
                        }
                        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                        stmt.executeUpdate("INSERT INTO payments "
                                + "(studentId,classId,year,month,status,paymentDate) "
                                + "VALUES "
                                + "('" + jTextField1.getText() + "',"
                                + "'" + jTextField2.getText() + "',"
                                + "'" + year + "',"
                                + "'" + month + "','1','" + today + "')");
                        dataRefresh();
                    } else {
                        stmt.executeUpdate("UPDATE payments "
                                + "SET status='1' "
                                + "WHERE id='" + id + "'");
                        dataRefresh();
                    }
                    String monthString = null;
                    switch (month) {
                        case 1 ->
                            monthString = "January";
                        case 2 ->
                            monthString = "February";
                        case 3 ->
                            monthString = "March";
                        case 4 ->
                            monthString = "April";
                        case 5 ->
                            monthString = "May";
                        case 6 ->
                            monthString = "June";
                        case 7 ->
                            monthString = "July";
                        case 8 ->
                            monthString = "August";
                        case 9 ->
                            monthString = "September";
                        case 10 ->
                            monthString = "October";
                        case 11 ->
                            monthString = "November";
                        case 12 ->
                            monthString = "December";

                    }
                    paymentDay.setText(year + " - " + monthString);
                    telegramUpdate();
                    autoMessage.paymentSuccess();
                    JOptionPane.showMessageDialog(this, "Success!");
                }
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void telegramUpdate() {
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM students "
                    + "WHERE id='" + jTextField1.getText() + "'");
            while (rs.next()) {
                telegramId.setText(rs.getString("telegramId"));
                studentName.setText(rs.getString("firstName") + " "
                        + rs.getString("lastName"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM classes "
                    + "WHERE id='" + jTextField2.getText() + "'");
            while (rs.next()) {
                paymentValue.setText(rs.getString("payment"));
                int day = rs.getInt("day");
                switch (day) {
                    case 1 ->
                        classDay.setText("Monday");
                    case 2 ->
                        classDay.setText("Tuesday");
                    case 3 ->
                        classDay.setText("Wednesday");
                    case 4 ->
                        classDay.setText("Thursday");
                    case 5 ->
                        classDay.setText("Friday");
                    case 6 ->
                        classDay.setText("Saturday");
                    case 7 ->
                        classDay.setText("Sunday");
                }
                String teacherId = rs.getString("teacherId");
                ResultSet rs2 = stmt.executeQuery("SELECT * "
                        + "FROM subjects "
                        + "WHERE id='" + rs.getString("subjectId") + "'");
                while (rs2.next()) {
                    className.setText(rs2.getString("grade") + " - " + rs2.getString("subject"));
                    ResultSet rs3 = stmt.executeQuery("SELECT * "
                            + "FROM teachers "
                            + "WHERE id='" + teacherId + "'");
                    while (rs3.next()) {
                        classTeacher.setText(rs3.getString("name"));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        paymentId.setText(model.getValueAt(model.getRowCount() - 1, 0).toString());
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
            java.util.logging.Logger.getLogger(PayNowGate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PayNowGate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PayNowGate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PayNowGate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PayNowGate().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JLabel classDay;
    public static javax.swing.JLabel className;
    public static javax.swing.JLabel classTeacher;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    public static javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    public static javax.swing.JLabel paymentDay;
    public static javax.swing.JLabel paymentId;
    public static javax.swing.JLabel paymentValue;
    public static javax.swing.JLabel studentName;
    public static javax.swing.JLabel telegramId;
    // End of variables declaration//GEN-END:variables
}
