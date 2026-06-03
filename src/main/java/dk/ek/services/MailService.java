package dk.ek.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MailService {
    private static final String FROM_EMAIL = System.getenv("MAIL_FROM");
    private static final String RESEND_API_KEY = System.getenv("RESEND_API_KEY");

    public static void sendMail(String to, String subject, String body) {
        try {
            String json = String.format("""
                {
                    "from": "%s",
                    "to": ["%s"],
                    "subject": "%s",
                    "html": "%s"
                }
                """, FROM_EMAIL, to, subject, body.replace("\"", "\\\"").replace("\n", "\\n"));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.resend.com/emails"))
                    .header("Authorization", "Bearer " + RESEND_API_KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Mail response: " + response.statusCode() + " " + response.body());

        } catch (Exception e) {
            System.out.println("Mail error: " + e.getMessage());
            e.printStackTrace();
        }
    }
 }
