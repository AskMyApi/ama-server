package askmyapi.amaserver.member.adapter.out.persistence;

import askmyapi.amaserver.member.adapter.out.persistence.entity.MemberJpaEntity;
import askmyapi.amaserver.member.domain.Member;
import askmyapi.amaserver.member.domain.vo.MemberId;
import org.springframework.stereotype.Component;

@Component
public class MemberEntityMapper {

    MemberJpaEntity toEntity(Member member) {
        return MemberJpaEntity.builder()
                .id(member.getMemberId().getValue())
                .name(member.getName())
                .profileImageUrl(member.getProfileImageUrl())
                .build();
    }

    Member toDomain(MemberJpaEntity entity) {
        return Member.createWithId(
                MemberId.from(entity.getId()),
                entity.getName(),
                entity.getProfileImageUrl()
        );
    }
}
