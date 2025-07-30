package askmyapi.amaserver.member;

import static java.util.UUID.randomUUID;

public class MemberTestFixture {

    public static String generateName() {
        return "user-" + randomUUID();
    }

    public static String generateProfileImageUrl() {
        return "https://example.com/profile/" + randomUUID() + ".jpg";
    }
}
