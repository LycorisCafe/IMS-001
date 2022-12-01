/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package MainPkg;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
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
        
        // ===================== Register Telegram Bot =========================
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot((LongPollingBot) new Helper.TelegramBot());
        } catch (TelegramApiException e) {
            System.out.println(e);
        }

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
//            Welcome welcome = new Welcome();
//            splash.dispose();
//            welcome.setVisible(true);
//        } else {
//            AuthError auth = new AuthError();
//            splash.dispose();
//            auth.setVisible(true);
//        }

        // =================== Load Main Interface (DEMO) ======================
        Welcome welcome = new Welcome();
        splash.dispose();
        welcome.setVisible(true);
    }
}
