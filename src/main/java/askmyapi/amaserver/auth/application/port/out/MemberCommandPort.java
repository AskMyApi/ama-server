package askmyapi.amaserver.auth.application.port.out;

public interface MemberCommandPort {

    CreateMemberResult create(CreateMemberCommand command);
}
