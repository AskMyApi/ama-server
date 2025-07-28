package askmyapi.amaserver.auth.adapter.in.web;

public record RefreshTokenResponse(
        String accessToken,
        String refreshToken
) {
}
