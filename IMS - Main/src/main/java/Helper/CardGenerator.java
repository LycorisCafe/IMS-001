/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.AreaBreakType;
import static com.itextpdf.layout.properties.Property.FONT;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import javax.imageio.ImageIO;

/**
 *
 * @author Anupama
 */
public class CardGenerator {

    public void cardGenerator() {
        String defFileLocation = null;
        String studentName = Moderator.NewStudent.tSendStudentName.getText();
        String tempStudentPic = "C:\\ProgramData\\LycorisCafe\\IMS\\Temp\\TempStudent.png";
        String tempStudentPicR = "C:\\ProgramData\\LycorisCafe\\IMS\\Temp\\TempStudentR.png";
        String tempQr = "C:\\ProgramData\\LycorisCafe\\IMS\\Temp\\TempQR.png";
        String path = "C:\\ProgramData\\LycorisCafe\\IMS\\";
        String institute = Helper.MainDetails.instituteName();
        String studentId = Moderator.NewStudent.tSendStudentId.getText();

        try ( Stream<String> lines = Files.lines(
                Paths.get("C:\\ProgramData\\LycorisCafe\\IMS\\files.lc"))) {
            defFileLocation = lines.skip(0).findFirst().get();
        } catch (IOException ex) {
            System.out.println(ex);
        }

        try {
            File file = new File(tempStudentPic);
            File file1 = new File(tempStudentPicR);
            BufferedImage bufferedImage = ImageIO.read(file);
            int width = bufferedImage.getWidth();
            BufferedImage circleBuffer = new BufferedImage(width, width, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = circleBuffer.createGraphics();
            g2.setClip(new Ellipse2D.Float(0, 0, width, width));
            g2.drawImage(bufferedImage, 0, 0, width, width, null);
            ImageIO.write(circleBuffer, "PNG", file1);
        } catch (IOException e) {
            System.out.println(e);
        }

        try {
            PdfDocument pdfDoc = new PdfDocument(
                    new PdfWriter(defFileLocation + "\\" + studentId + ".pdf"));
            PageSize pageSize = PageSize.B8;
            Document doc = new Document(pdfDoc, pageSize);
            doc.setMargins(0, 0, 0, 0);

            PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
            canvas.addImageFittedIntoRectangle(ImageDataFactory.create(
                    path + "frontPage.png"),
                    pageSize, false);
            Table table = new Table(1);
            Cell c0 = new Cell()
                    .setWidth(170)
                    .setBorder(Border.NO_BORDER)
                    .add(new Paragraph("\n\n\n\n\n\n\n\n\n\n\n"))
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.addCell(c0);
            Cell c1 = new Cell()
                    .setWidth(170)
                    .setBorder(Border.NO_BORDER)
                    .add(new Paragraph(studentName))
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.addCell(c1);
            Cell c2 = new Cell()
                    .setWidth(170)
                    .setBorder(Border.NO_BORDER)
                    .add(new Paragraph("\n\n\n\n"))
                    .setFontSize(4)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.addCell(c2);
            Cell c3 = new Cell()
                    .setWidth(170)
                    .setBorder(Border.NO_BORDER)
                    .add(new Paragraph(institute + "-STUDENT-" + studentId))
                    .setFontSize(8)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.addCell(c3);

            doc.add(table);

            ImageData image = ImageDataFactory.create(tempStudentPicR);
            Image img = new Image(image);
            img.scaleAbsolute(88, 88);
            img.setFixedPosition(44, 94);
            doc.add(img);

            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            PdfCanvas canvas1 = new PdfCanvas(doc.getPdfDocument().getPage(2));
            canvas1.addImageFittedIntoRectangle(ImageDataFactory.create(
                    path + "backPage.png"),
                    pageSize, false);
            ImageData image1 = ImageDataFactory.create(tempQr);
            Image img1 = new Image(image1);
            img1.scaleAbsolute(135, 135);
            img1.setFixedPosition(20, 54);
            doc.add(img1);
            doc.close();
        } catch (FileNotFoundException | MalformedURLException e) {
            System.out.println(e);
        }
    }
}
