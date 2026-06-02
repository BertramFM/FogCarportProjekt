package dk.ek.services;

import dk.ek.persistence.ConnectionPool;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.InternetAddress;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMultipart;

import java.util.Properties;
public class MailService {
    private static final String FROM_EMAIL = "jasminagar21@gmail.com";
    private static final String APP_PASSWORD = "hpzbuwveaxhjfcef";

    public static void sendMail(String to, String subject, String body) {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(
            properties,
            new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
                }
            }
        );

        try {

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(to)
            );

            message.setSubject(subject);

            // HTML del
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(body, "text/html; charset=utf-8");

            // Billede del
            MimeBodyPart imagePart = new MimeBodyPart();

            DataSource fds = new FileDataSource(
                "src/main/resources/public/images/logo.png"
            );

            imagePart.setDataHandler(new DataHandler(fds));

            // matcher cid:fogLogo i HTML
            imagePart.setHeader("Content-ID", "<logo>");

            imagePart.setDisposition(MimeBodyPart.INLINE);

            // Multipart mail
            MimeMultipart multipart = new MimeMultipart();

            multipart.addBodyPart(htmlPart);
            multipart.addBodyPart(imagePart);

            message.setContent(multipart);

            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
