/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import java.text.SimpleDateFormat;
import java.util.Date;
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
                + "Welcome to " + instituteName + " Institute\n"
                + "Student ID : " + instituteName + "-STUDENT-" + studentId + "\n"
                + "Student Name : " + studentName);
        photo.setProtectContent(true);
        sendPhoto();
        int rowcount = Moderator.NewStudent.jTable1.getRowCount();
        for (int y = 0; y < rowcount; y++) {
            String grade = (String) Moderator.NewStudent.jTable1.getValueAt(y, 1);
            String subject = (String) Moderator.NewStudent.jTable1.getValueAt(y, 2);
            String teacher = (String) Moderator.NewStudent.jTable1.getValueAt(y, 3);
            String day = (String) Moderator.NewStudent.jTable1.getValueAt(y, 4);
            String payment = (String) Moderator.NewStudent.jTable1.getValueAt(y, 5);
            message.setChatId(chatId);
            message.setText("Hello " + studentName + "\n"
                    + "You have  successfully enrolled " + subject + " Class\n"
                    + "Teacher : " + teacher + "\n"
                    + "Grade : " + grade + "\n"
                    + "Class scheduled day : " + day + "\n"
                    + "Payment : " + payment
            );
            sendMessage();
        }
    }

    public void attendanceMarking() {
        // Moderator (pkg) -> Main -> Mark Ateendance (button)
        String chatId = Moderator.Main.tId.getText();
        String studentId = instituteName + "-STUDENT-" + Moderator.Main.studentIdLabel.getText();
        String studentName = Moderator.Main.jTextField3.getText();
        String atendClass = Moderator.Main.jComboBox3.getSelectedItem().toString();
        String paymentDetails = Moderator.Main.jTextField5.getText();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        // message body start
        // message body end
        sendMessage();
    }

    public void newExamAddedStudent() {
        // Administrator (pkg) -> Main -> Exams (tab) -> Add (button)
        String chatId = Administrator.Main.telegramId.getText();
        String examName = Administrator.Main.jTextField22.getText();
        String examDate = Administrator.Main.jTextField27.getText();

        // message body start
        // greeting msg ekakuth danna aa nikn wish you all the best wge
        // message body end
        sendMessage(); // or sendPhoto
    }

    public void newExamAddedGroup() {
        // Administrator (pkg) -> Main -> Exams (tab) -> Add (button)
        String groupId = Administrator.Main.tGroupId.getText();
        String examName = Administrator.Main.jTextField22.getText();
        String examDate = Administrator.Main.jTextField27.getText();

        // message body start
        // greeting msg ekakuth danna aa nikn wish you all the best wge
        // message body end
        sendMessage(); // or sendPhoto();
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
