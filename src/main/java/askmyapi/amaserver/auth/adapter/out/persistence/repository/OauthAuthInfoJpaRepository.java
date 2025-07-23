package askmyapi.amaserver.auth.adapter.out.persistence.repository;

import askmyapi.amaserver.auth.adapter.out.persistence.entity.OauthAuthInfoJpaEntity;
import askmyapi.amaserver.auth.domain.vo.OauthAuthenticate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OauthAuthInfoJpaRepository extends JpaRepository<OauthAuthInfoJpaEntity, String> {

    Optional<OauthAuthInfoJpaEntity> findByProviderAndEmail(OauthAuthenticate.OauthProvider provider, String email);
}
