package askmyapi.amaserver.auth.adapter.in.oauth2.model;

import askmyapi.amaserver.auth.adapter.in.oauth2.provider.Oauth2UserParseStrategy;
import askmyapi.amaserver.auth.application.port.in.LoadOrCreateOauthCommand;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Oauth2UserWrapper {
    private ClientRegistration registration;
    private OAuth2User oAuth2User;
    private Oauth2UserParseStrategy strategy;

    private Oauth2UserWrapper(ClientRegistration registration, OAuth2User oAuth2User) {
        this.registration = registration;
        this.oAuth2User = oAuth2User;
    }

    public static Oauth2UserWrapper from(ClientRegistration registration, OAuth2User oAuth2User) {
        return new Oauth2UserWrapper(registration, oAuth2User);
    }

    public Oauth2UserWrapper setStrategy(Oauth2UserParseStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public LoadOrCreateOauthCommand toCommand() {
        return new LoadOrCreateOauthCommand(
                registration.getClientName(),
                strategy.getSocialId(oAuth2User.getAttributes()),
                strategy.getUsername(oAuth2User.getAttributes()),
                strategy.getProfileImage(oAuth2User.getAttributes())
        );
    }
}
