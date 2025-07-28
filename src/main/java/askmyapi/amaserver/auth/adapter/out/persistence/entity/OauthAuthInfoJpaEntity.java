package askmyapi.amaserver.auth.adapter.out.persistence.entity;

import askmyapi.amaserver.auth.domain.vo.OauthAuthenticate;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@DiscriminatorValue("OAUTH")
@Table(name = "oauth_auth_info")
public class OauthAuthInfoJpaEntity extends AuthInfoJpaEntity {
    @Enumerated(EnumType.STRING)
    private OauthAuthenticate.OauthProvider provider;
    private String socialId;

}
