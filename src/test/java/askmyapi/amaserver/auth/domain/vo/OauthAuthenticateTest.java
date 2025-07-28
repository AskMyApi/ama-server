package askmyapi.amaserver.auth.domain.vo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static askmyapi.amaserver.auth.AuthTestFixture.generateSocialId;
import static askmyapi.amaserver.auth.AuthTestFixture.generateProvider;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TC
 * provider와 socialId로 생성된다
 * provider가 google, github이면 정상적으로 생성된다
 * provider가 google, github가 아니면 IllegalArgumentException을 던진다
 */
class OauthAuthenticateTest {

    @Test
    void OauthAuthenticate는_OauthProvider와_socialId로_생성된다() {
        // given
        var provider = generateProvider();
        var socialId = generateSocialId();

        // when
        OauthAuthenticate oauthAuthenticate = OauthAuthenticate.create(provider, socialId);

        // then
        assertNotNull(oauthAuthenticate);
        assertNotNull(oauthAuthenticate.getProvider());
        assertEquals(socialId, oauthAuthenticate.getSocialId());
    }

    @Test
    void OauthAuthenticate는_OauthProvider가_google이면_정상적으로_생성된다() {
        // given
        String provider = "google";
        var socialId = generateSocialId();

        // when
        OauthAuthenticate oauthAuthenticate = OauthAuthenticate.create(provider, socialId);

        // then
        assertNotNull(oauthAuthenticate);
        assertEquals(OauthAuthenticate.OauthProvider.GOOGLE, oauthAuthenticate.getProvider());
        assertEquals(socialId, oauthAuthenticate.getSocialId());
    }

    @Test
    void OauthAuthenticate는_OauthProvider가_github이면_정상적으로_생성된다() {
        // given
        String provider = "github";
        var socialId = generateSocialId();

        // when
        OauthAuthenticate oauthAuthenticate = OauthAuthenticate.create(provider, socialId);

        // then
        assertNotNull(oauthAuthenticate);
        assertEquals(OauthAuthenticate.OauthProvider.GITHUB, oauthAuthenticate.getProvider());
        assertEquals(socialId, oauthAuthenticate.getSocialId());
    }

    @ParameterizedTest
    @ValueSource(strings = {"google_", "git-hub", "facebook", "twitter", "linkedin", "invalid_provider"})
    void OauthAuthenticate는_유효하지_않은_OauthProvider이면_IllegalArgumentException을_던진다(
            String invalidProvider
    ) {
        // given
        var socialId = generateSocialId();

        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            OauthAuthenticate.create(invalidProvider, socialId);
        });

        assertEquals("Invalid OauthProvider", exception.getMessage());
    }
}