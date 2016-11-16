
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import dbAdministrator.prenotazione;
import java.io.ByteArrayOutputStream;
import java.util.List;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 *  @
 * @author Lele
 */


public class Createpdf {
    /**
     * 
     * @param bList una lista di stringe ognuna contenente il testo da converire in immagine qr
     * @return ritorna il puntatore ad un ByteArray che è il documento Pdf
     * @throws Exception 
     */
    static public ByteArrayOutputStream pdf(List<prenotazione> bList) throws Exception{
        Document document = new Document();
        ByteArrayOutputStream pdfFile = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, pdfFile);
        document.open();
        
        //scrivo il testo
        document.add(new Paragraph("Stampa questo documento e presentalo alla cassa quando ritirerai i biglietti"));
        
        //scrivo le immagini
        for(prenotazione s :bList){
            
            ByteArrayOutputStream out = QRCode.from(s.getFilm()+s.getPosto()+s.getSala()+s.getTipoBiglietto()+s.getTipoFilm()+s.getDataPrenotazione().toString()+s.getDataProiezione().toString()+Integer.toString(s.getPrenotazione()))
                .to(ImageType.JPG).withSize(300, 300).stream();
            
            Image qrimg = Image.getInstance(out.toByteArray());
            document.add(qrimg);  
        }
        document.close(); 
        return pdfFile;
    }
}
