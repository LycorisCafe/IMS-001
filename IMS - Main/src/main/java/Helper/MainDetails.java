/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 *
 * @author Lycoris Cafe
 */
public class MainDetails {

    public static String projectSerial() {
        // ProjectName-PrijectId
        String projectSerial = "IMS-001";
        return projectSerial;
    }

    public static String version() {
        String version = "0.1";
        return version;
    }

    public static String instituteName() {
        String instituteName = "Dekma";
        return instituteName;
    }

    public static String iconPath() {
        String iconPath = "/Media/WelcomeLogo.png";
        return iconPath;
    }

    public static String cpuId() {
        String cpuId = null;
        return cpuId;
    }

    public static String baseBordId() {
        String baseBordId = null;
        return baseBordId;
    }

    public static String botUsername() {
        String botID = null;
        try ( Stream<String> lines = Files.lines(Paths.get(
                "C:\\ProgramData\\LycorisCafe\\IMS\\telegram.lc"))) {
            botID = lines.skip(1).findFirst().get();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return botID;
    }

    public static String botAPI() {
        String botToken = null;
        try ( Stream<String> lines = Files.lines(Paths.get(
                "C:\\ProgramData\\LycorisCafe\\IMS\\telegram.lc"))) {
            botToken = lines.skip(0).findFirst().get();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return botToken;
    }

    public static String adminChatId() {
        String adminChatId = null;
        try ( Stream<String> lines = Files.lines(Paths.get(
                "C:\\ProgramData\\LycorisCafe\\IMS\\telegram.lc"))) {
            adminChatId = lines.skip(2).findFirst().get();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return adminChatId;
    }

    public static String devChatId() {
        String devGroupId = null;
        return devGroupId;
    }
}
