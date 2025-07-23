package askmyapi.amaserver.auth.adapter.out.persistence;

import askmyapi.amaserver.auth.adapter.out.persistence.repository.OauthAuthInfoJpaRepository;
import askmyapi.amaserver.auth.application.port.out.AuthCommandPort;
import askmyapi.amaserver.auth.application.port.out.AuthQueryPort;
import askmyapi.amaserver.auth.application.port.out.OauthAuthInfoQuery;
import askmyapi.amaserver.auth.domain.OauthAuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthPersistenceAdapter implements AuthQueryPort, AuthCommandPort {

    private final AuthEntityMapper authEntityMapper;

    private final OauthAuthInfoJpaRepository oauthAuthInfoJpaRepository;

    @Override
    public OauthAuthInfo createOauthAuthInfo(OauthAuthInfo oauthAuthInfo) {
        var entity = oauthAuthInfoJpaRepository.save(authEntityMapper.toEntity(oauthAuthInfo));
        return authEntityMapper.toDomain(entity);
    }

    @Override
    public Optional<OauthAuthInfo> findOauthAuthInfo(OauthAuthInfoQuery query) {
        return oauthAuthInfoJpaRepository.findByProviderAndEmail(query.provider(), query.email())
                .map(authEntityMapper::toDomain);
    }
}
