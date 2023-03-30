package passcard.infrastructure.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import passcard.domain.event.SignedInEvent;
import passcard.domain.event.SignedUpEvent;

@Component
public class AuthEventListener {

    @EventListener
    public void handleSignedUpEvent(SignedUpEvent event) {

        System.out.println("User registered: " + event.user().getEmail());
    }

    @EventListener
    public void handleSignedInEvent(SignedInEvent event) {

        System.out.println("User signed in successfully: " + event.user().getUsername());
    }
}
