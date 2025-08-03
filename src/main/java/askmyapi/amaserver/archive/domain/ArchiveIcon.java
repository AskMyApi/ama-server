package askmyapi.amaserver.archive.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ArchiveIcon {
    private String iconCode;
    private String iconUrl;

    @Builder
    private ArchiveIcon(String iconCode, String iconUrl) {
        this.iconCode = iconCode;
        this.iconUrl = iconUrl;
    }
}
