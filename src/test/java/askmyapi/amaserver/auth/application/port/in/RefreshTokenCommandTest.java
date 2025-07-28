package askmyapi.amaserver.auth.application.port.in;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import static askmyapi.amaserver.auth.AuthTestFixture.generateMemberId;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TC
 * 1. 유효한 memberId와 refreshToken으로 생성된다.
 * 2. memberId가 null이거나 비어있으면 예외가 발생한다.
 * 3. refreshToken이 null이거나 비어있으면 예외가 발생한다
 */
class RefreshTokenCommandTest {

    @Test
    void 유효한_memberId와_refreshToken으로_생성된다() {
        //Arrange
        String memberId = generateMemberId();
        String refreshToken = Jwts.builder().compact();

        //Act & Assert
        assertDoesNotThrow(() -> new RefreshTokenCommand(memberId, refreshToken));
    }

    @Test
    void memberId가_null이면_예외가_발생한다() {
        // Arrange
        String memberId = null;
        String refreshToken = Jwts.builder().compact();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new RefreshTokenCommand(memberId, refreshToken);
        }, "Invalid Member ID");
    }

    @Test
    void memberId가_빈_문자열이면_예외가_발생한다() {
        // Arrange
        String memberId = "";
        String refreshToken = Jwts.builder().compact();
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new RefreshTokenCommand(memberId, refreshToken);
        }, "Invalid Member ID");
    }

    @Test
    void refreshToken이_null이면_예외가_발생한다() {
        // Arrange
        String memberId = generateMemberId();
        String refreshToken = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new RefreshTokenCommand(memberId, refreshToken);
        }, "Invalid Refresh Token");
    }

    @Test
    void refreshToken이_빈_문자열이면_예외가_발생한다() {
        // Arrange
        String memberId = generateMemberId();
        String refreshToken = "";
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new RefreshTokenCommand(memberId, refreshToken);
        }, "Invalid Refresh Token");
    }
}