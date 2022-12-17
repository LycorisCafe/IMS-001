/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
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
            if (Moderator.NewStudent.telegram != null) {
                if (update.getMessage().getText().equals(Moderator.NewStudent.telegram.getText())
                        && update.getMessage().getChat().getType().equals("private")) {
                    Moderator.NewStudent.telegramId.setText(update.getMessage().getFrom().getId().toString());
                    Moderator.NewStudent.jLabel8.setText("Success!");
                    Moderator.NewStudent.jButton6.setEnabled(true);
                }
            }

            if (Administrator.Main.fakeNumber != null && !Administrator.Main.fakeNumber.getText().equals("")) {
                if (update.getMessage().getText().equals(Administrator.Main.fakeNumber.getText())
                        && update.getMessage().getChat().getType().equals(Administrator.Main.type.getText())) {
                    switch (Administrator.Main.returnMethod.getText()) {
                        case "teacherAdd" -> {
                            Administrator.Main.telegramId.setText(update.getMessage().getFrom().getId().toString());
                            Administrator.Main.success.setText("teacherAdd");
                            Administrator.Main.jLabel67.setText("");
                            Administrator.Main.jLabel66.setIcon(null);
                        }
                        case "teacherUpdate" -> {
                            Administrator.Main.telegramId.setText(
                                    update.getMessage().getFrom().getId().toString());
                            Administrator.Main.success.setText("teacherUpdate");
                            Administrator.Main.jLabel67.setText("");
                            Administrator.Main.jLabel66.setIcon(null);
                        }
                        case "groupAdd" -> {
                            Administrator.Main.telegramId.setText(
                                    update.getMessage().getChat().getId().toString());
                            Administrator.Main.success.setText("groupAdd");
                            Administrator.Main.jLabel72.setText("");
                            Administrator.Main.jLabel71.setIcon(null);
                        }
                        case "studentUpdate" -> {
                            Administrator.Main.telegramId.setText(
                                    update.getMessage().getFrom().getId().toString());
                            Administrator.Main.success.setText("studentUpdate");
                            Administrator.Main.tupdatelabel.setText("");
                            Administrator.Main.jLabel65.setIcon(null);
                        }
                        case "groupTIdUpdate" -> {
                            Administrator.Main.telegramId.setText(
                                    update.getMessage().getChat().getId().toString());
                            Administrator.Main.success.setText("groupTIdUpdate");
                            Administrator.Main.jLabel68.setText("");
                            Administrator.Main.jLabel69.setIcon(null);
                        }
                    }
                }
            }

        }
        if (Maintainer.Main.jTextArea1 != null) {
            String user;
            if (update.getMessage().getFrom().getUserName() == null) {
                user = update.getMessage().getFrom().getFirstName();
            } else {
                user = update.getMessage().getFrom().getUserName();
            }
            Maintainer.Main.jTextArea1.append(update.getMessage().getFrom().getId().toString()
                    + " " + "@" + user + " : " + update.getMessage().getText() + "\n");
        }

    }
}
