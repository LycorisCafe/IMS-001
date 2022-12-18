/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;
import javax.swing.JOptionPane;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author Lycoris Cafe
 */
public class AutomatedMessages {

    TelegramBot bot;
    String instituteName;

    public AutomatedMessages() {
        instituteName = Helper.MainDetails.instituteName();
        bot = new TelegramBot();
    }

    // message designs ===============>>>>>>>>>>
    public void upToDateMessage() {
        String installedTime = null;
        String installedPC = null;
        try ( Stream<String> lines = Files.lines(
                Paths.get("C:\\ProgramData\\LycorisCafe\\IMS\\Temp\\newVersion.lc"))) {
            installedTime = lines.skip(0).findFirst().get();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        try ( Stream<String> lines = Files.lines(
                Paths.get("C:\\ProgramData\\LycorisCafe\\IMS\\Temp\\newVersion.lc"))) {
            installedPC = lines.skip(1).findFirst().get();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        SendMessage message = new SendMessage();
        message.setText("New version installed!\n\n"
                + "PC Name : " + installedPC + "\n"
                + "Time : " + installedTime);
        for (int i = 0; i > 1; i++) {
            if (i == 0) {
                message.setChatId(MainDetails.devChatId());
            } else {
                message.setChatId(MainDetails.adminChatId());
            }
            try {
                bot.execute(message);
            } catch (TelegramApiException e) {
                System.out.println(e);
            }
        }
    }

    public void studentRegistrationSuccess() {
        // @ Moderator.NewStudent jButton8 ActionPerformed
        // Send welcome message to each reggistered student
        SendMessage message = new SendMessage();
        SendPhoto photo = new SendPhoto();
        String chatId = Moderator.NewStudent.telegramId.getText();
        String studentId = Moderator.NewStudent.tSendStudentId.getText();
        String studentName = Moderator.NewStudent.tSendStudentName.getText();
        String logo = "AgACAgUAAx0CaCw0FAADNmOYwc-PNveRzCO8zv_aiRPWQQJMAAKMtDEbfIXJVKJWQtEZKVQ6AQADAgADbQADLAQ";
        photo.setChatId(chatId);
        photo.setPhoto(new InputFile(logo));
        photo.setCaption("Student Registration Success!\n\n"
                + "Welcome to " + instituteName + " Institute\n"
                + "Student ID : " + instituteName + "-STUDENT-" + studentId + "\n"
                + "Student Name : " + studentName);
        photo.setProtectContent(true);
        try {
            bot.execute(photo);
        } catch (TelegramApiException ex) {
            System.out.println(ex);
        }
        int rowcount = Moderator.NewStudent.jTable1.getRowCount();
        for (int y = 0; y < rowcount; y++) {
            String grade = (String) Moderator.NewStudent.jTable1.getValueAt(y, 1);
            String subject = (String) Moderator.NewStudent.jTable1.getValueAt(y, 2);
            String teacher = (String) Moderator.NewStudent.jTable1.getValueAt(y, 3);
            String day = (String) Moderator.NewStudent.jTable1.getValueAt(y, 4);
            String time = (String) Moderator.NewStudent.jTable1.getValueAt(y, 5);
            String duration = (String) Moderator.NewStudent.jTable1.getValueAt(y, 6);
            String payment = (String) Moderator.NewStudent.jTable1.getValueAt(y, 7);
            message.setChatId(chatId);
            message.setText("Hello, " + studentName + "\n\n"
                    + "You have successfully enrolled to :\n"
                    + "Class : " + grade + " - " + subject + "\n"
                    + "Teacher : " + teacher + "\n"
                    + "Schedule : " + day + " @ " + time + "\n"
                    + "Duration : " + duration + "\n"
                    + "Payment : " + payment
            );
            try {
                bot.execute(message);
            } catch (TelegramApiException ex) {
                System.out.println(ex);
            }
        }
    }

    public void attendanceMarking() {
        // Moderator (pkg) -> Main -> Mark Ateendance (button)
        SendMessage message = new SendMessage();
        String chatId = Moderator.Main.tId.getText();
        String studentId = instituteName + "-STUDENT-" + Moderator.Main.studentIdLabel.getText();
        String studentName = Moderator.Main.jTextField3.getText();
        String atendClass = Moderator.Main.jComboBox3.getSelectedItem().toString();
        String paymentDetails = Moderator.Main.jTextField5.getText();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        message.setChatId(chatId);
        message.setText("Student Attendance Marked!! \n\n"
                + "Studnt Id : " + studentId + "\n"
                + "Student Name : " + studentName + "\n"
                + "Attend Class : " + atendClass + "\n"
                + "Payments : " + paymentDetails + "\n"
                + "Time : " + time
        );
        try {
            bot.execute(message);
        } catch (TelegramApiException ex) {
            System.out.println(ex);
        }
    }

    public void newExamAddedStudent() {
        // Administrator (pkg) -> Main -> Exams (tab) -> Add (button)
        SendPhoto photo = new SendPhoto();
        String chatId = Administrator.Main.telegramId.getText();
        String grade = Administrator.Main.cr1.getSelectedItem().toString();
        String subject = Administrator.Main.cr2.getSelectedItem().toString();
        String teacher = Administrator.Main.cr3.getSelectedItem().toString();
        String day = Administrator.Main.cr4.getSelectedItem().toString();
        String examName = Administrator.Main.jTextField22.getText();
        String examDate = Administrator.Main.jTextField27.getText();
        String examTime = Administrator.Main.jTextField32.getText() + " "
                + Administrator.Main.jComboBox15.getSelectedItem().toString();
        String examDuration = Administrator.Main.jTextField39.getText() + "(h) " + Administrator.Main.jTextField40.getText() + "(min)";
        String image = "AgACAgUAAx0CaCw0FAADNWOYwc7TRCQ_8h-RXfMRHIBxBQ2yAAKLtDEbfIXJVOT-bHCX6s5UAQADAgADbQADLAQ";

        photo.setPhoto(new InputFile(image));
        System.out.println(grade + " " + subject + " " + teacher + " " + day);
        photo.setChatId(chatId);
        photo.setParseMode("html");
        photo.setCaption("<b>Exam Announcement !!!</b>\n\n"
                + teacher + " has called an exam<b> " + "\n\n"
                + "</b>Exam Name " + "- " + examName + "\n"
                + "Class " + "- " + grade + " " + subject + "\n"
                + "Exam Date " + "- <b>" + examDate + "\n"
                + "</b>Time " + "- " + examTime + "\n\n"
                + "<code>Don’t tell me you haven’t studied anything because you have. "
                + "Anyway, wish you good luck for your exam!!!</code>  "
        );
        try {
            bot.execute(photo);
        } catch (TelegramApiException ex) {
            System.out.println(ex);
        }
    }

    public void newExamAddedGroup() {
        // Administrator (pkg) -> Main -> Exams (tab) -> Add (button)
        SendPhoto photo = new SendPhoto();
        String groupId = Administrator.Main.tGroupId.getText();
        String grade = Administrator.Main.cr1.getSelectedItem().toString();
        String subject = Administrator.Main.cr2.getSelectedItem().toString();
        String teacher = Administrator.Main.cr3.getSelectedItem().toString();
        String day = Administrator.Main.cr4.getSelectedItem().toString();
        String examName = Administrator.Main.jTextField22.getText();
        String examDate = Administrator.Main.jTextField27.getText();
        String examTime = Administrator.Main.jTextField32.getText() + " "
                + Administrator.Main.jComboBox15.getSelectedItem().toString();
        String examDuration = Administrator.Main.jTextField39.getText() + "(h) " + Administrator.Main.jTextField40.getText() + "(min)";
        String image = "AgACAgUAAx0CaCw0FAADNWOYwc7TRCQ_8h-RXfMRHIBxBQ2yAAKLtDEbfIXJVOT-bHCX6s5UAQADAgADbQADLAQ";
        // message body start

        photo.setPhoto(new InputFile(image));
        System.out.println(grade + " " + subject + " " + teacher + " " + day);
        photo.setChatId(groupId);
        photo.setParseMode("html");
        photo.setCaption("<b>Exam Announcement !!!</b>\n\n"
                + teacher + " has called an exam<b> " + "\n\n"
                + "</b>Exam Name " + "- " + examName + "\n"
                + "Class " + "- " + grade + " " + subject + "\n"
                + "Exam Date " + "- <b>" + examDate + "\n"
                + "</b>Time " + "- " + examTime + "\n\n"
                + "Don’t tell me you haven’t studied anything because you have. "
                + "Anyway, wish you good luck for your exam!!!\n "
        );
        try {
            bot.execute(photo);
        } catch (TelegramApiException ex) {
            System.out.println(ex);
        }
    }

    public void examResultPush() {
        SendPhoto photo = new SendPhoto();
        String telegramId = Administrator.ExamResults.telegramId.getText();
        String studentName = Administrator.ExamResults.studentName.getText();
        String className = Administrator.ExamResults.className.getText();
        String teacherName = Administrator.ExamResults.teacherName.getText();
        String examName = Administrator.ExamResults.examName.getText();
        String examDate = Administrator.ExamResults.examDate.getText();
        String marks = Administrator.ExamResults.marks.getText();
        String rank = Administrator.ExamResults.rank.getText();
        String image = "AgACAgUAAx0CaCw0FAADQWOZmqVGir6LH9r5om728fZ0Tu7lAALttTEbfIXRVE9FAabdfWMOAQADAgADbQADLAQ";
        photo.setChatId(telegramId);
        photo.setPhoto(new InputFile(image));
        photo.setCaption("Exam Results Released!\n\n"
                + "Student Name : " + studentName + "\n"
                + "Class : " + className + "\n"
                + "Teacher : " + teacherName + "\n\n"
                + "Exam Name : " + examName + "\n"
                + "Exam Date : " + examDate + "\n\n"
                + "Marks : " + marks + "\n"
                + "Grade : " + rank);
        photo.setProtectContent(true);
        try {
            bot.execute(photo);
        } catch (TelegramApiException ex) {
            System.out.println(ex);
        }
    }

    public void paymentSuccess() {
        SendPhoto photo = new SendPhoto();
        String telegramId = Moderator.PayNowGate.telegramId.getText();
        String studentId = instituteName + "-STUDENT-" + Moderator.PayNowGate.jTextField1.getText();
        String studentName = Moderator.PayNowGate.studentName.getText();
        String className = Moderator.PayNowGate.className.getText();
        String classTeacher = Moderator.PayNowGate.classTeacher.getText();
        String classDay = Moderator.PayNowGate.classDay.getText();
        String paymentId = Moderator.PayNowGate.paymentId.getText();
        String paymentTarget = Moderator.PayNowGate.paymentDay.getText();
        String paymentValue = Moderator.PayNowGate.paymentValue.getText();
        String slipTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String image = "AgACAgUAAx0CaCw0FAADPmOZMlj_Abh9EZFQkVVd4ynn2LD2AALUtDEbfIXJVInjFD6XGb0dAQADAgADbQADLAQ";
        photo.setPhoto(new InputFile(image));
        photo.setChatId(telegramId);
        photo.setParseMode("html");
        photo.setProtectContent(Boolean.TRUE);
        photo.setCaption("Dear " + studentName + ",\n"
                + "We Confirmed that we got your Payment!!!\n\n"
                + "Student ID" + ": " + studentId + "\n"
                + "Class Name " + ": " + className + "\n"
                + "Teacher " + ": " + classTeacher + "\n"
                + "Class Day " + ": " + classDay + "\n\n"
                + "Payment Slip Number " + ": " + paymentId + "\n"
                + "Payment " + ": " + paymentValue + "\n"
                + "Payment Month " + ": " + paymentTarget + "\n"
                + "Slip Time " + ": " + slipTime + "\n\n"
                + "<b>Thank you very much for trusting us!</b>"
        );
        try {
            bot.execute(photo);
        } catch (TelegramApiException ex) {
            System.out.println(ex);
        }
    }

    public void broadast() {
        // broadast
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int rowCount = Administrator.TelegramReports.jTable1.getRowCount();
                int i = 0;
                while (i < rowCount) {
                    String telegramId = Administrator.TelegramReports.jTable1.getValueAt(i, 0).toString();
                    String sName = Administrator.TelegramReports.jTable1.getValueAt(i, 1).toString();
                    int successCount = Integer.parseInt(Administrator.TelegramReports.jLabel3.getText());
                    int unsuccessCount = Integer.parseInt(Administrator.TelegramReports.jLabel4.getText());
                    SendMessage msg = new SendMessage();
                    msg.setChatId(telegramId);
                    msg.setText(Administrator.Main.broadcastMessage.getText());
                    try {
                        bot.execute(msg);
                        Administrator.TelegramReports.jTextArea1.append("Message sent success to : " + sName + "\n");
                        successCount = successCount + 1;
                        Administrator.TelegramReports.jLabel3.setText("" + successCount);
                    } catch (TelegramApiException e) {
                        System.out.println(e);
                        Administrator.TelegramReports.jTextArea1.append("Message sent unsuccess to : " + sName + "\n");
                        unsuccessCount = unsuccessCount + 1;
                        Administrator.TelegramReports.jLabel4.setText("" + unsuccessCount);
                    }
                    i++;
                }
                Administrator.TelegramReports.save.setText("0");
                Administrator.TelegramReports report = new Administrator.TelegramReports();
                JOptionPane.showMessageDialog(report, "Success!");
            }
        });
        t2.start();
    }

    public void classDetailsUpdatedGroup() {
        String[] parts = Administrator.Main.longDetails.getText().split("@");
        String telegramId = Administrator.Main.telegramId.getText();
        String classn = parts[0];
        String teacher = parts[1];
        String day = parts[2];
        String payment = parts[3];
        String time = parts[4];
        String duration = parts[5];
    }

    public void classDetailsUpdatedStudent() {
        String[] parts = Administrator.Main.longDetails.getText().split("@");
        String telegramId = Administrator.Main.telegramId.getText();
        String classn = parts[0];
        String teacher = parts[1];
        String day = parts[2];
        String payment = parts[3];
        String time = parts[4];
        String duration = parts[5];
    }

}
