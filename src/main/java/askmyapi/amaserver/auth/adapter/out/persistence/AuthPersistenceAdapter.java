package askmyapi.amaserver.auth.adapter.out.persistence;

import askmyapi.amaserver.auth.adapter.out.persistence.repository.OauthAuthInfoJpaRepository;
import askmyapi.amaserver.auth.application.port.out.*;
import askmyapi.amaserver.auth.domain.OauthAuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthPersistenceAdapter implements AuthQueryPort, AuthCommandPort {

    private final AuthEntityMapper authEntityMapper;

    private final OauthAuthInfoJpaRepository oauthAuthInfoJpaRepository;
    private final RedisTemplate redisTemplate;

    @Override
    public OauthAuthInfo createOauthAuthInfo(OauthAuthInfo oauthAuthInfo) {
        var entity = oauthAuthInfoJpaRepository.save(authEntityMapper.toEntity(oauthAuthInfo));
        return authEntityMapper.toDomain(entity);
    }

    @Override
    public Optional<OauthAuthInfo> findOauthAuthInfo(OauthAuthInfoQuery query) {
        return oauthAuthInfoJpaRepository.findByProviderAndSocialId(query.provider(), query.socialId())
                .map(authEntityMapper::toDomain);
    }

    @Override
    public void saveToken(SaveRefreshTokenCommand command) {
        redisTemplate.opsForValue().set(
                command.memberId(),
                command.refreshToken(),
                Duration.ofMillis(command.expireAt())
        );
    }

    @Override
    public Optional<String> findRefreshToken(FindRefreshTokenQuery query) {
        return Optional.ofNullable((String) redisTemplate.opsForValue().get(query.memberId()));
    }
}
