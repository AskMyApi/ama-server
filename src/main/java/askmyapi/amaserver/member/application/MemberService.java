package askmyapi.amaserver.member.application;

import askmyapi.amaserver.member.application.port.in.CreateMemberCommand;
import askmyapi.amaserver.member.application.port.in.CreateMemberUseCase;
import askmyapi.amaserver.member.application.port.in.MemberResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements CreateMemberUseCase {

    @Override
    public MemberResult.Create createMember(CreateMemberCommand command) {
        return null;
    }
}
