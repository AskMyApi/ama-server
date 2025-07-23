package askmyapi.amaserver.auth.application.port.out;

import askmyapi.amaserver.auth.domain.OauthAuthInfo;

public interface AuthCommandPort {

    OauthAuthInfo createOauthAuthInfo(OauthAuthInfo oauthAuthInfo);
}
