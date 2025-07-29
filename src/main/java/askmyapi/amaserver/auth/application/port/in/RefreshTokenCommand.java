package askmyapi.amaserver.auth.application.port.in;

public record RefreshTokenCommand(
        String memberId,
        String refreshToken
) {
    public RefreshTokenCommand {
        if (memberId == null || memberId.isBlank()) {
            throw new IllegalArgumentException("Invalid Member ID");
        }
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new IllegalArgumentException("Invalid Refresh Token");
        }
    }
}
