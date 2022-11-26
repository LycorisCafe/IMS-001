/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

/**
 *
 * @author Anupama
 */
public class FTP {

    public static String ftpUrl() {
        String url = null;
        String host = "127.0.0.1";
        String port = "21";
        String user = "root";
        String pass = "root";
        url = "ftp://" + user + ":" + pass + "@" + host + ":" + port;
        return url;
    }
}
