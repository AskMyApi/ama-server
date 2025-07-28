package askmyapi.amaserver.auth.adapter.out.persistence;

import askmyapi.amaserver.auth.adapter.out.persistence.entity.OauthAuthInfoJpaEntity;
import askmyapi.amaserver.auth.domain.OauthAuthInfo;
import askmyapi.amaserver.auth.domain.vo.AuthId;
import askmyapi.amaserver.auth.domain.vo.OauthAuthenticate;
import org.springframework.stereotype.Component;

@Component
public class AuthEntityMapper {

    public OauthAuthInfo toDomain(OauthAuthInfoJpaEntity entity) {
        return OauthAuthInfo.createWithId(
                AuthId.from(entity.getId()),
                OauthAuthenticate.from(
                        entity.getProvider(),
                        entity.getSocialId()
                ),
                entity.getMemberId());
    }

    public OauthAuthInfoJpaEntity toEntity(OauthAuthInfo domain) {
        return OauthAuthInfoJpaEntity.builder()
                .id(domain.getAuthId().getValue())
                .memberId(domain.getMemberId())
                .provider(domain.getOauthAuthenticate().getProvider())
                .socialId(domain.getOauthAuthenticate().getSocialId())
                .build();
    }
}
