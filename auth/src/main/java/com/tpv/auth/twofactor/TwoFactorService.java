package com.tpv.auth.twofactor;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;

@Service
@SessionScope
@Data
public class TwoFactorService {

    private Authentication authentication;

    public void sendTwilioCode(String code) throws IOException {
        final Dotenv dotenv = Dotenv.load();
        final String apiKey = dotenv.get("TWILIO_API_KEY");

        // Crear una instancia de SendGrid
        final SendGrid sendGrid = new SendGrid(apiKey);

        // Configuración del correo
        final Email from = new Email(dotenv.get("EMAIL_USER"));
        final String subject = "Codigo Auth";
        final Email to = new Email(dotenv.get("EMAIL_USER")); // TODO Cambia por el correo del destinatario, aqui lo ideal seria pasarselo por parametro el del userDetail (Cambiar en BBDD)
        final Content content = new Content("text/plain", "¡Hola! Este es un mensaje enviado desde Twilio SendGrid." +
                "En el tienes este code:" + code);
        final Mail mail = new Mail(from, subject, to, content);

        // Enviar el correo
        final Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            final Response response = sendGrid.api(request);

            // Mostrar la respuesta
            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Body: " + response.getBody());
            System.out.println("Headers: " + response.getHeaders());
        } catch (final Exception ex) {
            System.out.printf("ERROR {}%n", ex.getMessage());
            throw ex;
        }
    }
}
