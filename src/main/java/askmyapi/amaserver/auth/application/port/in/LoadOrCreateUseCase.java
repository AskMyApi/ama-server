package askmyapi.amaserver.auth.application.port.in;

public interface LoadOrCreateUseCase {

    public AuthResult.Id loadOrCreate(AuthCommand.LoadOrCreateOauth command);
}
