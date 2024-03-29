/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package Main;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.ComputerSystem;
import oshi.hardware.HardwareAbstractionLayer;

/**
 *
 * @author Lycoris Cafe
 */
public class IMS {

    public static void main(String[] args) {
        Helper.AppUpdate updates = new Helper.AppUpdate();

        // =========================== Set Theme ===============================
        File theme = new File("C:\\ProgramData\\LycorisCafe\\IMS\\theme.lc");
        if (theme.exists()) {
            try {
                Scanner myReader = new Scanner(theme);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    if (data.equals("Light")) {
                        FlatLightLaf.setup();
                    }
                    if (data.equals("Dark")) {
                        FlatDarkLaf.setup();
                    }
                    if (data.equals("Default")) {
                        try {
                            UIManager.setLookAndFeel(
                                    UIManager.getSystemLookAndFeelClassName());
                        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
                        }
                    }
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println(e);
            }
        } else {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
                System.out.println(e);
            }
        }

        // ======================= Load Splash Screen ==========================
        SplashScreen splash = new SplashScreen();
        splash.setVisible(true);

        // ================= check internet connection =========================
        try {
            URL url = new URL("https://telegram.org");
            URLConnection connection = url.openConnection();
            connection.connect();
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(splash, "Internet connection error!\n"
                    + "Please try again after connect to the internet.");
            System.exit(0);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(splash, "Internet connection error!\n"
                    + "Please try again after connect to the internet.");
            System.exit(0);
        }

        // ============== send message if new version installed ================
        File newVersion = new File("C:\\ProgramData\\LycorisCafe\\IMS\\Temp\\newVersion.lc");
        if (newVersion.exists()) {
            Helper.AutomatedMessages msg = new Helper.AutomatedMessages();
            msg.upToDateMessage();
            newVersion.delete();
        }

        // ================== Make Application Workspace =======================
        File file = new File("C:\\ProgramData\\LycorisCafe\\IMS\\StudentImgs");
        if (!file.exists()) {
            file.mkdirs();
        }
        File file1 = new File("C:\\ProgramData\\LycorisCafe\\IMS\\Temp");
        if (!file1.exists()) {
            file1.mkdirs();
        }
        File file2 = new File("C:\\ProgramData\\LycorisCafe\\IMS\\Logs");
        if (!file2.exists()) {
            file2.mkdirs();
        }
        File file3 = new File("C:\\ProgramData\\LycorisCafe\\IMS\\StudentCards");
        if (!file3.exists()) {
            file3.mkdirs();
        }
        File file4 = new File("C:\\ProgramData\\LycorisCafe\\IMS\\frontPage.png");
        File file5 = new File("C:\\ProgramData\\LycorisCafe\\IMS\\backPage.png");
        if (!file4.exists() || !file5.exists()) {
            updates.firstRunDownloads();
        }

        // ===================== Register Telegram Bot =========================
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot((LongPollingBot) new Helper.TelegramBot());
        } catch (TelegramApiException e) {
            System.out.println(e);
        }

        // ================ Download updates when available ====================
        updates.checkUpdates();

//        // ========================= Check License =============================
//        SystemInfo systemInfo = new SystemInfo();
//        HardwareAbstractionLayer hardware = systemInfo.getHardware();
//        //get processor id
//        CentralProcessor processorx = hardware.getProcessor();
//        CentralProcessor.ProcessorIdentifier processorIdentifier = processorx.getProcessorIdentifier();
//        String processor = processorIdentifier.getProcessorID();
//        //get baseboard id
//        ComputerSystem comsys = hardware.getComputerSystem();
//        String baseboard = comsys.getBaseboard().getSerialNumber();
//        if (processor.equals(Helper.MainDetails.cpuId()) 
//                && baseboard.equals(Helper.MainDetails.baseBordId())) {
        Welcome welcome = new Welcome();
        splash.dispose();
        welcome.setVisible(true);
//        } else {
//            AuthError auth = new AuthError();
//            splash.dispose();
//            auth.setVisible(true);
//        }

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                Helper.AppUpdate update = new Helper.AppUpdate();
                update.appExitUpdate();
            }
        }, "Shutdown-thread"));

    }
}
