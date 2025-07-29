package askmyapi.amaserver.auth.application.port.in;

public interface RefreshTokenUseCase {
    public AuthResult.IssueToken refresh(RefreshTokenCommand command);
}
