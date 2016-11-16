package ServletGenerali;


import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Admin
 */
public class SendMail implements Serializable {
    
    /**
     * manda una mail con una prenotazione all'indirizzo specificato
     * @param destinatario
     * @param pdfFile 
     */
    static public void send(String destinatario, ByteArrayOutputStream pdfFile){
        try {

            final String username = "noreply.multiplex@gmail.com";
            final String password = "cinemacinema";

 // Get a Properties object to set the mailing configuration 
            // parameters
            Properties props = System.getProperties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.port", "465");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");

            //We create the session object with the authentication information
            Session session = Session.getDefaultInstance(props, new Authenticator() {

                @Override
                protected PasswordAuthentication
                        getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message msg = new MimeMessage(session);

 //Set the FROM and TO fields –
            msg.setFrom(new InternetAddress(username + ""));
            msg.setRecipients(Message.RecipientType.TO,
                    //InternetAddress.parse("lorenzoturi1994@gmail.com", false)
                    InternetAddress.parse(destinatario, false)
            // InternetAddress.parse("germana.baldi@gmail.com",false)
            );
            msg.setSubject("Cinema Multiplex - Prenotazione Biglietti");
            //msg.setText("Ho creato un account semi-fasullo per inviare email di spam utile anche per il progetto; la pwd e' la parola cinema ripetura 2 volte. " + new Date());
            msg.setSentDate(new Date());

            //Create the multipart message
            Multipart multipart = new MimeMultipart();
            
            //Create the textual part of the message
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setContent("La sua prenotazione è avvenuta con successo."
                    + "In allegato troverà i codici QR da stampare e consegnare alla cassa al momento dell'acquisto del bigletto", "text/html; charset=utf-8");
            

            //Create the Word part of the message
            DataSource source = new ByteArrayDataSource(pdfFile.toByteArray(), "application/pdf");
            BodyPart pdfBodyPart = new MimeBodyPart();
            pdfBodyPart.setDataHandler(new DataHandler(source));
            String data = new SimpleDateFormat(" dd-mm-yy").format(new Date());
            pdfBodyPart.setFileName("Bigletti-Cinema Multiplex"+data+".pdf");
            
            //Add the parts to the Multipart message
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(pdfBodyPart); 
            msg.setContent(multipart);

            //We create the transport object to actually send the e-mail
            Transport transport = session.getTransport("smtps");
            transport.connect("smtp.gmail.com", 465, username, password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            System.out.println("\nEmail sent!\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    /**
     * manda una e-mail cona la password corrispondente all'indirizzo specificato
     * @param destinatario
     * @param pass 
     */
    static public void recupera(String destinatario, String pass){
        try {

            final String username = "noreply.multiplex@gmail.com";
            final String password = "cinemacinema";

 // Get a Properties object to set the mailing configuration 
            // parameters
            Properties props = System.getProperties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.port", "465");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");

            //We create the session object with the authentication information
            Session session = Session.getDefaultInstance(props, new Authenticator() {

                @Override
                protected PasswordAuthentication
                        getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            
            MimeMessage msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(username + ""));
            msg.setRecipients(Message.RecipientType.TO,
                              InternetAddress.parse(destinatario, false));
            msg.setSubject("Cinema Multiplex - Recupero Password");
            msg.setSentDate(new Date());
            
            Multipart multipart = new MimeMultipart();
            
            BodyPart messageBodyPart1 = new MimeBodyPart();
            
            String text = "<p>Password = <b>" + pass + "</b></p>";
            messageBodyPart1.setContent(text, "text/html; charset=utf-8");
            //messageBodyPart1.setText("<p>Password = <b>" + pass + "</b></p>");

            multipart.addBodyPart(messageBodyPart1);
            msg.setContent(multipart);
            
            Transport transport = session.getTransport("smtps");
            transport.connect("smtp.gmail.com", 465, username, password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            System.out.println("\nEmail sent!\n");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

