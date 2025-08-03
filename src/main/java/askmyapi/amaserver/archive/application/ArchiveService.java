package askmyapi.amaserver.archive.application;

import askmyapi.amaserver.archive.application.port.in.ArchiveIconResult;
import askmyapi.amaserver.archive.application.port.in.ReadArchiveIconAllUseCase;
import askmyapi.amaserver.archive.application.port.out.ArchiveQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArchiveService implements ReadArchiveIconAllUseCase {

    private final ArchiveQueryPort archiveQueryPort;

    @Override
    public List<ArchiveIconResult.Read> readIconAll() {
        return archiveQueryPort.findIconAll().stream()
                .map(icon -> new ArchiveIconResult.Read(
                        icon.getIconCode(),
                        icon.getIconUrl()
                ))
                .toList();
    }
}
