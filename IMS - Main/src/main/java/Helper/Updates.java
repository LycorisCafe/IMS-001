/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Helper;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.security.CodeSource;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author Anupama
 */
public class Updates extends javax.swing.JFrame {

    /**
     * Creates new form Updates
     */
    public Updates() {
        initComponents();
        formDetails();
        downloadUpdates();
    }
    
    private void formDetails() {
        Helper.MainDetails details = new Helper.MainDetails();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(details.iconPath())));
    }

    private void downloadUpdates() {
        jLabel1.setText("Preparing Update for Download...");
        jProgressBar1.setStringPainted(true);
        String newVersionDownload = AppUpdate.newVersionDownload();
        String unrarDownload = AppUpdate.unrar();
        String updaterDownload = AppUpdate.updater();
        TelegramBot bot = new TelegramBot();
        try {
            URL url = new URL(newVersionDownload);
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            try ( BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line = null;
                int count = 0;
                while ((line = br.readLine()) != null) {
                    count = count + 1;
                }
                jProgressBar1.setMaximum(count);
                jProgressBar1.setMinimum(0);
            }
        } catch (MalformedURLException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        // =====================================================================
        jLabel1.setText("Downloading Update...");
        try {
            URL url = new URL(newVersionDownload);
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            try ( BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line = null;
                int count = 0;
                while ((line = br.readLine()) != null) {
                    count = count + 1;
                    try {
                        GetFile getFile = new GetFile();
                        getFile.setFileId(line);
                        String filePath = bot.execute(getFile).getFilePath();
                        bot.downloadFile(filePath, new File("C:\\ProgramData\\LycorisCafe\\IMS\\Temp\\" + count + ".rar"));
                        jProgressBar1.setValue(count);
                    } catch (TelegramApiException e) {
                        System.out.println(e);
                    }
                }
            }
        } catch (MalformedURLException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        // =====================================================================
        jLabel1.setText("Downloading Extractor...");
        try {
            URL url = new URL(unrarDownload);
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            try ( BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line = null;
                while ((line = br.readLine()) != null) {
                    try {
                        GetFile getFile = new GetFile();
                        getFile.setFileId(line);
                        String filePath = bot.execute(getFile).getFilePath();
                        bot.downloadFile(filePath, new File("C:\\ProgramData\\LycorisCafe\\IMS\\Temp\\unrar.exe"));
                    } catch (TelegramApiException e) {
                        System.out.println(e);
                    }
                }
            }
        } catch (MalformedURLException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        // =====================================================================
        jLabel1.setText("Downloading Updater...");
        try {
            URL url = new URL(updaterDownload);
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            try ( BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line = null;
                while ((line = br.readLine()) != null) {
                    try {
                        GetFile getFile = new GetFile();
                        getFile.setFileId(line);
                        String filePath = bot.execute(getFile).getFilePath();
                        bot.downloadFile(filePath, new File("C:\\ProgramData\\LycorisCafe\\IMS\\Temp\\updater.exe"));
                    } catch (TelegramApiException e) {
                        System.out.println(e);
                    }
                }
            }
        } catch (MalformedURLException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        // =====================================================================
        jLabel1.setText("Setting Paths...");
        String jarDir = null;
        try {
            CodeSource codeSource = MainPkg.IMS.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            jarDir = jarFile.getParentFile().getPath();
        } catch (URISyntaxException e) {
            System.out.println(e);
        }
        try ( PrintStream out = new PrintStream(new File("C:\\ProgramData\\LycorisCafe\\IMS\\Temp\\appPath.lc"))) {
            out.println(jarDir);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
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

        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setTitle("Updater");
        setUndecorated(true);
        setResizable(false);

        jProgressBar1.setFont(new java.awt.Font("Consolas", 0, 8)); // NOI18N

        jLabel1.setText("---");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/Loding(100x25).gif"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Updates.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Updates.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Updates.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Updates.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Updates().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
}
