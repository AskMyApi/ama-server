package askmyapi.amaserver.member.application.port.in;

import static askmyapi.amaserver.util.UrlValidator.isValidUrl;

public record CreateMemberCommand(
        String name,
        String profileImageUrl
) {
    public CreateMemberCommand {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Invalid Name");
        }
        if (profileImageUrl != null && !isValidUrl(profileImageUrl)) {
            throw new IllegalArgumentException("Invalid Profile Image URL");
        }
    }
}
