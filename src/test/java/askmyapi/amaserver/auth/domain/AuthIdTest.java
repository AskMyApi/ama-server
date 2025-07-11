package askmyapi.amaserver.auth.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static askmyapi.amaserver.auth.AuthTestFixture.generateMemberId;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TC
 * 1. AuthId는 memberId(String)로 생성된다.
 * 2. memberId는 null이 아니어야 하며, 이와 다를 시 IllegalArgumentException이 발생한다.
 * 4. AuthId는 생성 시 식별자(UUID)를 생성한다.
 * 5. 서로 같은 memberId에 대해 같은 식별자를 가진다.
 * 6. 서로 다른 memberId에 대해 서로 다른 식별자를 가진다.
 */
class AuthIdTest {

    @Test
    void AuthId는_memberId로_생성되며_전달받은_값을_정확히_보유한다() {
        // Arrange
        String memberId = generateMemberId();

        // Act
        AuthId authId = AuthId.of(memberId);

        // Assert
        assertNotNull(authId);
    }

    @Test
    void memberId는_null이_아니어야_하며_이와_다를_시_IllegalArgumentException이_발생한다() {
        // Arrange
        String memberId = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            AuthId.of(memberId);
        });
    }

    @Test
    void authId는_생성시_식별자_UUID를_생성한다() {
        // Arrange
        String memberId = generateMemberId();

        // Act
        AuthId authId = AuthId.of(memberId);

        // Assert
        assertNotNull(authId.id());
    }

    @Test
    void 서로_같은_memberId에_대해_같은_식별자를_가진다() {
        // Arrange
        String memberId = generateMemberId();

        // Act
        AuthId authId1 = AuthId.of(memberId);
        AuthId authId2 = AuthId.of(memberId);

        // Assert
        assertEquals(authId1.id(), authId2.id());
    }

    @Test
    void 서로_다른_memberId에_대해_서로_다른_식별자를_가진다() {
        // Arrange
        String memberId1 = generateMemberId();
        String memberId2 = generateMemberId();

        // Act
        AuthId authId1 = AuthId.of(memberId1);
        AuthId authId2 = AuthId.of(memberId2);

        // Assert
        assertNotEquals(authId1.id(), authId2.id());
    }

}