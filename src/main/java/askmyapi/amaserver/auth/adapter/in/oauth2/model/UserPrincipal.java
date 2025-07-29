package askmyapi.amaserver.auth.adapter.in.oauth2.model;

import askmyapi.amaserver.auth.application.port.in.AuthResult;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class UserPrincipal implements OAuth2User {
    private String memberId;
    private OAuth2User oAuth2User;

    public UserPrincipal(AuthResult.Load result, OAuth2User oAuth2User) {
        this.memberId = result.memberId();
        this.oAuth2User = oAuth2User;
    }

    @Override
    public String getName() {
        return this.memberId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }
}
