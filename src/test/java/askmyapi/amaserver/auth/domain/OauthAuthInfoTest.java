package askmyapi.amaserver.auth.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static askmyapi.amaserver.auth.AuthTestFixture.generateEmail;
import static askmyapi.amaserver.auth.AuthTestFixture.generateMemberId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TC
 * 1. OauthAuthInfo는 provider(OauthProvider), email(String), memberId(String)로 생성되며, 전달받은 값을 정확히 보유한다.
 * 2. provider가 null 일 때, IllegalArgumentException이 발생한다.
 * 3. email은 null이 아니어야 하며, 이와 다를 시 IllegalArgumentException이 발생한다.
 * 4. email은 유효한 이메일 형식이어야 하며, 이와 다를 시 IllegalArgumentException이 발생한다.
 * 5. OauthAuthInfo는 생성 시 식별자(AuthId)를 함께 생성한다.
 * 6. OauthAuthInfo는 생성 시 생성시간(LocalDateTime)을 설정한다.
 * 7. OauthAuthInfo는 생성 시 갱신시간(LocalDateTime)을 설정한다.
 * 8. OauthAuthInfo는 AuthInfo로 추상화하여 식별자, 생성시간, 갱신시간을 조회할 수 있다.
 */
class OauthAuthInfoTest {

    @Test
    void OauthAuthInfo는_provider_email_memberId로_생성되며_전달받은_값을_정확히_보유한다() {
        // Arrange
        OauthProvider provider = OauthProvider.GOOGLE;
        String email = generateEmail();
        String memberId = generateMemberId();

        // Act
        var authInfo = OauthAuthInfo.create(provider, email, memberId);

        // Assert
        assertThat(authInfo).isNotNull();
        assertEquals(provider, authInfo.getProvider());
        assertEquals(email, authInfo.getEmail());
        assertEquals(memberId, authInfo.getMemberId());
    }

    @Test
    void provider가_null일_때_IllegalArgumentException이_발생한다() {
        // Arrange
        OauthProvider provider = null;
        String email = generateEmail();
        String memberId = generateMemberId();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            OauthAuthInfo.create(provider, email, memberId);
        });
    }

    @Test
    void email은_null이_아니어야_하며_이와_다를_시_IllegalArgumentException이_발생한다() {
        // Arrange
        OauthProvider provider = OauthProvider.GOOGLE;
        String memberId = generateMemberId();
        String email = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            OauthAuthInfo.create(provider, email, memberId);
        });
    }

    @ParameterizedTest
    @ValueSource( strings = {
            "",
            " ",
            "invalid-email",
            "test@.com",
            "test@com",
            "@example.com"}
    )
    void email은_유효한_이메일_형식이고_null이_아니어야_하며_이와_다를_시_IllegalArgumentException이_발생한다(
            String email
    ) {
        // Arrange
        OauthProvider provider = OauthProvider.GOOGLE;
        String memberId = generateMemberId();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            OauthAuthInfo.create(provider, email, memberId);
        });
    }

    @Test
    void OauthAuthInfo는_생성시_식별자_AuthId를_생성한다() {
        // Arrange
        OauthProvider provider = OauthProvider.GOOGLE;
        String email = generateEmail();
        String memberId = generateMemberId();
        // Act
        var authInfo = OauthAuthInfo.create(provider, email, memberId);
        // Assert
        assertThat(authInfo.getAuthId()).isNotNull();
    }

    @Test
    void OauthAuthInfo는_생성시_생성시간과_갱신시간을_설정한다() {
        // Arrange
        OauthProvider provider = OauthProvider.GOOGLE;
        String email = generateEmail();
        String memberId = generateMemberId();

        // Act
        var authInfo = OauthAuthInfo.create(provider, email, memberId);

        // Assert
        assertThat(authInfo.getCreatedAt()).isNotNull();
        assertThat(authInfo.getUpdatedAt()).isNotNull();
        assertThat(authInfo.getCreatedAt()).isEqualTo(authInfo.getUpdatedAt());
    }

    @Test
    void OauthAuthInfo는_AuthInfo로_추상화하여_식별자_생성시간_갱신시간을_조회할_수_있다() {
        // Arrange
        OauthProvider provider = OauthProvider.GOOGLE;
        String email = generateEmail();
        String memberId = generateMemberId();

        // Act
        AuthInfo authInfo = OauthAuthInfo.create(provider, email, memberId);

        // Assert
        assertThat(authInfo.getAuthId()).isNotNull();
        assertThat(authInfo.getMemberId()).isEqualTo(memberId);
        assertThat(authInfo.getCreatedAt()).isNotNull();
        assertThat(authInfo.getUpdatedAt()).isNotNull();
    }
}