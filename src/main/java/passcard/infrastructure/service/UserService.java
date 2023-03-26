package passcard.infrastructure.service;

import org.springframework.transaction.annotation.Transactional;
import passcard.application.Dto.request.LoginDto;
import passcard.application.Dto.response.ApiResponse;
import passcard.application.mapper.UserMapper;
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
    private final UserMapper mapper;

    public UserService(
            UserRepository repository,
            UserMapper mapper,
            PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
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

    @Transactional
    public Mono<User> register(User user) {
        return repository.findByUsername(user.getUsername())
                .flatMap(u -> Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists")))
                .switchIfEmpty(repository.save(createUser(user)))
                .cast(User.class);
    }

    public Mono<ApiResponse> authenticate(LoginDto loginDto) {

        return repository.findByUsername(loginDto.username())
                .map(user -> {
                    if (passwordEncoder.matches(loginDto.password(), user.getPassword())) {
                        return new ApiResponse(user.toString(), true);
                    } else {
                        return new ApiResponse("Incorrect username or password", false);
                    }
                })
                .defaultIfEmpty(new ApiResponse("User not found", false));
    }

}
