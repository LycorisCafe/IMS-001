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
        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm a ").format(new Date());

        // message body start
        // message body end
        message.setChatId(chatId);
        message.setText("Student Attendance Marked! \n\n"
                + "Studnt Id " + ":" + studentId + "\n"
                + "Student Name " + ":" + studentName + "\n"
                + "Atend Class " + ":" + atendClass + "\n"
                + "Payments " + ":" + paymentDetails + "\n"
                + "Time " + ":" + time
        );
        sendMessage();
    }

    public void newExamAddedStudent() {
        System.out.println("Hello");
        // Administrator (pkg) -> Main -> Exams (tab) -> Add (button)
        String chatId = Administrator.Main.telegramId.getText();
        String grade = Administrator.Main.cr1.getSelectedItem().toString();
        String subject = Administrator.Main.cr2.getSelectedItem().toString();
        String teacher = Administrator.Main.cr3.getSelectedItem().toString();
        String day = Administrator.Main.cr4.getSelectedItem().toString();
        String examName = Administrator.Main.jTextField22.getText();
        String examDate = Administrator.Main.jTextField27.getText();
<<<<<<< HEAD

        photo.setPhoto(new InputFile("AgACAgUAAxkBAAEawhhjkailyDqig3uSn5KyAYCD1ONotwACQbExG-XLiVSSFmc_l8QlGQEAAwIAA3kAAysE"));
        System.out.println(grade + " " + subject + " " + teacher + " " + day);
=======
        
        photo.setPhoto(new InputFile("AgACAgUAAx0CaCw0FAADJWOQu1s0v-FOwfh8rNTKhv2xUsGlAAJBsTEb5cuJVKqRfyj1EdxMAQADAgADbQADKwQ"));
>>>>>>> ad44a3695b2efa2b45dda25c1af327ce05d38d0b
        photo.setChatId(chatId);
        photo.setParseMode("html");
        photo.setCaption("<b>Exam Announcement !!!</b>\n\n"
                + teacher + " has called an exam<b> " + examName + "\n\n"
                + "</b>Exam Name " + "- " + examName + "\n"
                + "Subjet " + "- " + subject + "\n"
                + "Grade " + "- " + grade + "\n"
                + "Exam Date " + "- <b>" + examDate + "\n"
                + "</b>Time " + "- " + "TIme\n\n"
                + "<code>Don’t tell me you haven’t studied anything because you have. Anyway, wish you good luck for your exam!!!</code>  "
        );
        sendPhoto();

    }

    public void newExamAddedGroup() {
        // Administrator (pkg) -> Main -> Exams (tab) -> Add (button)
        String groupId = Administrator.Main.tGroupId.getText();
        String grade = Administrator.Main.cr1.getSelectedItem().toString();
        String subject = Administrator.Main.cr2.getSelectedItem().toString();
        String teacher = Administrator.Main.cr3.getSelectedItem().toString();
        String day = Administrator.Main.cr4.getSelectedItem().toString();
        String examName = Administrator.Main.jTextField22.getText();
        String examDate = Administrator.Main.jTextField27.getText();
        // message body start

        photo.setPhoto(new InputFile("AgACAgUAAx0CaCw0FAADJWOQu1s0v-FOwfh8rNTKhv2xUsGlAAJBsTEb5cuJVKqRfyj1EdxMAQADAgADbQADKwQ"));
        photo.setChatId(groupId);
        photo.setCaption("Dear Student " + teacher + " has called an exam\n "
                + examName + "this  will be held on <b>" + examDate + "</b>\n\n"
                + "Don’t tell me you haven’t studied anything because you have. Anyway, wish you good luck for your exam!!! "
        );
//        sendPhoto();
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
