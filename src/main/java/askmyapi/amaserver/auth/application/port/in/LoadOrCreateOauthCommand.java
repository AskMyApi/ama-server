package askmyapi.amaserver.auth.application.port.in;

import askmyapi.amaserver.auth.domain.vo.OauthAuthenticate;
import org.apache.commons.validator.routines.UrlValidator;

import java.net.MalformedURLException;

public record LoadOrCreateOauthCommand(
        String provider,
        String socialId,
        String username,
        String profileImageUrl
) {

    public LoadOrCreateOauthCommand {
        validateProvider(provider);
        validateSocialId(socialId);
        validateUsername(username);
        validateProfileImageUrl(profileImageUrl);
        OauthAuthenticate.OauthProvider.fromValueIgnoreCase(provider);
    }

    private void validateProvider(String provider) {
        if (provider == null) {
            throw new IllegalArgumentException("Invalid Provider");
        }
    }

    private void validateSocialId(String socialId) {
        if (socialId == null || socialId.isBlank()) {
            throw new IllegalArgumentException("Invalid Social ID");
        }
    }

    private void validateUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Invalid Username");
        }
    }

    private void validateProfileImageUrl(String profileImageUrl) {
        if (profileImageUrl != null && !isValidUrl(profileImageUrl)) {
            throw new IllegalArgumentException("Invalid Profile Image URL");
        }
    }

    private boolean isValidUrl(String url) {
        UrlValidator validator = new UrlValidator(
                new String[] {"http", "https"} // 허용할 프로토콜\
        );
        return validator.isValid(url);
    }

}
