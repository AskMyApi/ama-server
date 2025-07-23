package askmyapi.amaserver.auth.application;

import askmyapi.amaserver.auth.application.port.in.AuthResult;
import askmyapi.amaserver.auth.application.port.in.LoadOrCreateOauthCommand;
import askmyapi.amaserver.auth.application.port.in.LoadOrCreateOauthUseCase;
import askmyapi.amaserver.auth.application.port.out.*;
import askmyapi.amaserver.auth.domain.OauthAuthInfo;
import askmyapi.amaserver.auth.domain.vo.OauthAuthenticate;
import askmyapi.amaserver.auth.domain.vo.OauthAuthenticate.OauthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService implements LoadOrCreateOauthUseCase  {

    private final MemberCommandPort memberCommandPort;
    private final AuthCommandPort authCommandPort;
    private final AuthQueryPort authQueryPort;

    public AuthResult.Load loadOrCreate(LoadOrCreateOauthCommand command) {
        var query = new OauthAuthInfoQuery(
                OauthProvider.fromValueIgnoreCase(
                        command.provider()
                ),
                command.email());

        return authQueryPort.findOauthAuthInfo(query)
                .map(authInfo -> new AuthResult.Load(authInfo.getAuthId().getValue(), authInfo.getMemberId()))
                .orElseGet(() ->{
                    var memberCmd = new CreateMemberCommand(command.username(), command.profileImageUrl());
                    var authenticate = OauthAuthenticate.create(command.provider(), command.email());

                    var memberId = memberCommandPort.create(memberCmd).memberId();
                    var authInfo = authCommandPort.createOauthAuthInfo(OauthAuthInfo.create(authenticate, memberId));
                    return new AuthResult.Load(authInfo.getAuthId().getValue(), authInfo.getMemberId());
                });
    }
}
