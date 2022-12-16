/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author Lycoris Cafe
 */
public class AppUpdate {

    public static String newVersionCheck() {
        // new version raw (just 0.2 or something)
        String newVerLink = "https://pastebin.com/raw/1rJGEQKd";
        return newVerLink;
    }

    public static String newVersionDownload() {
        // new version download links raw
        String newVersionDownload = "https://pastebin.com/raw/UTaaiKjT";
        return newVersionDownload;
    }

    public static String unrar() {
        // unrar.exe download link
        String unrar = "https://pastebin.com/raw/TvBduKEW";
        return unrar;
    }

    public static String updater() {
        // updater.bat download link
        String updater = "https://pastebin.com/raw/7x8pwcrv";
        return updater;
    }

    public static String firstRun() {
        // first run downloads
        String firstRun = "https://pastebin.com/raw/GZqrmR50";
        return firstRun;
    }

    public void checkUpdates() {
        String newVersionCheck = newVersionCheck();
        String thisVersion = Helper.MainDetails.version();
        String newVersion = null;
        float thisVersionx = 0;
        float newVersionx = 0;

        try {
            URL url = new URL(newVersionCheck);
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            try ( BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line = null;
                while ((line = br.readLine()) != null) {
                    newVersion = line;
                }
            }
        } catch (MalformedURLException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }

        try {
            thisVersionx = Float.parseFloat(thisVersion);
            newVersionx = Float.parseFloat(newVersion);
        } catch (NumberFormatException e) {
            System.out.println(e);
        }

        if (thisVersionx < newVersionx) {
            Updates update = new Updates();
            update.setVisible(true);
        }
    }

    public void firstRunDownloads() {
        String firstRun = firstRun();
        TelegramBot bot = new TelegramBot();
        String fileName;
        try {
            URL url = new URL(firstRun);
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            try ( BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line = null;
                int count = 0;
                while ((line = br.readLine()) != null) {
                    count = count + 1;
                    if (count == 1) {
                        fileName = "frontPage.png";
                    } else {
                        fileName = "backPage.png";
                    }
                    try {
                        GetFile getFile = new GetFile();
                        getFile.setFileId(line);
                        String filePath = bot.execute(getFile).getFilePath();
                        bot.downloadFile(filePath, new File("C:\\ProgramData\\LycorisCafe\\IMS\\" + fileName));
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
    }
}
