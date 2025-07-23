package askmyapi.amaserver.auth.adapter.out.persistence.entity;

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
public abstract class AuthInfoJpaEntity {
    @Id
    private UUID id;
    private String memberId;
}
