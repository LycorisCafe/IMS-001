/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.stream.Stream;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author Anupama
 */
public class Updater {

    public void update() {
        TelegramBot bot = new TelegramBot();
        String path = "C:\\ProgramData\\LycorisCafe\\IMS\\Temp\\appPath.lc";
        String installedLocation = null;
        String count = null;
        try ( Stream<String> lines = Files.lines(Paths.get(path))) {
            installedLocation = lines.skip(0).findFirst().get();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        try ( Stream<String> lines = Files.lines(Paths.get(path))) {
            count = lines.skip(1).findFirst().get();
        } catch (IOException ex) {
            System.out.println(ex);
        }

        // previous version delete
        File app = new File(installedLocation + "\\IMS.exe");
        app.delete();

        // move .rar files to installatiuon directory
        int fileCount = Integer.parseInt(count);
        for (int x = 1; x > fileCount; x++) {
            File file = new File("C:\\ProgramData\\LycorisCafe\\IMS\\Temp\\" + x + ".rar");
            file.renameTo(new File(installedLocation + "\\" + x + ".rar"));
        }

        // decompressing and installing new version
        try {
            ProcessBuilder processBuilder
                    = new ProcessBuilder("unrar.exe x " + installedLocation + "\\1.rar");
            processBuilder.redirectErrorStream(true);
            processBuilder.start();
        } catch (IOException e) {
            System.out.println(e);
        }

        // delete leftovers
        File unrar = new File("C:\\ProgramData\\LycorisCafe\\IMS\\Temp\\unrar.exe");
        File text = new File("C:\\ProgramData\\LycorisCafe\\IMS\\Temp\\appPath.lc");
        unrar.delete();
        text.delete();
        for (int x = 1; x > fileCount; x++) {
            File file = new File(installedLocation + "\\" + x + ".rar");
            file.delete();
        }

        String computerName = null;
        try {
            ProcessBuilder processBuilder
                    = new ProcessBuilder("cmd.exe", "/c",
                            "hostname");
            processBuilder.redirectErrorStream(true);
            Process p = processBuilder.start();
            String line = null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = bufferedReader.readLine()) != null) {
                computerName = line;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        String logTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        SendMessage ms = new SendMessage();
        ms.setChatId(IMSUpdater.chatId());
        ms.setText("New version successfully installed!\n\n"
                + "Computer Name : " + computerName + "\n"
                + "Time : " + logTime);
        try {
            bot.execute(ms);
        } catch (TelegramApiException e) {
            System.out.println(e);
        }

        System.exit(0);
    }
}
