package askmyapi.amaserver.auth.application.port.in;

public interface LoadOrCreateOauthUseCase {
    public AuthResult.Load loadOrCreate(LoadOrCreateOauthCommand command);
}
