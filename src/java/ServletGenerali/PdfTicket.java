package ServletGenerali;



import java.io.ByteArrayOutputStream;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dbAdministrator.prenotazione;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lele
 */
public class PdfTicket {
    
   // public static void main(String[] args) {

      public ByteArrayOutputStream generapdf(ArrayList<prenotazione> lista){ 
         ByteArrayOutputStream result= new ByteArrayOutputStream();
        try {
            //salva l'immagine su file. devo pero sposatre il codice per farloandare
//           FileOutputStream fout = new FileOutputStream(new File(
//                    "C:\\Users\\Admin\\Desktop\\QR_Code.JPG"));
//           fout.write(out.toByteArray());
//            fout.flush();
//            fout.close();
            
            //crea il pdf
            Document document = new Document();
            document.setMargins(45, 45, 40, 40);
//            creo la posizione
            //File ciao = new File("C:\\Users\\Admin\\Desktop\\biglietto.pdf");
            //FileOutputStream pdfout =  new FileOutputStream(ciao);
            
            //PdfWriter.getInstance(document, pdfout);
            PdfWriter.getInstance(document, result);
            
            document.open();
            SimpleDateFormat fdata = new SimpleDateFormat("dd/MM/yy");
            SimpleDateFormat fora = new SimpleDateFormat("HH:mm");
            
            for(int p=0; p< lista.size(); p++){
                
           ByteArrayOutputStream out;
                
                out = QRCode.from(lista.get(p).getId_utente() + fdata.format(lista.get(p).getDataProiezione())+
                        fora.format(lista.get(p).getDataProiezione()) + lista.get(p).getSala()+ lista.get(p).getPosto() +
                        lista.get(p).getPrezzo() + lista.get(p).getTipoBiglietto()+lista.get(p).getTipoFilm()+ lista.get(p).getFilm()+"")
                .to(ImageType.JPG).withSize(300, 300).stream();
            Image qrimg = Image.getInstance(out.toByteArray());

            
             PdfPTable table = new PdfPTable(100);
             table.setWidthPercentage(100);
        // the cell object
            PdfPCell cell;
            qrimg.setWidthPercentage(100);
            cell = new PdfPCell();
            
            //creo sottotabella;
            PdfPTable qrtable = new PdfPTable(1);
            cell.addElement(qrimg);
            cell.setBorder(Rectangle.NO_BORDER);
            qrtable.addCell(cell);
            cell = new PdfPCell();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.addElement(new Paragraph("Cinema Multiplex",FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0, 0, 0))));
            cell.addElement(new Paragraph("Trento - Via Sommarive,9 38123",FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0, 0, 0))));
            cell.addElement(new Paragraph("Tel: 0000 00 0000 www.cinemamultiplex.it",FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0, 0, 0))));
            cell.setBorder(Rectangle.NO_BORDER);
            qrtable.addCell(cell);
            
            //seconda cella del biglietto
            cell = new PdfPCell();
            cell.addElement(qrtable);
            cell.setColspan(33);
            cell.setFixedHeight(180f);

            table.addCell(cell);
            // now we add a cell with rowspan 2
            cell = new PdfPCell(new Phrase(lista.get(p).getFilm(),FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(0, 0, 0))));
            cell.setColspan(3);
            cell.setPadding(0f);
            cell.setFixedHeight(180f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setRotation(90);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase( fdata.format(lista.get(p).getDataProiezione()) + 
                    " -  "+fora.format(lista.get(p).getDataProiezione())+ "  |  "  + lista.get(p).getPrezzo() + "  |  " + lista.get(p).getTipoBiglietto(),FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(0, 0, 0))));
            cell.setColspan(3);
            cell.setPadding(0f);
            cell.setFixedHeight(180f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setRotation(90);
            table.addCell(cell);
          
            
            cell = new PdfPCell(new Phrase("Sala: "  +lista.get(p).getSala() +"\t Posto: "+lista.get(p).getPosto()+" ",FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(0, 0, 0))));
            cell.setColspan(3);
            cell.setPadding(0f);
            cell.setRotation(90);
            cell.setFixedHeight(180f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            table.addCell(cell);
            
            //creo la tabella tabella
            PdfPTable tabella = new PdfPTable(3);
            tabella.setWidthPercentage(100);
            tabella.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            
            cell = new PdfPCell();
            cell.setMinimumHeight(4f);
            cell.setColspan(3);
            cell.setBorderWidthTop(0f);
            cell.setBorderWidthLeft(0f);
            cell.setBorderWidthRight(0f);
            cell.setBorderWidthBottom(0f);
            cell.addElement(new Phrase(lista.get(p).getFilm()));
            cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            tabella.addCell(cell);
            
            cell = new PdfPCell();
            cell.setColspan(2);
            cell.setBorderWidthLeft(0f);
            cell.setBorderWidthRight(0f);
            cell.setBorderWidthBottom(0f);
            cell.addElement(new Phrase(" Data: " +fdata.format(lista.get(p).getDataProiezione())));
            tabella.addCell(cell);
            
            cell = new PdfPCell();
            cell.setColspan(1);
            cell.setBorderWidthRight(0f);
            cell.setBorderWidthBottom(0f);
            cell.addElement(new Phrase(" Ora: "+fora.format(lista.get(p).getDataProiezione())));
            tabella.addCell(cell);
            
            cell = new PdfPCell();
            cell.setColspan(1);
            cell.setBorderWidthLeft(0f);
            cell.setBorderWidthBottom(0f);
            cell.addElement(new Phrase(" Sala: "+lista.get(p).getSala()));
            tabella.addCell(cell);
            
            cell = new PdfPCell();
            cell.setColspan(1);
            cell.setBorderWidthLeft(0f);
            cell.setBorderWidthRight(0f);
            cell.setBorderWidthBottom(0f);
            cell.addElement(new Phrase(" Fila: "+lista.get(p).getRiga()));
            tabella.addCell(cell);
            
            cell = new PdfPCell();
            cell.setColspan(1);
            cell.setBorderWidthBottom(0f);
            cell.addElement(new Phrase(" Posto: "+lista.get(p).getColonna()));
            tabella.addCell(cell);
            
            cell = new PdfPCell();
            cell.setColspan(2);
            cell.setBorderWidthLeft(0f);
            cell.setBorderWidthRight(0f);
            cell.setBorderWidthBottom(0f);
            cell.addElement(new Phrase(" Prezzo: " + lista.get(p).getPrezzo() + " €"));
            tabella.addCell(cell);
            
            cell = new PdfPCell();
            cell.setColspan(1);
            cell.setBorderWidthBottom(0f);
            cell.addElement(new Phrase(lista.get(p).getTipoBiglietto()+"\t"+ lista.get(p).getTipoFilm()));
            tabella.addCell(cell);
            
            cell = new PdfPCell();
            cell.setColspan(3);
            cell.setBorderWidthLeft(0f);
            cell.setBorderWidthRight(0f);
            cell.setBorderWidthBottom(0f);
            cell.addElement(new Phrase(" Id utente: "+lista.get(p).getId_utente()));
            tabella.addCell(cell);
            
            cell = new PdfPCell();
            cell.setColspan(1);
            cell.setPadding(8f);
            cell.setPaddingTop(9f);
            Image mlogo = Image.getInstance("http://localhost:8084/WebProgProject/img/logomultiplex.png");
            mlogo.setWidthPercentage(100);
            cell.addElement(mlogo);
            cell.setFixedHeight(20f);
            cell.setBorderWidthLeft(0f);
            cell.setBorderWidthRight(0f);
            cell.setBorderWidthBottom(0f);
            tabella.addCell(cell);
            
            cell = new PdfPCell();
            cell.setColspan(2);
            cell.setBorderWidthLeft(0f);
            cell.setBorderWidthRight(0f);
            cell.setBorderWidthBottom(0f);
            cell.setPaddingLeft(0f);
            cell.addElement(new Phrase("\t\tCinema Multiplex\n"
                    + "\t\tTrento Via Sommarive,9 38123 \n"
                    + "\t\tTel:0000 00 0000\n"
                    + "\t\twww.cinemamultiplex.it\n"
                    ,FontFactory.getFont(FontFactory.HELVETICA, 8f, Font.NORMAL, new BaseColor(0, 0, 0)) ));
            tabella.addCell(cell);
            
            
            cell = new PdfPCell();
            cell.setColspan(3);
            cell.setBorderWidthLeft(0f);
            cell.setBorderWidthRight(0f);
            cell.setBorderWidthTop(0f);
            cell.setBorderWidthBottom(0f);
            cell.setPaddingLeft(10f);
            cell.addElement(new Phrase( "Questo titolo di accesso vale solo per il titolo, la sala, l'orario e  la data  indicati",FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0, 0, 0))));
            tabella.addCell(cell);

            
            //inserisco la tabella
            cell = new PdfPCell(tabella);
            cell.setColspan(58);
            //cell.setFixedHeight(180f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            table.addCell(cell);
            table.setSpacingAfter(10f);

            
            
            document.add(table);
            if((p % 3) ==0 && p!=0){
                document.newPage();
            }
        }
            
            document.close();
            //Desktop.getDesktop().open(ciao);

            

        } catch (Exception e) {
            System.out.println( e.getStackTrace());
            e.printStackTrace();
        }

    return result;}
}

