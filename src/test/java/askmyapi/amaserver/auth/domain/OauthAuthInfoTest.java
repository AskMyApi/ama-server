package askmyapi.amaserver.auth.domain;

import askmyapi.amaserver.auth.domain.vo.OauthAuthenticate;
import org.junit.jupiter.api.Test;

import static askmyapi.amaserver.auth.AuthTestFixture.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TC
 * OauthAuthenticate와 memberId로 생성된다
 */
class OauthAuthInfoTest {

    @Test
    void OauthAuthenticate와_memberId로_생성된다() {
        // given
        var provider = OauthAuthenticate.create(generateProvider(), generateEmail());
        var memberId = generateMemberId();

        // when
        OauthAuthInfo oauthAuthInfo = OauthAuthInfo.create(provider, memberId);

        // then
        assertNotNull(oauthAuthInfo);
        assertEquals(provider, oauthAuthInfo.getOauthAuthenticate());
        assertEquals(memberId, oauthAuthInfo.getMemberId());
        assertNotNull(oauthAuthInfo.getAuthId());
    }
}