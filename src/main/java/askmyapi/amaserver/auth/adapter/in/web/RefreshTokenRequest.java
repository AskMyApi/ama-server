package askmyapi.amaserver.auth.adapter.in.web;

public record RefreshTokenRequest(
        String memberId,
        String refreshToken
) {
}
