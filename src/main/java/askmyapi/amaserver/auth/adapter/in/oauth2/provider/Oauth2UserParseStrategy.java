package askmyapi.amaserver.auth.adapter.in.oauth2.provider;

import java.util.Map;

public interface Oauth2UserParseStrategy {
    String getEmail(Map<String, Object> attributes);
    String getUsername(Map<String, Object> attributes);
    String getProfileImage(Map<String, Object> attributes);
}
