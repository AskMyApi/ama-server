package askmyapi.amaserver.auth.application.port.in;

import askmyapi.amaserver.auth.domain.OauthProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static askmyapi.amaserver.auth.AuthTestFixture.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TC
 * - AuthCommand.LoadOrCreateOauth
 * 1. OauthProvider, email, username, profileImageUrl을 받아 생성된다.
 * 2. OauthProvider이 null이면 IllegalArgumentException이 발생한다.
 * 3. email이 null이면 IllegalArgumentException이 발생한다.
 * 4. email이 유효한 이메일 형식이 아니면 IllegalArgumentException이 발생한다.
 * 5. username이 null이 아니면 IllegalArgumentException이 발생한다.
 * 6. username이 비어있으면 IllegalArgumentException이 발생한다.
 * 7. profileImageUrl이 url 형식이 아니면 IllegalArgumentException이 발생한다.
 */
class AuthCommandTest {

    @Test
    void LoadOrCreateOauth는_OauthProvider_email_username_profileImageUrl로_생성된다() {
        // Arrange
        OauthProvider provider = OauthProvider.GOOGLE;
        String email = generateEmail();
        String username = generateUsername();
        String profileImageUrl = generateProfileImgUrl();

        // Act
        assertDoesNotThrow(() -> {
            var command = new AuthCommand.LoadOrCreateOauth(provider, email, username, profileImageUrl);
            // Assert
            assertNotNull(command);
            assertEquals(provider, command.provider());
            assertEquals(email, command.email());
            assertEquals(username, command.username());
            assertEquals(profileImageUrl, command.profileImageUrl());
        });
    }

    @Test
    void OauthProvider가_null이면_IllegalArgumentException이_발생한다() {
        // Arrange
        OauthProvider provider = null;
        String email = generateEmail();
        String username = generateUsername();
        String profileImageUrl = generateProfileImgUrl();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new AuthCommand.LoadOrCreateOauth(provider, email, username, profileImageUrl);
        });
    }

    @Test
    void email이_null이면_IllegalArgumentException이_발생한다() {
        // Arrange
        OauthProvider provider = OauthProvider.GOOGLE;
        String email = null;
        String username = generateUsername();
        String profileImageUrl = generateProfileImgUrl();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new AuthCommand.LoadOrCreateOauth(provider, email, username, profileImageUrl);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "", "plainaddress", "@missingusername.com", "username@.com", "username@domain..com"
    })
    void email이_유효한_형식이_아니면_IllegalArgumentException이_발생한다(String invalidEmail) {
        // Arrange
        OauthProvider provider = OauthProvider.GOOGLE;
        String email = invalidEmail;
        String username = generateUsername();
        String profileImageUrl = generateProfileImgUrl();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new AuthCommand.LoadOrCreateOauth(provider, email, username, profileImageUrl);
        });
    }

    @Test
    void username이_null이면_IllegalArgumentException이_발생한다() {
        // Arrange
        OauthProvider provider = OauthProvider.GOOGLE;
        String email = generateEmail();
        String username = null;
        String profileImageUrl = generateProfileImgUrl();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new AuthCommand.LoadOrCreateOauth(provider, email, username, profileImageUrl);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t"})
    void username이_비어있으면_IllegalArgumentException이_발생한다(String emptyUsername) {
        // Arrange
        OauthProvider provider = OauthProvider.GOOGLE;
        String email = generateEmail();
        String username = emptyUsername;
        String profileImageUrl = generateProfileImgUrl();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new AuthCommand.LoadOrCreateOauth(provider, email, username, profileImageUrl);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "", " ", "invalid-url", "http//missing-colon.com", "ftp://example.com/image.jpg", "https://example..com/image.jpg"
    })
    void profileImageUrl이_유효한_URL_형식이_아니면_IllegalArgumentException이_발생한다(String invalidUrl) {
        // Arrange
        OauthProvider provider = OauthProvider.GOOGLE;
        String email = generateEmail();
        String username = generateUsername();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new AuthCommand.LoadOrCreateOauth(provider, email, username, invalidUrl);
        });
    }

}