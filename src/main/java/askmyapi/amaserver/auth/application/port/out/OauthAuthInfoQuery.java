package askmyapi.amaserver.auth.application.port.out;

import askmyapi.amaserver.auth.domain.vo.OauthAuthenticate;

public record OauthAuthInfoQuery(
        OauthAuthenticate.OauthProvider provider,
        String email
) {
}
