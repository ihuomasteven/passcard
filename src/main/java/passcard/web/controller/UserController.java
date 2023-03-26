package passcard.web.controller;

import org.springframework.http.ResponseEntity;
import passcard.application.Dto.request.LoginDto;
import passcard.application.Dto.response.ApiResponse;
import passcard.domain.entity.User;
import passcard.infrastructure.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Mono<User> register(@Valid @RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<ApiResponse>> authenticate(@RequestBody LoginDto loginDto) {
        return userService
                .authenticate(loginDto)
                .map(apiResponse -> ResponseEntity.ok().body(apiResponse))
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()));
    }
}
