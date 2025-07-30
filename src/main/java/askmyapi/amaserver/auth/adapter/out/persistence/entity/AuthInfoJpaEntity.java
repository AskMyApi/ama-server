package askmyapi.amaserver.auth.adapter.out.persistence.entity;

import askmyapi.amaserver.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@DiscriminatorColumn
@Getter
@Table(name = "auth_info")
public abstract class AuthInfoJpaEntity extends BaseTimeEntity {
    @Id
    private UUID id;
    private String memberId;
}
