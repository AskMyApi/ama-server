package askmyapi.amaserver.archive.adapter.out.pesistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "archive_icon")
public class ArchiveIconJpaEntity {
    @Id
    private String iconCode;
    private String iconUrl;
}
