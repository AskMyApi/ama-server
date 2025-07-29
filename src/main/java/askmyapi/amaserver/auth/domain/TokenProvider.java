package askmyapi.amaserver.auth.domain;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class TokenProvider {
    private final String secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public TokenProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.access-token.expiration}") long accessTokenExpiration,
            @Value("${jwt.refresh-token.expiration}") long refreshTokenExpiration
    ) {
        this.secretKey = secretKey;
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public String issueAccessToken(String memberId) {
        // Access token 발급 로직 구현
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        return Jwts.builder()
                .subject(memberId)
                .claim("id", memberId)
                .claim("jti", java.util.UUID.randomUUID().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(key)
                .compact();
    }

    public String issueRefreshToken(String memberId) {
        // Refresh token 발급 로직 구현
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        return Jwts.builder()
                .subject(memberId)
                .claim("jti", java.util.UUID.randomUUID().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(key)
                .compact();
    }

    public Long extractExpiration(String token) {
        // 토큰의 만료 시간을 추출하는 로직
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .getTime();
    }
}
