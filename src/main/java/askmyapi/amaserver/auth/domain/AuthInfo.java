package askmyapi.amaserver.auth.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public abstract class AuthInfo {
    private AuthId authId;
    private String memberId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected AuthInfo(String memberId){
        LocalDateTime now = LocalDateTime.now();

        this.authId = AuthId.of(memberId);
        this.memberId = memberId;
        this.createdAt = now;
        this.updatedAt = now;
    }
}
