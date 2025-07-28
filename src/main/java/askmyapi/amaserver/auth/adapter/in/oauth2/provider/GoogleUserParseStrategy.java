package askmyapi.amaserver.auth.adapter.in.oauth2.provider;


import java.util.Map;

public class GoogleUserParseStrategy implements Oauth2UserParseStrategy {

    @Override
    public String getSocialId(Map<String, Object> attributes) {
        return (String) attributes.get("sub").toString();
    }

    @Override
    public String getUsername(Map<String, Object> attributes) {
        return (String) attributes.get("name");
    }

    @Override
    public String getProfileImage(Map<String, Object> attributes) {
        return (String) attributes.get("picture");
    }
}
