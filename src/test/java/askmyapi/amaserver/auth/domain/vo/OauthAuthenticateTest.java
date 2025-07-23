package askmyapi.amaserver.auth.domain.vo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static askmyapi.amaserver.auth.AuthTestFixture.generateEmail;
import static askmyapi.amaserver.auth.AuthTestFixture.generateProvider;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TC
 * provider와 email로 생성된다
 * provider가 google, github이면 정상적으로 생성된다
 * provider가 google, github가 아니면 IllegalArgumentException을 던진다
 */
class OauthAuthenticateTest {

    @Test
    void OauthAuthenticate는_OauthProvider와_email로_생성된다() {
        // given
        var provider = generateProvider();
        var email = generateEmail();

        // when
        OauthAuthenticate oauthAuthenticate = OauthAuthenticate.create(provider, email);

        // then
        assertNotNull(oauthAuthenticate);
        assertNotNull(oauthAuthenticate.getProvider());
        assertEquals(email, oauthAuthenticate.getEmail());
    }

    @Test
    void OauthAuthenticate는_OauthProvider가_google이면_정상적으로_생성된다() {
        // given
        String provider = "google";
        var email = generateEmail();

        // when
        OauthAuthenticate oauthAuthenticate = OauthAuthenticate.create(provider, email);

        // then
        assertNotNull(oauthAuthenticate);
        assertEquals(OauthAuthenticate.OauthProvider.GOOGLE, oauthAuthenticate.getProvider());
        assertEquals(email, oauthAuthenticate.getEmail());
    }

    @Test
    void OauthAuthenticate는_OauthProvider가_github이면_정상적으로_생성된다() {
        // given
        String provider = "github";
        var email = generateEmail();

        // when
        OauthAuthenticate oauthAuthenticate = OauthAuthenticate.create(provider, email);

        // then
        assertNotNull(oauthAuthenticate);
        assertEquals(OauthAuthenticate.OauthProvider.GITHUB, oauthAuthenticate.getProvider());
        assertEquals(email, oauthAuthenticate.getEmail());
    }

    @ParameterizedTest
    @ValueSource(strings = {"google_", "git-hub", "facebook", "twitter", "linkedin", "invalid_provider"})
    void OauthAuthenticate는_유효하지_않은_OauthProvider이면_IllegalArgumentException을_던진다(
            String invalidProvider
    ) {
        // given
        var email = generateEmail();

        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            OauthAuthenticate.create(invalidProvider, email);
        });

        assertEquals("Invalid OauthProvider", exception.getMessage());
    }
}