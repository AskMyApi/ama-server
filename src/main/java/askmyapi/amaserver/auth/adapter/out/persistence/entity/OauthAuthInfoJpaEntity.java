package askmyapi.amaserver.auth.adapter.out.persistence.entity;

import askmyapi.amaserver.auth.domain.vo.OauthAuthenticate;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@DiscriminatorValue("OAUTH")
public class OauthAuthInfoJpaEntity extends AuthInfoJpaEntity {
    private OauthAuthenticate.OauthProvider provider;
    private String email;

}
