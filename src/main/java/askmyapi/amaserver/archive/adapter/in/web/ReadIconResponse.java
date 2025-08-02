package askmyapi.amaserver.archive.adapter.in.web;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "아이콘 읽기 응답")
public record ReadIconResponse(
    @Schema(description = "아이콘 코드", example = "ICON001")
    String iconCode,

    @Schema(description = "아이콘 URL", example = "https://example.com/icon.png")
    String iconUrl) {}
