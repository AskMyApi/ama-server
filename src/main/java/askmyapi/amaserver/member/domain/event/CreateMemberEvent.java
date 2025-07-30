package askmyapi.amaserver.member.domain.event;

import askmyapi.amaserver.global.DomainEvent;

import java.util.UUID;

public record CreateMemberEvent(
        UUID memberId,
        String name,
        String profileImageUrl
) implements DomainEvent {
}
