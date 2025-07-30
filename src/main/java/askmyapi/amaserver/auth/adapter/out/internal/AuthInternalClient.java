package askmyapi.amaserver.auth.adapter.out.internal;

import askmyapi.amaserver.auth.application.port.out.CreateMemberCommand;
import askmyapi.amaserver.auth.application.port.out.CreateMemberResult;
import askmyapi.amaserver.auth.application.port.out.MemberCommandPort;
import askmyapi.amaserver.member.adapter.in.internal.CreateMemberRequest;
import askmyapi.amaserver.member.adapter.in.internal.MemberInternalAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthInternalClient implements MemberCommandPort {

    private final MemberInternalAdapter memberInternalAdapter;

    @Override
    public CreateMemberResult create(CreateMemberCommand command) {
        var request = new CreateMemberRequest(command.username(), command.profileImageUrl());
        var response = memberInternalAdapter.createMember(request);
        return new CreateMemberResult(
                response.memberId()
        );
    }
}
