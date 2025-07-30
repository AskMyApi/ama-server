package askmyapi.amaserver.member.application.port.in;

public interface CreateMemberUseCase {

    MemberResult.Create createMember(CreateMemberCommand command);
}
