/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author Anupama
 */
public class AutomatedMessages {
    
    SendPhoto photo = new SendPhoto();
    public void testMessage (){
        String myChat = "1241006555";
        
        File file = new File("C:\\Users\\Anupama\\Pictures\\92-920036_nature-top-whatsapp-dp.jpg");
        InputFile myFile = new InputFile(file);
        photo.setPhoto(myFile);
        photo.setChatId(myChat);
        photo.setCaption("hello!");
        send();
        
    }
    
    
    private void send(){
        TelegramBot bot = new TelegramBot();
        try {
            bot.execute(photo);
        } catch (TelegramApiException ex) {
            System.out.println(ex);
        }
    }
}
