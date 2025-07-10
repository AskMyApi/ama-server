package askmyapi.amaserver.auth.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TC
 * 1. OauthAuthInfo는 provider(OauthProvider), email(String), userId(String)로 생성되며, 전달받은 값을 정확히 보유한다.
 * 2. provider는 google, github 중 하나이며, 이와 다를 시 IllegalArgumentException이 발생한다.
 * 3. email은 유효한 이메일 형식이어야 하며, 이와 다를 시 IllegalArgumentException이 발생한다.
 * 4. userId는 UUID 형식이어야 하며, 이와 다를 시 IllegalArgumentException이 발생한다.
 */
class OauthAuthInfoTest {

    @Test
    void OauthAuthInfo는_provider_email_userId로_생성되며_전달받은_값을_정확히_보유한다() {
        // Arrange
        OauthProvider provider = OauthProvider.GOOGLE;
        String email = "test@example.com";
        String userId = UUID.randomUUID().toString();

        // Act
        var authInfo = OauthAuthInfo.create(provider, email, userId);

        // Assert
        assertThat(authInfo).isNotNull();
        assertEquals(provider, authInfo.getProvider());
        assertEquals(email, authInfo.getEmail());
        assertEquals(userId, authInfo.getUserId());
    }
}