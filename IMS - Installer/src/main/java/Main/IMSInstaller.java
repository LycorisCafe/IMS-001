/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package Main;

import com.formdev.flatlaf.FlatDarkLaf;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 *
 * @author Anupama
 */
public class IMSInstaller {

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot((LongPollingBot) new TelegramBot());
        } catch (TelegramApiException e) {
            System.out.println(e);
        }
        
        Interface ui = new Interface();
        ui.setVisible(true);
    }
    
    public static String chatId(){
        String chatId = "-1001698896292";
        return chatId;
    }
    
    public static String botToken(){
        String token = "5347591870:AAE72QOdY-qYn7jPGCzi2qYIj68o1ToVLmE";
        return token;
    }
    
    public static String botUser(){
        String user = "@NaveenB2004_bot";
        return user;
    }
}
