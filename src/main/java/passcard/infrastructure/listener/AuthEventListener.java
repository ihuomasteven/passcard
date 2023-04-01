package passcard.infrastructure.listener;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import passcard.domain.event.SignedInEvent;
import passcard.domain.event.SignedUpEvent;
import passcard.infrastructure.service.MailService;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthEventListener {

    private final MailService mailService;

    @EventListener
    public void handleSignedUpEvent(SignedUpEvent event) {

        log.info("User registered: " + event.user().getEmail());
    }

    @EventListener
    public void handleSignedInEvent(SignedInEvent event) throws TemplateException, MessagingException, IOException {

        mailService.sendAccountActivityEmail(event.user().getEmail(), "Login", "Successful");
        log.info("User signed in successfully: " + event.user().getUsername());
    }
}
