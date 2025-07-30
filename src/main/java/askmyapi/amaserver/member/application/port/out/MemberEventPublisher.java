package askmyapi.amaserver.member.application.port.out;

import askmyapi.amaserver.global.DomainEvent;

public interface MemberEventPublisher {

    void publish(DomainEvent event);
}
