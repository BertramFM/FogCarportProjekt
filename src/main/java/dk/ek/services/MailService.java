package dk.ek.services;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.InternetAddress;
import jakarta.activation.DataHandler;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;

import java.io.InputStream;
import java.util.Properties;
public class MailService {
    private static final String FROM_EMAIL = System.getenv("MAIL_FROM");
    private static final String RESEND_API_KEY = System.getenv("RESEND_API_KEY");

    public static void sendMail(String to, String subject, String body) {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.host", "smtp.resend.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.trust", "smtp.resend.com");

        Session session = Session.getInstance(
                properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("resend", RESEND_API_KEY);
                    }
                }
        );

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(body, "text/html; charset=utf-8");

            MimeBodyPart imagePart = new MimeBodyPart();
            InputStream imageStream = MailService.class
                    .getResourceAsStream("/public/images/logo.png");

            if (imageStream != null) {
                imagePart.setDataHandler(new DataHandler(
                        new ByteArrayDataSource(imageStream, "image/png")
                ));
                imagePart.setHeader("Content-ID", "<logo>");
                imagePart.setDisposition(MimeBodyPart.INLINE);
            }

            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);
            if (imageStream != null) multipart.addBodyPart(imagePart);

            message.setContent(multipart);
            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 }
