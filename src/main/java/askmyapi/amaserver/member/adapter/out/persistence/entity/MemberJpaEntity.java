package askmyapi.amaserver.member.adapter.out.persistence.entity;

import askmyapi.amaserver.global.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "member")
public class MemberJpaEntity extends BaseTimeEntity {
    @Id
    private UUID id;
    private String name;
    private String profileImageUrl;
}
