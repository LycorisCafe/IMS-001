/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 *
 * @author Anupama
 */
public class TelegramBot extends TelegramLongPollingBot {

    @Override
    public String getBotToken() {
        return IMSUpdater.botToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        
    }

    @Override
    public String getBotUsername() {
        return IMSUpdater.botUser();
    }
    
}
