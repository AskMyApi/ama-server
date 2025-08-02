package askmyapi.amaserver.archive.adapter.in.web;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "아카이브 생성 요청")
public record UpdateArchiveRequest(

        @Schema(description = "아카이브 이름", example = "내 아카이브")
        String name,

        @Schema(description = "아카이브 아이콘 코드", example = "ICON001")
        String iconCode
) {
}
