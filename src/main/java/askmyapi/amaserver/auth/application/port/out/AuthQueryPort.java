package askmyapi.amaserver.auth.application.port.out;

import askmyapi.amaserver.auth.domain.OauthAuthInfo;

import java.util.Optional;

public interface AuthQueryPort {

    Optional<OauthAuthInfo> findOauthAuthInfo(OauthAuthInfoQuery query);
}
