package askmyapi.amaserver.archive.adapter;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "스펙 추가 요청")
public record AddSpecRequest(
        @Schema(description = "스펙 URL (URL 형식을 만족, 실제 존재하는 URL, 스펙 문서 타입에 일치하는 스펙)", example = "https://petstore.swagger.io/")
        String specUrl,

        @Schema(
                description = "스펙 문서 타입 (SWAGGER, REST_DOCS, GIT_BOOK)",
                example = "SWAGGER",
                allowableValues = {"SWAGGER", "REST_DOCS", "GIT_BOOK"}
        )
        String specType
) {
}
