package askmyapi.amaserver.member.adapter.in.internal;

public record CreateMemberRequest(
        String name,
        String profileImageUrl
) {
}
