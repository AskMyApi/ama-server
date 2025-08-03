package askmyapi.amaserver.archive.application.port.in;

import java.util.List;

public interface ReadArchiveIconAllUseCase {

    public List<ArchiveIconResult.Read> readIconAll();
}
