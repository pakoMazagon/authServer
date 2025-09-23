package com.tpv.auth.twofactor;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
@Data
public class TwoFactorService {

    private Authentication authentication;


    public void sendResendCode(String code, String mail) {
        final Dotenv dotenv = Dotenv.load();
        final String apiKey = dotenv.get("RENDER_API_KEY");
        final Resend resend = new Resend(apiKey);

        final CreateEmailOptions params = CreateEmailOptions.builder()
                .from("Pako TPV <onboarding@resend.dev>")
                .to(mail)
                .subject("Codigo para acceder a TPV")
                .html("<p>Tu código para acceder es: <strong>" + code + "</strong><p>")
                .build();

        try {
            final CreateEmailResponse data = resend.emails().send(params);
            System.out.println(data.getId());
        } catch (final ResendException e) {
            e.printStackTrace();
        }
    }
}
