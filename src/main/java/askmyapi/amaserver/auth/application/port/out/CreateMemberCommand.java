package askmyapi.amaserver.auth.application.port.out;

public record CreateMemberCommand(
        String username,
        String profileImageUrl
) {
}
