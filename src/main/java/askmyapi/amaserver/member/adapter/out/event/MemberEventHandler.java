package askmyapi.amaserver.member.adapter.out.event;

import askmyapi.amaserver.global.DomainEvent;
import askmyapi.amaserver.member.application.port.out.MemberEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberEventHandler implements MemberEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(DomainEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
