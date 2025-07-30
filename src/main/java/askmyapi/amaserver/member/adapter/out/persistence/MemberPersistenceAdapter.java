package askmyapi.amaserver.member.adapter.out.persistence;

import askmyapi.amaserver.member.adapter.out.persistence.repository.MemberJpaRepository;
import askmyapi.amaserver.member.application.port.out.MemberCommandPort;
import askmyapi.amaserver.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements MemberCommandPort {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberEntityMapper entityMapper;

    @Override
    public Member save(Member member) {
        var savedEntity = memberJpaRepository.save(
                entityMapper.toEntity(member)
        );
        return entityMapper.toDomain(savedEntity);
    }
}
