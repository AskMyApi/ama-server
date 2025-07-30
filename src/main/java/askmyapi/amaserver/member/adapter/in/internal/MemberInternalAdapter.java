package askmyapi.amaserver.member.adapter.in.internal;

import askmyapi.amaserver.member.application.port.in.CreateMemberCommand;
import askmyapi.amaserver.member.application.port.in.CreateMemberUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberInternalAdapter {

    private final CreateMemberUseCase createMemberUseCase;

    public CreateMemberResponse createMember(CreateMemberRequest request) {
        var command = new CreateMemberCommand(request.name(), request.profileImageUrl());
        var result = createMemberUseCase.createMember(command);
        return new CreateMemberResponse(result.memberId().toString());
    }

}
