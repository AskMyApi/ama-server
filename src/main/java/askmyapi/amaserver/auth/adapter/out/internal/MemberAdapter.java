package askmyapi.amaserver.auth.adapter.out.internal;

import askmyapi.amaserver.auth.application.port.out.CreateMemberCommand;
import askmyapi.amaserver.auth.application.port.out.CreateMemberResult;
import askmyapi.amaserver.auth.application.port.out.MemberCommandPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MemberAdapter implements MemberCommandPort {

    //TODO member Service 구현 후 연동
    @Override
    public CreateMemberResult create(CreateMemberCommand command) {
        return new CreateMemberResult(
                UUID.randomUUID().toString()
        );
    }
}
