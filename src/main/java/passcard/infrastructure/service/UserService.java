package passcard.infrastructure.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import passcard.application.Dto.request.LoginDto;
import passcard.application.Dto.request.SignupDto;
import passcard.application.Dto.response.ApiResponse;
import passcard.application.Dto.response.AuthResponse;
import passcard.application.exception.LoginException;
import passcard.application.exception.SignupException;
//import passcard.application.mapper.UserMapper;
import passcard.domain.entity.User;
import passcard.domain.event.SignedInEvent;
import passcard.domain.event.SignedUpEvent;
import passcard.infrastructure.repository.UserRepository;
import passcard.infrastructure.security.TokenProvider;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

//    private final UserMapper mapper;
    private final UserRepository repository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    public User createUser(SignupDto signupDto) {

        return User.builder()
                .email(signupDto.email())
                .username(signupDto.username())
                .roles(signupDto.roles())
                .password(passwordEncoder.encode(signupDto.password()))
                .build();
    }


    public Mono<ApiResponse> register(SignupDto signupDto) {
        Mono<Boolean> isUsername = repository.existsByUsername(signupDto.username());
        Mono<Boolean> isEmail = repository.existsByEmail(signupDto.email());

        return Mono.zip(isUsername, isEmail)
                .flatMap(tuple -> {
                    boolean usernameExists = tuple.getT1();
                    boolean emailExists = tuple.getT2();

                    if (usernameExists) {
                        return Mono.error(new SignupException(signupDto.username(), "Username already exists"));
                    }

                    if (emailExists) {
                        return Mono.error(new SignupException(signupDto.email(), "Email already exists"));
                    }

                    User newUser = createUser(signupDto);
                    return repository.save(newUser)
                            .map(user -> new ApiResponse("User registered successfully", true))
                            .doOnSuccess(user -> eventPublisher.publishEvent(new SignedUpEvent(newUser)))
                            .onErrorResume(error -> Mono.just(new ApiResponse(error.getMessage(), false)));
                });
    }

    public Mono<AuthResponse> authenticate(LoginDto loginDto) {

        return repository.findByUsername(loginDto.username())
                .flatMap(user -> {
                    if (passwordEncoder.matches(loginDto.password(), user.getPassword())) {
                        String accessToken = tokenProvider.generateToken(user);
                        Long expiryDuration = tokenProvider.getExpiryDuration();

                        eventPublisher.publishEvent(new SignedInEvent(user));

                        return Mono.just(new AuthResponse(accessToken, expiryDuration));
                    }
                    else {
                        return Mono.error(new LoginException("Incorrect username or password"));
                    }
                })
                .switchIfEmpty(Mono.error(new LoginException("User not found")));
    }

}
