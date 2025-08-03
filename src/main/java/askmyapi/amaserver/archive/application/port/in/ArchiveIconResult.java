package askmyapi.amaserver.archive.application.port.in;

public record ArchiveIconResult() {

    public record Read(
            String iconCode,
            String iconUrl
    ) { }
}
