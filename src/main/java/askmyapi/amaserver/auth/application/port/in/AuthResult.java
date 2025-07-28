package askmyapi.amaserver.auth.application.port.in;

import java.util.UUID;

public record AuthResult() {

    public record Load(
            UUID id,
            String memberId
    ) {}

    public record IssueToken(
            String accessToken,
            Long accessTokenExpiresIn,
            String refreshToken,
            Long refreshTokenExpiresIn
    ) {}
}
