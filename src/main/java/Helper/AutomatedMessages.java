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
        // @ Moderator.NewStudent jButton8 ActionPerformed
        String chatId = Moderator.NewStudent.telegramId.getText();
        String studentId = Moderator.NewStudent.tSendStudentId.getText();
        String studentName = Moderator.NewStudent.tSendStudentName.getText();
        String logo = "https://drive.google.com/uc?id=1XgoNzTsqpcvCLI790ICyb6XpsgO4uyVQ";
        photo.setChatId(chatId);
        photo.setPhoto(new InputFile(logo));
        photo.setCaption("Student Registration Success!\n\n"
                + "Student ID : " + studentId + "\n"
                + "Student Name : " + studentName);
        photo.setProtectContent(true);
        sendPhoto();
    }

    public void pushExamResults() {
        // @ Administartor.ExamResults jButton2 ActionPerformed
        String studentName = Administrator.ExamResults.studentName.getText();
        String telegramId = Administrator.ExamResults.telegramId.getText();
        String teacherName = Administrator.ExamResults.teacherName.getText();
        String className = Administrator.ExamResults.className.getText();
        String examName = Administrator.ExamResults.examName.getText();
        String examDate = Administrator.ExamResults.examDate.getText();
        String marks = Administrator.ExamResults.marks.getText();
        String rank = Administrator.ExamResults.rank.getText();
        String image = "https://drive.google.com/uc?id=1uSdNx09HJQP_JcOpAlIkl8CnLXdgUEgz";
        String caption
                = "Exam Results Released!\n\n"
                + "Student Name : " + studentName + "\n"
                + "Class : " + className + "\n"
                + "Teacher : " + teacherName + "\n\n"
                + "Exam Name : " + examName + "\n"
                + "Exam Date : " + examDate + "\n\n"
                + "Marks : " + marks + "\n"
                + "Rank : " + rank;

        photo.setChatId(telegramId);
        photo.setPhoto(new InputFile(image));
        photo.setCaption(caption);
        photo.setProtectContent(true);
        sendPhoto();
    }

    // sending operations =========>>>>>>>>>>>
    private void sendMessage() {
        try {
            bot.execute(message);
            if (Administrator.TelegramReports.jTextArea1 != null) {
                Administrator.TelegramReports.jTextArea1.setText("Success!\n");
                int successCount = Integer.parseInt(Administrator.TelegramReports.jLabel3.getText());
                successCount = successCount + 1;
                Administrator.TelegramReports.jLabel3.setText("" + successCount);
            }
        } catch (TelegramApiException ex) {
            System.out.println(ex);
            if (Administrator.TelegramReports.jTextArea1 != null) {
                Administrator.TelegramReports.jTextArea1.setText("Unuccess!\n");
                int unsuccessCount = Integer.parseInt(Administrator.TelegramReports.jLabel4.getText());
                unsuccessCount = unsuccessCount + 1;
                Administrator.TelegramReports.jLabel4.setText("" + unsuccessCount);
            }
        }
    }

    private void sendPhoto() {
        try {
            bot.execute(photo);
            if (Administrator.TelegramReports.jTextArea1 != null) {
                Administrator.TelegramReports.jTextArea1.setText("Success!\n");
                int successCount = Integer.parseInt(Administrator.TelegramReports.jLabel3.getText());
                successCount = successCount + 1;
                Administrator.TelegramReports.jLabel3.setText("" + successCount);
            }
        } catch (TelegramApiException ex) {
            System.out.println(ex);
            if (Administrator.TelegramReports.jTextArea1 != null) {
                Administrator.TelegramReports.jTextArea1.setText("Unuccess!\n");
                int unsuccessCount = Integer.parseInt(Administrator.TelegramReports.jLabel4.getText());
                unsuccessCount = unsuccessCount + 1;
                Administrator.TelegramReports.jLabel4.setText("" + unsuccessCount);
            }
        }
    }
}
