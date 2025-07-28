package askmyapi.amaserver.auth.adapter.in.oauth2.provider;

import java.util.Map;

public class GitHubUserParseStrategy implements Oauth2UserParseStrategy {

    @Override
    public String getSocialId(Map<String, Object> attributes) {
        return (String) attributes.get("id").toString();
    }

    @Override
    public String getUsername(Map<String, Object> attributes) {
        return (String) attributes.get("login");
    }

    @Override
    public String getProfileImage(Map<String, Object> attributes) {
        return (String) attributes.get("avatar_url");
    }
}
