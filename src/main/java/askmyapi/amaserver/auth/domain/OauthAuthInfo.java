package askmyapi.amaserver.auth.domain;

import lombok.Getter;

@Getter
public class OauthAuthInfo extends AuthInfo {
    private OauthProvider provider;
    private String email;

    private OauthAuthInfo(OauthProvider provider, String email, String userId) {
        super(userId);
        this.provider = provider;
        this.email = email;
    }

    public static OauthAuthInfo create(OauthProvider provider, String email, String userId) {
        validateParams(provider, email, userId);
        return new OauthAuthInfo(provider, email, userId);
    }

    private static void validateParams(OauthProvider provider, String email, String userId) {
        validateProvider(provider);
        validateEmail(email);
    }

    private static void validateEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("이메일 정보가 없습니다.");
        }
        if (!email.matches("^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
        }
    }

    private static void validateProvider(OauthProvider provider) {
        if (provider == null) {
            throw new IllegalArgumentException("인증 제공자 정보가 없습니다.");
        }
    }
}
