package askmyapi.amaserver.auth.application.port.in;

import askmyapi.amaserver.auth.domain.OauthProvider;

public record AuthCommand() {

    public record LoadOrCreateOauth(
            OauthProvider provider,
            String email,
            String username,
            String profileImageUrl
    ) {
        public LoadOrCreateOauth {
            if (provider == null) {
                throw new IllegalArgumentException("인증 제공자 정보가 없습니다.");
            }
            if (email == null || !email.matches("^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
                throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
            }
            if (username == null || username.isBlank()) {
                throw new IllegalArgumentException("사용자 이름이 비어있습니다.");
            }
            if (profileImageUrl != null && !profileImageUrl.matches("^(https?)://[\\w-]+(\\.[\\w-]+)+(/[^\\s]*)?$")) {
                throw new IllegalArgumentException("유효하지 않은 프로필 이미지 URL 형식입니다.");
            }
        }
    }
}
