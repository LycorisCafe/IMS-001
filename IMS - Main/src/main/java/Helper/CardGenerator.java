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
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author Anupama
 */
public class CardGenerator {

    public void cardGenerator() {
        String defFileLocation = null;
        String tempStudentPic = "C:\\ProgramData\\LycorisCafe\\IMS\\Temp\\TempStudent.png";
        String tempQr = "C:\\ProgramData\\LycorisCafe\\IMS\\Temp\\TempQR.png";
        String path = "C:\\ProgramData\\LycorisCafe\\IMS\\";
        String institute = Helper.MainDetails.instituteName();
        String studentId = Moderator.NewStudent.tSendStudentId.getText();
        String studentName = Moderator.NewStudent.tSendStudentName.getText();

        // if for student name length
        
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
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(defFileLocation + "\\.pdf"));
            doc.open();
            FixText(studentName, 30, 60, writer, 13);
            FixText(institute + "-STUDENT-" + studentId, 55, 23, writer, 6);
            PdfContentByte canvas = writer.getDirectContentUnder();
            Image image = Image.getInstance(path + "frontPage.png");
            image.scaleAbsolute(PageSize.B8);
            image.setAbsolutePosition(0, 0);
            canvas.addImage(image);
            PdfContentByte canvas3 = writer.getDirectContentUnder();
            Image image3 = Image.getInstance(tempStudentPic);
            image3.setAbsolutePosition(44, 94);
            float scaler1 = ((doc.getPageSize().getWidth() - doc.leftMargin()
                    - doc.rightMargin()) / image3.getWidth()) * 85;
            image3.scalePercent(scaler1);
            canvas3.addImage(image3);
            doc.newPage();
            PdfContentByte canvas1 = writer.getDirectContentUnder();
            Image image1 = Image.getInstance(path + "backPage.png");
            image1.scaleAbsolute(PageSize.B8);
            image1.setAbsolutePosition(0, 0);
            canvas1.addImage(image1);
            PdfContentByte canvas2 = writer.getDirectContentUnder();
            Image image2 = Image.getInstance(tempQr);
            image2.setAbsolutePosition(20, 55);
            float scaler = ((doc.getPageSize().getWidth() - doc.leftMargin()
                    - doc.rightMargin()) / image2.getWidth()) * 130;
            image2.scalePercent(scaler);
            canvas2.addImage(image2);
            doc.close();
        } catch (DocumentException | IOException e) {
            System.out.println(e);
        }
    }

    private static void FixText(String text, int x, int y, PdfWriter writer, int size) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
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

}
