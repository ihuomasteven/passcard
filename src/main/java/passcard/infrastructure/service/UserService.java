package passcard.infrastructure.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import passcard.application.Dto.request.LoginDto;
import passcard.application.Dto.response.AuthResponse;
import passcard.application.exception.LoginException;
import passcard.application.mapper.UserMapper;
import passcard.domain.entity.User;
import passcard.infrastructure.repository.UserRepository;
import passcard.infrastructure.security.TokenProvider;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper mapper;
    private final UserRepository repository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        user = User.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .roles(user.getRoles())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();

        return user;
    }

    @Transactional
    public Mono<User> register(User user) {
        return repository.findByUsername(user.getUsername())
                .flatMap(u -> Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists")))
                .switchIfEmpty(repository.save(createUser(user)))
                .cast(User.class);
    }

    public Mono<AuthResponse> authenticate(LoginDto loginDto) {

        return repository.findByUsername(loginDto.username())
                .flatMap(user -> {
                    if (passwordEncoder.matches(loginDto.password(), user.getPassword())) {
                        String accessToken = tokenProvider.generateToken(user);
                        Long expiryDuration = tokenProvider.getExpiryDuration();

                        return Mono.just(new AuthResponse(accessToken, expiryDuration));
                    }
                    else {
                        return Mono.error(new LoginException("Incorrect username or password"));
                    }
                })
                .switchIfEmpty(Mono.error(new LoginException("User not found")));
    }

}
