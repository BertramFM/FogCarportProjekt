package dk.ek.services;

import dk.ek.persistence.ConnectionPool;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.InternetAddress;

import java.util.Properties;
public class MailService {
    ConnectionPool connectionPool;
    private static final String FROM_EMAIL = "jasminagar21@gmail.com";
    private static final String APP_PASSWORD = "hpzbuwveaxhjfcef";

    public MailService(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

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

        try{

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            message.setContent(body, "text/html; charset=utf-8");

            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
