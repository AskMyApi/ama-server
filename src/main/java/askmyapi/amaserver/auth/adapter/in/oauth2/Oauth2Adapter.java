package askmyapi.amaserver.auth.adapter.in.oauth2;

import askmyapi.amaserver.auth.adapter.in.oauth2.model.UserPrincipal;
import askmyapi.amaserver.auth.application.port.in.LoadOrCreateOauthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Oauth2Adapter implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final LoadOrCreateOauthUseCase loadOrCreateOauthUseCase;

    private final SecurityMapper securityMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        var command = securityMapper.oauth2UserToCommand(userRequest.getClientRegistration(), oAuth2User);
        var result = loadOrCreateOauthUseCase.loadOrCreate(command);

        // Security context에 저장할 객체 생성
        return new UserPrincipal(result, oAuth2User);
    }
}
