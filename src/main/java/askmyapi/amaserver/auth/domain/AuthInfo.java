package askmyapi.amaserver.auth.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public abstract class AuthInfo {
    private String authId;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected AuthInfo(String userId){
        validateUserId(userId);
        LocalDateTime now = LocalDateTime.now();

        this.authId = generateAuthId(userId);
        this.userId = userId;
        this.createdAt = now;
        this.updatedAt = now;
    }

    private String generateAuthId(String userId) {
        return UUID.fromString(userId).toString();
    }

    private static void validateUserId(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("사용자 ID 정보가 없습니다.");
        }
        if (!userId.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")) {
            throw new IllegalArgumentException("유효하지 않은 사용자 ID 형식입니다.");
        }
    }
}
