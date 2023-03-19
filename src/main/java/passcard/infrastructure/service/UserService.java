package passcard.infrastructure.service;

import passcard.domain.entity.User;
import passcard.infrastructure.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        user = User.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .roles(user.getRoles())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();

        return user;
    }

    public Mono<User> register(User user) {
        return repository.findByUsername(user.getUsername())
                .flatMap(u -> Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists")))
                .switchIfEmpty(repository.save(createUser(user)))
                .cast(User.class);
    }

    public Mono<User> login(String username, String password) {
        return repository.findByUsername(username)
                .flatMap(user -> {
                    if (passwordEncoder.matches(password, user.getPassword())) {
                        log.info(user.getPassword());
                        return Mono.just(user);
                    } else {
                        return Mono.error(
                                new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));
                    }
                })
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password")))
                .cast(User.class);
    }
}
