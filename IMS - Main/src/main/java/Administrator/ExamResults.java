/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Administrator;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author Lycoris Cafe
 */
public class ExamResults extends javax.swing.JFrame {

    /**
     * Creates new form ExamResults
     */
    public ExamResults() {
        initComponents();
        formDetails();
        loadResults();
    }

    private void formDetails() {
        Helper.MainDetails details = new Helper.MainDetails();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(details.iconPath())));
        setExtendedState(this.MAXIMIZED_BOTH);
    }

    private void loadResults() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        String examId = Main.examId.getText();
        String rank = null;
        try {
            Connection con = Helper.DB.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * "
                    + "FROM results "
                    + "WHERE examId='" + examId + "'");
            while (rs.next()) {
                String resultId = rs.getString("id");
                String marks = rs.getString("marks");
                String studentId = rs.getString("studentId");
                ResultSet rs2 = stmt.executeQuery("SELECT * "
                        + "FROM students "
                        + "WHERE id='" + studentId + "'");
                while (rs2.next()) {
                    if (marks.equals("N/A")) {
                        rank = marks;
                    } else {
                        int marksx = Integer.parseInt(marks);
                        if (marksx >= 75) {
                            rank = "A";
                        } else if (marksx >= 65) {
                            rank = "B";
                        } else if (marksx >= 55) {
                            rank = "C";
                        } else if (marksx >= 35) {
                            rank = "S";
                        } else {
                            rank = "W";
                        }
                    }
                    Object[] row = {resultId, studentId, rs2.getString("firstName")
                        + " " + rs2.getString("lastName"), marks, rank};
                    model.addRow(row);
                }
            }
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

        tReportLinking = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        tReportLinking.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Exam Results");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Result Id", "Student Id", "Student Name", "Marks", "Rank"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Update");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Push");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel1.setText("[ Exam results will send via Telegram Bot ]");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 920, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int y = 0;
        int count = jTable1.getRowCount();
        for (int x = 0; x < count; x++) {
            String resultId = jTable1.getValueAt(y, 0).toString();
            String marks = jTable1.getValueAt(y, 3).toString();
            try {
                Connection con = Helper.DB.connect();
                Statement stmt = con.createStatement();
                stmt.executeUpdate("UPDATE results SET "
                        + "marks='" + marks + "' "
                        + "WHERE id='" + resultId + "'");
            } catch (SQLException e) {
                System.out.println(e);
            }
            y = y + 1;
        }
        JOptionPane.showMessageDialog(this, "Success!");
        loadResults();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int pushConfirm = JOptionPane.showConfirmDialog(null,
                "Confirm that the all results added successfully.\n"
                + "Cuz, message can't be delete after sent!\nDo you want to continue?",
                "Warning", JOptionPane.YES_NO_OPTION);
        if (pushConfirm == JOptionPane.YES_OPTION) {
            loadResults();
            Helper.TelegramBot bot = new Helper.TelegramBot();
            SendPhoto message = new SendPhoto();
            int y = 0;
            int count = jTable1.getRowCount();
            for (int x = 0; x < count; x++) {
                String resultId = jTable1.getValueAt(y, 0).toString();
                String studentId = jTable1.getValueAt(y, 1).toString();
                String marksx = jTable1.getValueAt(y, 3).toString();
                String rankx = jTable1.getValueAt(y, 4).toString();
                try {
                    Connection con = Helper.DB.connect();
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * "
                            + "FROM students "
                            + "WHERE id='" + studentId + "'");
                    while (rs.next()) {
                        String studentNamex = rs.getString("firstName") + " " + rs.getString("lastName");
                        String telegramIdx = rs.getString("telegramId");
                        ResultSet rs2 = stmt.executeQuery("SELECT * "
                                + "FROM results "
                                + "WHERE id='" + resultId + "'");
                        while (rs2.next()) {
                            ResultSet rs3 = stmt.executeQuery("SELECT * "
                                    + "FROM exams "
                                    + "WHERE id='" + rs2.getString("examId") + "'");
                            while (rs3.next()) {
                                String examNamex = rs3.getString("name");
                                String examDatex = rs3.getString("date");
                                ResultSet rs4 = stmt.executeQuery("SELECT * "
                                        + "FROM classes "
                                        + "WHERE id='" + rs3.getString("classId") + "'");
                                while (rs4.next()) {
                                    String teacherId = rs4.getString("teacherId");
                                    String subjectId = rs4.getString("subjectId");
                                    ResultSet rs5 = stmt.executeQuery("SELECT * "
                                            + "FROM teachers "
                                            + "WHERE id='" + teacherId + "'");
                                    while (rs5.next()) {
                                        String teacherNamex = rs5.getString("name");
                                        ResultSet rs6 = stmt.executeQuery("SELECT * "
                                                + "FROM subjects "
                                                + "WHERE id='" + subjectId + "'");
                                        while (rs6.next()) {
                                            String classNamex = rs6.getString("grade")
                                                    + " - " + rs6.getString("subject");
                                            message.setChatId(telegramIdx);
                                            message.setPhoto(
                                                    new InputFile("https://drive.google.com/uc?id=1uSdNx09HJQP_JcOpAlIkl8CnLXdgUEgz"));
                                            message.setCaption("Exam Results Released!\n\n"
                                                    + "Student Name : " + studentNamex + "\n"
                                                    + "Class : " + classNamex + "\n"
                                                    + "Teacher : " + teacherNamex + "\n\n"
                                                    + "Exam Name : " + examNamex + "\n"
                                                    + "Exam Date : " + examDatex + "\n\n"
                                                    + "Marks : " + marksx + "\n"
                                                    + "Rank : " + rankx);
                                            message.setProtectContent(true);
                                            try {
                                                bot.execute(message);
                                            } catch (TelegramApiException e) {
                                                System.out.println(e);
                                                JOptionPane.showMessageDialog(this, "Error while send results to :\n"
                                                        + "Name : " + studentNamex + "\n"
                                                        + "Class : " + classNamex);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                }
                y = y + 1;
            }
            JOptionPane.showMessageDialog(this, "Success!");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(ExamResults.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExamResults.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExamResults.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExamResults.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExamResults().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    public static javax.swing.JLabel tReportLinking;
    // End of variables declaration//GEN-END:variables
}