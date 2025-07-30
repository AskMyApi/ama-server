package askmyapi.amaserver.member.application.port.in;

import java.util.UUID;

public record MemberResult() {

    public record Create(
            UUID memberId,
            String name,
            String profileImageUrl
    ) {}
}
