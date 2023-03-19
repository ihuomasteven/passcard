package passcard.application.Dto.response;

import lombok.*;

@Data
public class JwtAuthResponse {

    private String accessToken;
//    private String refreshToken;
    private String tokenType;
    private Long expiryDuration;

    public JwtAuthResponse(String accessToken, Long expiryDuration) {
        this.accessToken = accessToken;
//        this.refreshToken = refreshToken;
        this.expiryDuration = expiryDuration;
        tokenType = "Bearer ";
    }
}
