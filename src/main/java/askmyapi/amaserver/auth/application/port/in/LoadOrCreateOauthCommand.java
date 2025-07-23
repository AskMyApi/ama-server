package askmyapi.amaserver.auth.application.port.in;

import askmyapi.amaserver.auth.domain.vo.OauthAuthenticate;
import org.apache.commons.validator.routines.UrlValidator;

import java.net.MalformedURLException;

public record LoadOrCreateOauthCommand(
        String provider,
        String email,
        String username,
        String profileImageUrl
) {

    public LoadOrCreateOauthCommand {
        validateProvider(provider);
        validateEmail(email);
        validateUsername(username);
        validateProfileImageUrl(profileImageUrl);
        OauthAuthenticate.OauthProvider.fromValueIgnoreCase(provider);
    }

    private void validateProvider(String provider) {
        if (provider == null) {
            throw new IllegalArgumentException("Invalid Provider");
        }
    }

    private void validateEmail(String email) {
        if (email == null || !email.matches("^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,}$")) {
            throw new IllegalArgumentException("Invalid Email");
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
