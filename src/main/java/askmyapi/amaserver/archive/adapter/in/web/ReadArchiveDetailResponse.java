package askmyapi.amaserver.archive.adapter.in.web;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema
public record ReadArchiveDetailResponse(
        @Schema(description = "아카이브 ID(UUID)", example = "123e4567-e89b-12d3-a456-426614174000")
        String archiveId,

        @Schema(description = "아카이브 이름", example = "내 아카이브")
        String name,

        @Schema(description = "아카이브 아이콘 코드", example = "ICON001")
        String iconCode,

        @Schema(description = "아이콘 URL", example = "https://example.com/icon.svg")
        String iconUrl,

        @ArraySchema(
                schema = @Schema(implementation = Spec.class),
                arraySchema = @Schema(description = "스펙 리스트")
        )
        List<Spec> specs
) {
        public record Spec(
                @Schema(description = "스펙 ID(UUID)", example = "123e4567-e89b-12d3-a456-426614174001")
                String specId,

                @Schema(description = "스펙 URL (URL 형식을 만족, 실제 존재하는 URL, 스펙 문서 타입에 일치하는 스펙)", example = "https://petstore.swagger.io/")
                String specUrl,

                @Schema(
                        description = "스펙 문서 타입 (SWAGGER, REST_DOCS)",
                        example = "SWAGGER",
                        allowableValues = {"SWAGGER", "REST_DOCS"}
                )
                String specType,

                @Schema(description = "스펙 유효성 여부", example = "true")
                Boolean isValid
        ){ }
}
