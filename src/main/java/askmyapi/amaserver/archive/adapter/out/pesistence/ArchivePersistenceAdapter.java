package askmyapi.amaserver.archive.adapter.out.pesistence;

import askmyapi.amaserver.archive.adapter.out.pesistence.repository.ArchiveIconJpaRepository;
import askmyapi.amaserver.archive.application.port.out.ArchiveQueryPort;
import askmyapi.amaserver.archive.domain.ArchiveIcon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ArchivePersistenceAdapter implements ArchiveQueryPort {

    private final ArchiveIconJpaRepository archiveIconJpaRepository;

    private final ArchiveEntityMapper entityMapper;

    @Override
    public List<ArchiveIcon> findIconAll() {
        return archiveIconJpaRepository.findAll().stream()
                .map(entityMapper::toDomain)
                .toList();
    }
}
