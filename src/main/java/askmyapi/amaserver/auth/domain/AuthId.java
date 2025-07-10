package askmyapi.amaserver.auth.domain;

import lombok.Getter;

import java.util.UUID;

public record AuthId(
    String id
) {
    public static AuthId of(String userId) {
        validateUserId(userId);
        return new AuthId(UUID.fromString(userId).toString());
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
