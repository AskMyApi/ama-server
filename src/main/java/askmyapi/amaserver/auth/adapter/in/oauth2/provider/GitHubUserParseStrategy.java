package askmyapi.amaserver.auth.adapter.in.oauth2.provider;

import java.util.Map;

public class GitHubUserParseStrategy implements Oauth2UserParseStrategy {

    @Override
    public String getEmail(Map<String, Object> attributes) {
        return (String) attributes.get("email");
    }

    @Override
    public String getUsername(Map<String, Object> attributes) {
        return (String) attributes.get("name"); // 또는 login
    }

    @Override
    public String getProfileImage(Map<String, Object> attributes) {
        return (String) attributes.get("avatar_url");
    }
}
