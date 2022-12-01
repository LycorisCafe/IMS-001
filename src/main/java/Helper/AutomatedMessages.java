/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author Lycoris Cafe
 */
public class AutomatedMessages {

    // method calling ===============>>>>>>>>>>>
    TelegramBot bot = new TelegramBot();
    SendMessage message = new SendMessage();
    SendPhoto photo = new SendPhoto();

    // message designs ===============>>>>>>>>>>
    public void studentRegistrationSuccess() {
        // @ Moderator.NewStudent.java jButton8 ActionPerformed
        String chatId = Moderator.NewStudent.telegramId.getText();
        String studentId = Moderator.NewStudent.tSendStudentId.getText();
        String studentName = Moderator.NewStudent.tSendStudentName.getText();
        String logo = "https://drive.google.com/uc?id=17YSyIO5gdI1A_YtgURWxTT3ArfXpYdyw";
        photo.setChatId(chatId);
        photo.setPhoto(new InputFile(logo));
        photo.setCaption("Student Registration Success!\n\n"
                + "Student ID : " + studentId + "\n"
                + "Student Name : " + studentName);
        photo.setProtectContent(true);
        sendPhoto();
    }

    // sending operations =========>>>>>>>>>>>
    private void sendMessage() {
        try {
            bot.execute(message);
        } catch (TelegramApiException ex) {
            System.out.println(ex);
        }
    }

    private void sendPhoto() {
        try {
            bot.execute(photo);
        } catch (TelegramApiException ex) {
            System.out.println(ex);
        }
    }
}
