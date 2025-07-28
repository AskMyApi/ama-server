package askmyapi.amaserver.auth.domain;

import static org.junit.jupiter.api.Assertions.*;

import askmyapi.amaserver.auth.JwtAssertions;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

class TokenProviderTest {

    private TokenProvider tokenProvider;

    // 테스트용 설정값
    private final String secretKey = "tmp_secret_key_123456789012345678901234567890"; // 최소 32byte
    private final long accessTokenExpiration = 1000 * 60 * 30; // 30분
    private final long refreshTokenExpiration = 1000 * 60 * 60 * 24 * 7; // 7일

    @BeforeEach
    void setUp() {
        tokenProvider = new TokenProvider(secretKey, accessTokenExpiration, refreshTokenExpiration);
    }

    @Test
    void issueAccessToken_정상적으로_JWT_반환한다() {
        String memberId = "member-123";
        String token = tokenProvider.issueAccessToken(memberId);

        assertNotNull(token);
        assertDoesNotThrow(() -> JwtAssertions.conformsToJwtFormat(token));
    }

    @Test
    void issueRefreshToken_정상적으로_JWT_반환한다() {
        String memberId = "member-123";
        String token = tokenProvider.issueRefreshToken(memberId);

        assertNotNull(token);
        assertDoesNotThrow(() -> JwtAssertions.conformsToJwtFormat(token));
    }

    @Test
    void extractExpiration_토큰_만료시간_정확히_추출한다() {
        String memberId = "member-123";
        String accessToken = tokenProvider.issueAccessToken(memberId);

        Long expiration = tokenProvider.extractExpiration(accessToken);
        assertTrue(expiration > System.currentTimeMillis());
    }

    @Test
    void issueAccessToken_클레임에_memberId_포함된다() {
        String memberId = "member-123";
        String accessToken = tokenProvider.issueAccessToken(memberId);

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        String idFromClaim = (String) Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(accessToken)
                .getPayload()
                .get("id");

        assertEquals(memberId, idFromClaim);
    }
}
