package passcard.infrastructure.security;

import passcard.application.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AuthEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException authException) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = "Authentication failed";

        if (authException instanceof LockedException) {
            status = HttpStatus.LOCKED;
            message = "Your account has been locked. Please contact support.";
        } else if (authException instanceof DisabledException) {
            status = HttpStatus.FORBIDDEN;
            message = "Your account has been disabled. Please contact support.";
        } else if (authException instanceof CredentialsExpiredException) {
            status = HttpStatus.FORBIDDEN;
            message = "Your credentials have expired. Please reset your password.";
        } else if (authException instanceof AccountExpiredException) {
            status = HttpStatus.FORBIDDEN;
            message = "Your account has expired. Please contact support.";
        }

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", message);

        return response.writeWith(Mono.just(response.bufferFactory()
                .wrap(JsonUtils.toJson(responseBody).getBytes())));
    }
}