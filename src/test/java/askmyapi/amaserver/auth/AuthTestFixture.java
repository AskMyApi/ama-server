package askmyapi.amaserver.auth;

import java.util.UUID;

public class AuthTestFixture {

    public static String generateEmail() {
        return UUID.randomUUID() + "@example.com";
    }

    public static String generateMemberId() {
        return UUID.randomUUID().toString();
    }

    public static String generateUsername() {
        return UUID.randomUUID().toString();
    }

    public static String generateProfileImgUrl() {
        return "https://example.com/profile/" + UUID.randomUUID();
    }
}
