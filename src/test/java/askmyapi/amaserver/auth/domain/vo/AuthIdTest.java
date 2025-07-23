package askmyapi.amaserver.auth.domain.vo;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * TC
 * AuthId는 생성 시점에 UUID를 생성하여 authId를 부여한다
 * AuthId는 서로 중복되지 않는다
 */
class AuthIdTest {

    @Test
    void AuthId는_생성_시점에_UUID를_생성한다() {
        // given
        AuthId authId = AuthId.create();

        // when
        UUID value = authId.getValue();

        // then
        assertNotNull(value);
    }

    @Test
    void AuthId는_서로_중복되지_않는다() {
        // given
        AuthId authId1 = AuthId.create();
        AuthId authId2 = AuthId.create();

        // when
        UUID value1 = authId1.getValue();
        UUID value2 = authId2.getValue();

        // then
        assertNotEquals(authId1, authId2);
        assertNotEquals(value1, value2);
    }

}