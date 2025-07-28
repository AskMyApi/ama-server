package askmyapi.amaserver.auth.application.port.out;

public record SaveRefreshTokenCommand(
        String memberId,
        String refreshToken,
        Long expireAt
) {
}
