package askmyapi.amaserver.auth.application.port.in;

import askmyapi.amaserver.auth.domain.vo.OauthAuthenticate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;

import static askmyapi.amaserver.auth.AuthTestFixture.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TC
 * provider: String, email: String, username: String, profileImageUrl: String을 입력받아 생성한다
 * provider가 null이면 IllegalArgumentException, Invalid Provider가 발생한다.
 * email이 null이면 IllegalArgumentException, Invalid Email가 발생한다.
 * email이 유효하지 않은 형식이면 IllegalArgumentException, Invalid Email가 발생한다.
 * username이 null이면 IllegalArgumentException, Invalid Username가 발생한다.
 * profileImageUrl이 null일 경우는 유효한 경우이다
 * profileImageUrl이 유효한 형식이 아니면 IllegalArgumentException, Invalid Profile Image URL가 발생한다.
 */
class LoadOrCreateOauthCommandTest {

    @Test
    void provider_email_username_profileImageUrl을_입력받아_생성한다() {
        // given
        var provider = generateProvider();
        var email = generateEmail();
        var username = generateUsername();
        var profileImageUrl = generateProfileImgUrl();

        // when
        LoadOrCreateOauthCommand command = new LoadOrCreateOauthCommand(provider, email, username, profileImageUrl);

        // then
        assertNotNull(command);
        assertEquals(provider, command.provider());
        assertEquals(email, command.email());
        assertEquals(username, command.username());
        assertEquals(profileImageUrl, command.profileImageUrl());
    }

    @Test
    void provider가_null이면_IllegalArgumentException_Invalid_Provider가_발생한다() {
        // given
        String provider = null;
        var email = generateEmail();
        var username = generateUsername();
        var profileImageUrl = generateProfileImgUrl();

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            new LoadOrCreateOauthCommand(provider, email, username, profileImageUrl);
        }, "Invalid Provider");
    }

    @Test
    void email이_null이면_IllegalArgumentException_Invalid_Email가_발생한다() {
        // given
        var provider = generateProvider();
        String email = null;
        var username = generateUsername();
        var profileImageUrl = generateProfileImgUrl();

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            new LoadOrCreateOauthCommand(provider, email, username, profileImageUrl);
        }, "Invalid Email");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            " ",
            "invalidEmail",
            "invalid@domain",
            "invalid@domain.",
            "invalid@.com",
            "@example.com",
            "user@.com",
    })
    void email이_유효하지_않은_형식이면_IllegalArgumentException_Invalid_Email가_발생한다(String invalidEmail) {
        // given
        var provider = generateProvider();
        var username = generateUsername();
        var profileImageUrl = generateProfileImgUrl();

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            new LoadOrCreateOauthCommand(provider, invalidEmail, username, profileImageUrl);
        }, "Invalid Email");
    }

    @Test
    void username이_null이면_IllegalArgumentException_Invalid_Username가_발생한다() {
        // given
        var provider = generateProvider();
        var email = generateEmail();
        String username = null;
        var profileImageUrl = generateProfileImgUrl();

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            new LoadOrCreateOauthCommand(provider, email, username, profileImageUrl);
        }, "Invalid Username");
    }

    @Test
    void profileImageUrl이_null일_경우는_유효한_경우이다() {
        // given
        var provider = generateProvider();
        var email = generateEmail();
        var username = generateUsername();
        String profileImageUrl = null;

        // when
        LoadOrCreateOauthCommand command = new LoadOrCreateOauthCommand(provider, email, username, profileImageUrl);

        // then
        assertNotNull(command);
        assertEquals(provider, command.provider());
        assertEquals(email, command.email());
        assertEquals(username, command.username());
        assertNull(command.profileImageUrl());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "http://",                          // 도메인 없음
            "http://-example.com",              // 도메인 시작이 하이픈
            "http://example-.com",              // 도메인 끝이 하이픈
            "http://.example.com",              // 도메인 시작이 점
            "http://example..com",              // 도메인에 중복 점
            "http://example.com:abc",           // 포트에 문자 포함
            "http://example.com:99999",         // 포트 숫자 범위 초과
            "http://example.com/space here",    // 경로에 공백 포함
            "http:/example.com",                // 슬래시 하나 부족 (http:/)
            "htp://example.com"                 // 프로토콜 오타
    })
    void profileImageUrl이_유효한_형식이_아니면_IllegalArgumentException_Invalid_Profile_Image_URL가_발생한다(String invalidProfileImageUrl) {
        // given
        var provider = generateProvider();
        var email = generateEmail();
        var username = generateUsername();

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            new LoadOrCreateOauthCommand(provider, email, username, invalidProfileImageUrl);
        }, "Invalid Profile Image URL");
    }
}