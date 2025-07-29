package askmyapi.amaserver.auth;

import java.util.UUID;

public class AuthTestFixture {

    public static String generateSocialId() {
        return "test" + UUID.randomUUID();
    }

    public static String generateUsername() {
        return UUID.randomUUID().toString();
    }

    public static String generateMemberId() {
        return UUID.randomUUID().toString();
    }

    public static String generateProfileImgUrl() {
        return "https://example.com/profile/" + UUID.randomUUID() + ".jpg";
    }

    public static String generateProvider() {
        return generateGoogleProvider();
    }

    public static String generateGoogleProvider() {
        return "google";
    }

    public static String generateGithubProvider() {
        return "github";
    }
}
