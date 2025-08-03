package askmyapi.amaserver.archive.adapter.out.pesistence;

import askmyapi.amaserver.archive.adapter.out.pesistence.entity.ArchiveIconJpaEntity;
import askmyapi.amaserver.archive.domain.ArchiveIcon;
import org.springframework.stereotype.Component;

@Component
public class ArchiveEntityMapper {

    public ArchiveIcon toDomain(ArchiveIconJpaEntity entity) {
        return ArchiveIcon.builder()
                .iconCode(entity.getIconCode())
                .iconUrl(entity.getIconUrl())
                .build();
    }
}
