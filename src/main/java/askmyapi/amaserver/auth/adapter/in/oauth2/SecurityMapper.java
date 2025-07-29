package askmyapi.amaserver.auth.adapter.in.oauth2;

import askmyapi.amaserver.auth.adapter.in.oauth2.model.Oauth2UserWrapper;
import askmyapi.amaserver.auth.adapter.in.oauth2.provider.GitHubUserParseStrategy;
import askmyapi.amaserver.auth.adapter.in.oauth2.provider.GoogleUserParseStrategy;
import askmyapi.amaserver.auth.application.port.in.LoadOrCreateOauthCommand;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class SecurityMapper {

    public LoadOrCreateOauthCommand oauth2UserToCommand(ClientRegistration registration, OAuth2User oauth2User) {
        String providerAsLowerCase = registration.getClientName().toLowerCase();

        var strategy = switch (providerAsLowerCase) {
            case "google" -> new GoogleUserParseStrategy();
            case "github" -> new GitHubUserParseStrategy();
            default -> throw new IllegalArgumentException("Invalid Oauth2 Provider");
        };

        return Oauth2UserWrapper.from(registration, oauth2User)
                .setStrategy(strategy)
                .toCommand();
    }
}
