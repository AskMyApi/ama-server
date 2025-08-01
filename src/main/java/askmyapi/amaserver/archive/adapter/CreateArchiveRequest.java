package askmyapi.amaserver.archive.adapter;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "아카이브 생성 요청")
public record CreateArchiveRequest(

        @Schema(description = "아카이브 이름", example = "내 아카이브")
        String name,

        @Schema(description = "아카이브 아이콘 코드", example = "📁")
        String iconCode,

        @ArraySchema(
                schema = @Schema(implementation = Spec.class),
                arraySchema = @Schema(description = "아카이브 명세 리스트")
        )
        List<Spec> specs
) {
    @Schema(description = "아카이브 명세 정보")
    public record Spec(
            @Schema(description = "스펙 URL (URL 형식을 만족, 실제 존재하는 URL, 스펙 문서 타입에 일치하는 스펙)", example = "https://petstore.swagger.io/")
            String specUrl,

            @Schema(
                    description = "스펙 문서 타입 (SWAGGER, REST_DOCS, GIT_BOOK)",
                    example = "SWAGGER",
                    allowableValues = {"SWAGGER", "REST_DOCS", "GIT_BOOK"}
            )
            String specType
    ) {}
}
