package utils;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import configs.PropertiesLoader;

public class SendMail {

  public static void sendMail(String text, int contentCount) throws AddressException, MessagingException, IOException {
    Properties conf = PropertiesLoader.loadProperties();
    String from = conf.getProperty("SENDER");
    String to = conf.getProperty("RECEIVER");
    String host = conf.getProperty("HOST");
    String port = conf.getProperty("PORT");
    String password = conf.getProperty("PASSWORD");

    // Get system properties
    Properties properties = System.getProperties();

    // Setup mail server
    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.port", port);
    properties.put("mail.smtp.ssl.enable", "true");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

    // Get the Session object.// and pass username and password
    Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(from, password);
      }
    });

    // Create a default MimeMessage object.
    MimeMessage message = new MimeMessage(session);

    // Set From: header field of the header.
    message.setFrom(new InternetAddress(from));

    // Set To: header field of the header.
    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

    // Set Subject: header field
    message.setSubject("This is appointment checker for the Dutch Embassy");

    // This mail has 2 part, the BODY and the embedded image
    MimeMultipart multipart = new MimeMultipart("related");

    // first part (the html)
    BodyPart messageBodyPart = new MimeBodyPart();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm:ss");

    String htmlText = String.format(
        "<H1>I have checked any available appointment</H1> <br>" +
            "<b>Operation finished at : %s</b> <br>" +
            "<b>%s<b> <br>" + appendImageContent(contentCount),
        LocalTime.now().format(dtf), text);
    messageBodyPart.setContent(htmlText, "text/html");
    // add it
    multipart.addBodyPart(messageBodyPart);

    for (int i = 1; i <= contentCount; i++) {
      // second part (the image)
      messageBodyPart = new MimeBodyPart();
      DataSource fds = new FileDataSource("tmp/screenshot" + i + ".png");

      messageBodyPart.setDataHandler(new DataHandler(fds));
      messageBodyPart.setHeader("Content-ID", "<image" + i + ">");

      // add image to the multipart
      multipart.addBodyPart(messageBodyPart);
    }

    // put everything together
    message.setContent(multipart);
    // Send message
    Transport.send(message);
    System.out.println("Sent message successfully....");
  }

  private static String appendImageContent(int contentCount) {
    StringBuilder sb = new StringBuilder();
    for (int i = 1; i <= contentCount; i++) {
      sb.append("<img src=\"cid:image" + i + "\">");
    }

    return sb.toString();
  }
}
