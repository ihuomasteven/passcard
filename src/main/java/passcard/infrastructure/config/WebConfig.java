package passcard.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import passcard.domain.event.UserRegisteredEvent;
import reactor.core.publisher.Sinks;

@Configuration
public class WebConfig {

    @Bean
    public Sinks.Many<UserRegisteredEvent> userRegisteredEventSinks() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }
}
