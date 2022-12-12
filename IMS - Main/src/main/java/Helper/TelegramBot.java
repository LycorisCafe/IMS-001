/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 *
 * @author Lycoris Cafe
 */
public class TelegramBot extends TelegramLongPollingBot {

    String botToken = null;
    String botID = null;
    String path = "C:\\ProgramData\\LycorisCafe\\IMS\\telegram.lc";
    File f = new File(path);

    private void Token() {
        if (f.exists()) {
            try ( Stream<String> lines = Files.lines(Paths.get(path))) {
                botToken = lines.skip(0).findFirst().get();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

    private void ID() {
        if (f.exists()) {
            try ( Stream<String> lines = Files.lines(Paths.get(path))) {
                botID = lines.skip(1).findFirst().get();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

    @Override
    public String getBotToken() {
        Token();
        return botToken;
    }

    @Override
    public String getBotUsername() {
        ID();
        return botID;
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
                    Administrator.Main.telegramId.setText(update.getMessage().getFrom().getId().toString());
                    Administrator.Main.success.setText("0");
                    switch (Administrator.Main.returnMethod.getText()) {
                        case "teacherAdd":
//                            teacherAdd();
                            break;
                        case "teacherUpdate":
//                            teacherUpdate();
                            break;
                        case "groupAdd":
//                            groupAdd();
                            break;
                        case "studentUpdate":
                            Administrator.Main.tupdatelabel.setText("");
                            Administrator.Main.jLabel65.setIcon(null);
                            break;
                        case "groupTIdUpdate":
//                            groupTIdUpdate();
                            break;
                    }
                }
            }

        }
        if (Maintainer.Main.jTextArea1 != null) {
            Maintainer.Main.jTextArea1.append(update.getMessage().getFrom().getId().toString() + " " + "@" + update.getMessage().getFrom().getUserName()
                    + " : " + update.getMessage().getText() + "\n");
        }

    }
}
