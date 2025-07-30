package askmyapi.amaserver.member.application;

import askmyapi.amaserver.member.application.port.in.CreateMemberCommand;
import askmyapi.amaserver.member.application.port.in.CreateMemberUseCase;
import askmyapi.amaserver.member.application.port.in.MemberResult;
import askmyapi.amaserver.member.application.port.out.MemberCommandPort;
import askmyapi.amaserver.member.application.port.out.MemberEventPublisher;
import askmyapi.amaserver.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements CreateMemberUseCase {

    private final MemberCommandPort memberCommandPort;
    private final MemberEventPublisher eventPublisher;

    @Override
    public MemberResult.Create createMember(CreateMemberCommand command) {
        var member = Member.create(command.name(), command.profileImageUrl());
        memberCommandPort.save(member);
        member.consumeDomainEvents().forEach(eventPublisher::publish);

        return new MemberResult.Create(
                member.getMemberId().getValue(),
                member.getName(),
                member.getProfileImageUrl()
        );
    }
}
