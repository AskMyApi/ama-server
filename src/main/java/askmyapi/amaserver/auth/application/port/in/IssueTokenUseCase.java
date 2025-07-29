package askmyapi.amaserver.auth.application.port.in;

public interface IssueTokenUseCase {
    public AuthResult.IssueToken issue(IssueTokenCommand command);
}
