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

    // Global Variables ========>>>>>>>>
    String instituteName = Helper.MainDetails.instituteName();

    // method calling ===============>>>>>>>>>>>
    TelegramBot bot = new TelegramBot();
    SendMessage message = new SendMessage();
    SendPhoto photo = new SendPhoto();

    // message designs ===============>>>>>>>>>>
    public void studentRegistrationSuccess() {
        // @ Moderator.NewStudent jButton8 ActionPerformed
        // Send welcome message to each reggistered student
        String chatId = Moderator.NewStudent.telegramId.getText();
        String studentId = Moderator.NewStudent.tSendStudentId.getText();
        String studentName = Moderator.NewStudent.tSendStudentName.getText();
        String logo = "https://drive.google.com/uc?id=1XgoNzTsqpcvCLI790ICyb6XpsgO4uyVQ";
        photo.setChatId(chatId);
        photo.setPhoto(new InputFile(logo));
        photo.setCaption("Student Registration Success!\n\n"
                + "Student ID : " + instituteName + "-STUDENT-" + studentId + "\n"
                + "Student Name : " + studentName);
        photo.setProtectContent(true);
        sendPhoto();
<<<<<<< HEAD
        //institute + "-" + "STUDENT" + "-" + 
    }

    public void studentRegistrationClasses() {
        // @ Moderator.NewStudent jButton8 ActionPerformed
        // Send registed classes
        String chatId = Moderator.NewStudent.telegramId.getText();
=======
>>>>>>> 6c51a3a092cb39c875f9822b0062d72b93b16e8e
        int rowcount = Moderator.NewStudent.jTable1.getRowCount();
        for (int y = 0; y < rowcount; y++) {
            String grade = (String) Moderator.NewStudent.jTable1.getValueAt(y, 1);
            String subject = (String) Moderator.NewStudent.jTable1.getValueAt(y, 2);
            String teacher = (String) Moderator.NewStudent.jTable1.getValueAt(y, 3);
            String day = (String) Moderator.NewStudent.jTable1.getValueAt(y, 4);
            String payment = (String) Moderator.NewStudent.jTable1.getValueAt(y, 5);
            String studentName = Moderator.NewStudent.tSendStudentName.getText();

            // message body==>>
            message.setChatId(chatId);
            message.setText("Hello " + studentName + "\n"
                    + "Welcome to " + instituteName + " Institute \n"
                    + "You have  successfully enrolled " + subject + " Class  "  + "\n"
                    + "Teacher " + ": " + teacher + "\n"
                    + "Grade" + ": " + grade + "\n"
                    + "Class scheduled day " + ": " + day + "\n"
//                    + "Payment " + ":" + payment
            );

            sendMessage();
        }
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
