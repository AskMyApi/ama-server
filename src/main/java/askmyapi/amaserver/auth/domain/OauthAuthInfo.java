package askmyapi.amaserver.auth.domain;

import askmyapi.amaserver.auth.domain.vo.AuthId;
import askmyapi.amaserver.auth.domain.vo.OauthAuthenticate;
import lombok.Getter;

@Getter
public class OauthAuthInfo extends AuthInfo {
    private OauthAuthenticate oauthAuthenticate;

    private OauthAuthInfo(OauthAuthenticate oauthAuthenticate, String memberId) {
        super(memberId);

        this.oauthAuthenticate = oauthAuthenticate;
    }

    private OauthAuthInfo(AuthId id, OauthAuthenticate oauthAuthenticate, String memberId) {
        super(id, memberId);
        this.oauthAuthenticate = oauthAuthenticate;
    }

    public static OauthAuthInfo create(OauthAuthenticate oauthAuthenticate, String memberId) {
        return new OauthAuthInfo(oauthAuthenticate, memberId);
    }

    public static OauthAuthInfo createWithId(AuthId id, OauthAuthenticate oauthAuthenticate, String memberId) {
        return new OauthAuthInfo(id, oauthAuthenticate, memberId);
    }
}
