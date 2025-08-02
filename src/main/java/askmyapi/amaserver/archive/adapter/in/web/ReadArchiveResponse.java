package askmyapi.amaserver.archive.adapter.in.web;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record ReadArchiveResponse(
        @Schema(description = "아카이브 ID(UUID)", example = "123e4567-e89b-12d3-a456-426614174000")
        String archiveId,

        @Schema(description = "아카이브 이름", example = "내 아카이브")
        String name,

        @Schema(description = "아카이브 아이콘 코드", example = "ICON001")
        String iconCode,

        @Schema(description = "아이콘 URL", example = "https://example.com/icon.svg")
        String iconUrl
) {
}
