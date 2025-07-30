package askmyapi.amaserver.member.adapter.out.persistence.repository;

import askmyapi.amaserver.member.adapter.out.persistence.entity.MemberJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, UUID> {
}
