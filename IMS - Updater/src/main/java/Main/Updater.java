/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import javax.swing.JOptionPane;

/**
 *
 * @author Anupama
 */
public class Updater {

    public void update() {
        String path = "C:\\ProgramData\\LycorisCafe\\IMS\\Temp\\appPath.lc";
        String installedLocation = null;
        String count = null;
        try ( Stream<String> lines = Files.lines(Paths.get(path))) {
            installedLocation = lines.skip(0).findFirst().get();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        try ( Stream<String> lines = Files.lines(Paths.get(path))) {
            count = lines.skip(1).findFirst().get();
        } catch (IOException ex) {
            System.out.println(ex);
        }

        // previous version delete
        File app = new File(installedLocation + "\\IMS.exe");
        app.delete();

        // move .rar files to installatiuon directory
        int fileCount = Integer.parseInt(count);
        for (int x = 1; x > fileCount; x++) {
            File file = new File("C:\\ProgramData\\LycorisCafe\\IMS\\Temp\\" + x + ".rar");
            file.renameTo(new File(installedLocation + "\\" + x + ".rar"));
        }
        
        // move unrar.exe to installation directory
        File unrar = new File("C:\\ProgramData\\LycorisCafe\\IMS\\Temp\\unrar.exe");
        unrar.renameTo(new File(installedLocation + "\\unrar.exe"));
        
        // decompressing and installing new version
        try {
            ProcessBuilder processBuilder
                    = new ProcessBuilder(installedLocation + "\\unrar.exe", "x",
                            installedLocation + "\\1.rar");
            processBuilder.redirectErrorStream(true);
            processBuilder.start();
        } catch (IOException e) {
            System.out.println(e);
        }
        
        // delete leftovers
        File text = new File("C:\\ProgramData\\LycorisCafe\\IMS\\Temp\\appPath.lc");
        unrar.delete();
        text.delete();
        
        
    }
}
