/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.meta.api.methods.groupadministration.ApproveChatJoinRequest;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 *
 * @author Lycoris Cafe
 */
public class TelegramBot extends TelegramLongPollingBot {

    @Override
    public String getBotToken() {
        return MainDetails.botAPI();
    }

    @Override
    public String getBotUsername() {
        return MainDetails.botUsername();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            if (Moderator.NewStudent.jTextField5 != null) {
                if (update.getMessage().getText().equals(Moderator.NewStudent.jTextField5.getText())
                        && update.getMessage().getChat().getType().equals("private")) {
                    Moderator.NewStudent.telegramId.setText(update.getMessage().getFrom().getId().toString());
                    Moderator.NewStudent.jLabel8.setText("Success!");
                    Moderator.NewStudent.jButton6.setEnabled(true);
                }
            }

            if (Administrator.TelegramVerify.jTextField1 != null && !Administrator.TelegramVerify.jTextField1.getText().equals("---")) {
                if (update.getMessage().getText().equals(Administrator.TelegramVerify.jTextField1.getText())
                        && update.getMessage().getChat().getType().equals(Administrator.AMain.type.getText())) {
                    Administrator.TelegramVerify.disposeText.setText("0");
                    switch (Administrator.AMain.returnMethod.getText()) {
                        case "teacherAdd" -> {
                            Administrator.AMain.telegramId.setText(update.getMessage().getFrom().getId().toString());
                            Administrator.AMain.success.setText("teacherAdd");
                        }
                        case "teacherUpdate" -> {
                            Administrator.AMain.telegramId.setText(
                                    update.getMessage().getFrom().getId().toString());
                            Administrator.AMain.success.setText("teacherUpdate");
                        }
                        case "groupAdd" -> {
                            Administrator.AMain.telegramId.setText(
                                    update.getMessage().getChat().getId().toString());
                            Administrator.AMain.success.setText("groupAdd");
                        }
                        case "studentUpdate" -> {
                            Administrator.AMain.telegramId.setText(
                                    update.getMessage().getFrom().getId().toString());
                            Administrator.AMain.success.setText("studentUpdate");
                        }
                        case "groupTIdUpdate" -> {
                            Administrator.AMain.telegramId.setText(
                                    update.getMessage().getChat().getId().toString());
                            Administrator.AMain.success.setText("groupTIdUpdate");
                        }
                        case "adminId" -> {
                            Administrator.AMain.jTextField51.setText(
                                    update.getMessage().getChat().getId().toString());
                        }
                    }
                }
            }

        }
        if (Administrator.AMain.jToggleButton1 != null && Administrator.AMain.jToggleButton1.isSelected()) {
            String user;
            if (update.getMessage().getFrom().getUserName() == null) {
                user = update.getMessage().getFrom().getFirstName();
            } else {
                user = update.getMessage().getFrom().getUserName();
            }
            Administrator.AMain.jTextArea4.append(update.getMessage().getFrom().getId().toString()
                    + " " + "@" + user + " : " + update.getMessage().getText() + "\n");
        }

//        if (update.hasChatJoinRequest()) {
//            long userId = update.getChatJoinRequest().getUser().getId();
//            String link = update.getChatJoinRequest().getInviteLink().getInviteLink();
//            String regClassId = update.getChatJoinRequest().getInviteLink().getName();
//            try {
//                Connection con = DB.connect();
//                Statement stmt = con.createStatement();
//                ResultSet rs = stmt.executeQuery("SELECT * "
//                        + "FROM regclass "
//                        + "WHERE id='" + regClassId + "'");
//                while (rs.next()) {
//                    String classId = rs.getString("classId");
//                    ResultSet rs2 = stmt.executeQuery("SELECT * "
//                            + "FROM students "
//                            + "WHERE id='" + rs.getString("studentId") + "'");
//                    while (rs2.next()) {
//                        long telegramIdUser = rs2.getLong("telegramId");
//                        ResultSet rs3 = stmt.executeQuery("SELECT * "
//                                + "FROM classes "
//                                + "WHERE id='" + classId + "'");
//                        while (rs3.next()) {
//                            if (userId == telegramIdUser) {
//                                ApproveChatJoinRequest reqApprove = new ApproveChatJoinRequest();
//                                reqApprove.setChatId(rs3.getString("telegramId"));
//                                reqApprove.setUserId(userId);
//                                
//                            }
//                        }
//                    }
//                }
//            } catch (SQLException e) {
//                System.out.println(e);
//            }
//        }
    }
}
