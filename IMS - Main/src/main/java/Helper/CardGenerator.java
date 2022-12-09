/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import javax.imageio.ImageIO;

/**
 *
 * @author Anupama
 */
public class CardGenerator {

    String studentName = Moderator.NewStudent.tSendStudentName.getText();
    int studentX;

    public void cardGenerator() {
        String defFileLocation = null;
        String tempStudentPic = "C:\\ProgramData\\LycorisCafe\\IMS\\Temp\\TempStudent.png";
        String tempQr = "C:\\ProgramData\\LycorisCafe\\IMS\\Temp\\TempQR.png";
        String path = "C:\\ProgramData\\LycorisCafe\\IMS\\";
        String institute = Helper.MainDetails.instituteName();
        String studentId = Moderator.NewStudent.tSendStudentId.getText();

        studentLength();

        try ( Stream<String> lines = Files.lines(
                Paths.get("C:\\ProgramData\\LycorisCafe\\IMS\\files.lc"))) {
            defFileLocation = lines.skip(0).findFirst().get();
        } catch (IOException ex) {
            System.out.println(ex);
        }

        try {
            File file = new File(tempStudentPic);
            BufferedImage bufferedImage = ImageIO.read(file);
            int width = bufferedImage.getWidth();
            BufferedImage circleBuffer = new BufferedImage(width, width, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = circleBuffer.createGraphics();
            g2.setClip(new Ellipse2D.Float(0, 0, width, width));
            g2.drawImage(bufferedImage, 0, 0, width, width, null);
            ImageIO.write(circleBuffer, "PNG", file);
        } catch (IOException e) {
            System.out.println(e);
        }

        try {
            Document doc = new Document(PageSize.B8);
            PdfWriter writer = PdfWriter.getInstance(doc,
                    new FileOutputStream(defFileLocation + "\\" + studentId + ".pdf"));

            doc.open();
            FixText(studentName, studentX, 60, writer, 13);
            FixText(institute + "-STUDENT-" + studentId, 55, 23, writer, 6);
            PdfContentByte canvas = writer.getDirectContentUnder();
            Image frontPage = Image.getInstance(path + "frontPage.png");
            frontPage.scaleAbsolute(PageSize.B8);
            frontPage.setAbsolutePosition(0, 0);
            canvas.addImage(frontPage);

            PdfContentByte canvas1 = writer.getDirectContentUnder();
            Image studentPic = Image.getInstance(tempStudentPic);
            studentPic.setAbsolutePosition(44, 94);
            float scaler1 = ((doc.getPageSize().getWidth() - doc.leftMargin()
                    - doc.rightMargin()) / studentPic.getWidth()) * 85;
            studentPic.scalePercent(scaler1);
            canvas1.addImage(studentPic);

            doc.newPage();
            PdfContentByte canvas2 = writer.getDirectContentUnder();
            Image backPage = Image.getInstance(path + "backPage.png");
            backPage.scaleAbsolute(PageSize.B8);
            backPage.setAbsolutePosition(0, 0);
            canvas2.addImage(backPage);

            PdfContentByte canvas3 = writer.getDirectContentUnder();
            Image qr = Image.getInstance(tempQr);
            qr.setAbsolutePosition(20, 55);
            float scaler = ((doc.getPageSize().getWidth() - doc.leftMargin()
                    - doc.rightMargin()) / qr.getWidth()) * 130;
            qr.scalePercent(scaler);
            canvas3.addImage(qr);
            doc.close();
        } catch (DocumentException | IOException e) {
            System.out.println(e);
        }
    }

    private static void FixText(String text, int x, int y, PdfWriter writer, int size) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,
                    BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            cb.saveState();
            cb.beginText();
            cb.moveText(x, y);
            cb.setFontAndSize(bf, size);
            cb.showText(text);
            cb.endText();
            cb.restoreState();
        } catch (DocumentException | IOException e) {
            System.out.println(e);
        }
    }

    private void studentLength() {
        int length = studentName.length();
        if (length > 24) {
            String[] parts = studentName.split(" ");
            studentName = parts[0];
            studentLength();
        } else {
            switch (length) {
                case 5:
                    studentX = 70;
                    break;
                case 6:
                    studentX = 66;
                    break;
                case 7:
                    studentX = 62;
                    break;
                case 8:
                    studentX = 59;
                    break;
                case 9:
                    studentX = 55;
                    break;
                case 10:
                    studentX = 51;
                    break;
                case 11:
                    studentX = 48;
                    break;
                case 12:
                    studentX = 44;
                    break;
                case 13:
                    studentX = 41;
                    break;
                case 14:
                    studentX = 37;
                    break;
                case 15:
                    studentX = 34;
                    break;
                case 16:
                    studentX = 30;
                    break;
                case 17:
                    studentX = 26;
                    break;
                case 18:
                    studentX = 23;
                    break;
                case 19:
                    studentX = 19;
                    break;
                case 20:
                    studentX = 16;
                    break;
                case 21:
                    studentX = 12;
                    break;
                case 22:
                    studentX = 9;
                    break;
                case 23:
                    studentX = 5;
                    break;
            }
        }
    }
}
