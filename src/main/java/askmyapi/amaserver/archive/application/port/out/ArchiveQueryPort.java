package askmyapi.amaserver.archive.application.port.out;

import askmyapi.amaserver.archive.domain.ArchiveIcon;

import java.util.List;

public interface ArchiveQueryPort {

    List<ArchiveIcon> findIconAll();
}
