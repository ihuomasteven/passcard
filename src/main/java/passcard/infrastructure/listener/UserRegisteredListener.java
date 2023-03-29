package passcard.infrastructure.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import passcard.domain.entity.User;
import passcard.domain.event.UserRegisteredEvent;
import passcard.infrastructure.service.UserService;

@Component
public class UserRegisteredListener {

    @EventListener
    public void handleUserRegisteredEvent(UserRegisteredEvent event) {

        System.out.println("User registered: " + event.user().getEmail());
//        userService
//                .getEventPublisher()
//                .asFlux()
//                .subscribe(event -> {
//                    // process the event here
//
//                });
    }
}
