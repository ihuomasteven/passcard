package passcard.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import passcard.application.Dto.request.LoginDto;
import passcard.application.Dto.request.SignupDto;
import passcard.application.Dto.response.ApiResponse;
import passcard.application.Dto.response.AuthResponse;
import passcard.infrastructure.service.UserService;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public Mono<ResponseEntity<ApiResponse>> register(@Valid @RequestBody SignupDto signupDto) {
        return userService
                .register(signupDto)
                .map(apiResponse -> ResponseEntity.ok().body(apiResponse))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> authenticate(@RequestBody LoginDto loginDto) {
        return userService
                .authenticate(loginDto)
                .map(authResponse -> ResponseEntity.ok().body(authResponse))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
