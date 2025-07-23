package askmyapi.amaserver.auth.domain;

import askmyapi.amaserver.auth.domain.vo.AuthId;
import lombok.Getter;

@Getter
public abstract class AuthInfo {
    private AuthId authId;
    private String memberId;

    protected AuthInfo(String memberId){
        this.authId = AuthId.create();
        this.memberId = memberId;
    }

    protected AuthInfo(AuthId authId, String memberId) {
        this.authId = authId;
        this.memberId = memberId;
    }
}
