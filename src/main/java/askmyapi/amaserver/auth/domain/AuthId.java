package askmyapi.amaserver.auth.domain;

import java.util.UUID;

public record AuthId(
    UUID id
) {
    public static AuthId of(String memberId) {
        validateUserId(memberId);
        return new AuthId(UUID.fromString(memberId));
    }

    private static void validateUserId(String memberId) {
        if (memberId == null || memberId.isBlank()) {
            throw new IllegalArgumentException("사용자 ID 정보가 없습니다.");
        }
    }
}
