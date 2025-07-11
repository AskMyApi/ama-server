package askmyapi.amaserver.auth.application;

import askmyapi.amaserver.auth.application.port.in.AuthCommand;
import askmyapi.amaserver.auth.application.port.in.AuthResult;
import askmyapi.amaserver.auth.application.port.in.LoadOrCreateUseCase;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements LoadOrCreateUseCase {

    public AuthResult.Id loadOrCreate(AuthCommand.LoadOrCreateOauth command) {
        return null;
    }
}
