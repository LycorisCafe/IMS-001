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
public class TelegramBot extends TelegramLongPollingBot{

    @Override
    public String getBotToken() {
        return "5347591870:AAE72QOdY-qYn7jPGCzi2qYIj68o1ToVLmE";
    }

    @Override
    public void onUpdateReceived(Update update) {
        
    }

    @Override
    public String getBotUsername() {
        return "NaveenB2004_bot";
    }
    
}
