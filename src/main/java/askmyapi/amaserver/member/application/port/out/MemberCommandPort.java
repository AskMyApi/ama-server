package askmyapi.amaserver.member.application.port.out;

import askmyapi.amaserver.member.domain.Member;

public interface MemberCommandPort {

    Member save(Member member);
}
