package askmyapi.amaserver.archive.adapter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Archive API", description = "아카이브 관련 API")
@RequestMapping("/api/v1")
public interface ArchiveSpecs {

    @Operation(
            summary = "아카이브 생성",
            description = "지정된 이름과 스펙 목록으로 아카이브를 생성합니다. 스펙 타입은 SWAGGER, REST_DOCS, GIT_BOOK 중 하나여야 합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공적으로 생성됨"),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 Argument",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                    name = "Argument의 형식 오류 또는 누락",
                                    value = "{ " +
                                            "   \"errorCode\": \"INVALID_ARG\"" +
                                            "}"),
                                    @ExampleObject(
                                    name = "아이콘 코드가 유효하지 않은 경우",
                                    value = "{ " +
                                            "   \"errorCode\": \"INVALID_ICON_CODE\"" +
                                            "}"),
                                    @ExampleObject(
                                    name = "입력된 스펙 URL에 존재하지 않거나 접근할 수 없는 URL이 포함",
                                    value = "{ " +
                                            "   \"errorCode\": \"INVALID_SPEC_URL\", " +
                                            "}"),
                                    @ExampleObject(
                                    name = "스펙 URL의 스펙이 스펙 타입이 일치하지 않는 경우",
                                    value = "{ " +
                                            "   \"errorCode\": \"SPEC_TYPE_NOT_MATCHED\", " +
                                            "}"),
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "클라이언트와 서버 상태 충돌",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "PRO 플랜 구독자가 아니지만 아카이브 내 2개 이상의 스펙을 설정하는 경우",
                                            value = "{ " +
                                                    "   \"errorCode\": \"ARCHIVE_SPEC_LIMIT_EXCEEDED\"" +
                                                    "}"
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "서버 내부 오류",
                                            value = "{ " +
                                                    "   \"errorCode\": \"INTERNAL_SERVER_ERROR\"" +
                                                    "}"
                                    )
                            }
                    )
            )
    })
    @PostMapping("/archives")
    ResponseEntity<Void> createArchive(
            @RequestBody CreateArchiveRequest request
    );


    @Operation(
            summary = "아카이브 수정",
            description = "지정된 이름과 아이콘 코드로 아카이브를 수정합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "성공적으로 수정됨"),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 Argument",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Argument의 형식 오류 또는 누락",
                                            value = "{ " +
                                                    "   \"errorCode\": \"INVALID_ARG\"" +
                                                    "}"),
                                    @ExampleObject(
                                            name = "아이콘 코드가 유효하지 않은 경우",
                                            value = "{ " +
                                                    "   \"errorCode\": \"INVALID_ICON_CODE\"" +
                                                    "}"),
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "권한 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "요청자의 아카이브가 아닌 경우",
                                            value = "{ " +
                                                    "   \"errorCode\": \"FORBIDDEN\"" +
                                                    "}"
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "아카이브를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "아카이브가 존재하지 않는 경우",
                                            value = "{ " +
                                                    "   \"errorCode\": \"ARCHIVE_NOT_FOUND\"" +
                                                    "}"
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "서버 내부 오류",
                                            value = "{ " +
                                                    "   \"errorCode\": \"INTERNAL_SERVER_ERROR\"" +
                                                    "}"
                                    )
                            }
                    )
            )
    })
    @PutMapping("/archives/{archive-id}")
    ResponseEntity<Void> updateArchive(
            @PathVariable("archive-id") String archiveId,
            @RequestBody UpdateArchiveRequest request
    );


    @Operation(
            summary = "아카이브 삭제",
            description = "지정된 아카이브 ID로 아카이브를 삭제합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "성공적으로 삭제됨"),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 Argument",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Argument의 형식 오류 또는 누락",
                                            value = "{ " +
                                                    "   \"errorCode\": \"INVALID_ARG\"" +
                                                    "}"),
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "권한 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "요청자의 아카이브가 아닌 경우",
                                            value = "{ " +
                                                    "   \"errorCode\": \"FORBIDDEN\"" +
                                                    "}"
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "아카이브를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "아카이브가 존재하지 않는 경우",
                                            value = "{ " +
                                                    "   \"errorCode\": \"ARCHIVE_NOT_FOUND\"" +
                                                    "}"
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "서버 내부 오류",
                                            value = "{ " +
                                                    "   \"errorCode\": \"INTERNAL_SERVER_ERROR\"" +
                                                    "}"
                                    )
                            }
                    )
            )
    })
    @DeleteMapping("/archives/{archive-id}")
    ResponseEntity<Void> deleteArchive(
            @PathVariable("archive-id") String archiveId
    );

    @Operation(
            summary = "아카이브 조회",
            description = "요청자 소유의 아카이브 목록과 아카이브에 속한 스펙 목록을 조회합니다. 요청자 정보는 jwt 토큰에서 추출합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReadArchiveResponse.class)
                    )),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "서버 내부 오류",
                                            value = "{ " +
                                                    "   \"errorCode\": \"INTERNAL_SERVER_ERROR\"" +
                                                    "}"
                                    )
                            }
                    )
            )
    })
    @GetMapping("/archives")
    ResponseEntity<Page<ReadArchiveResponse>> getArchives(
            @Schema(description = "페이지 번호", example = "0")
            @RequestParam(value = "page", defaultValue = "0") int page,
            @Schema(description = "페이지 크기", example = "20")
            @RequestParam(value = "size", defaultValue = "20") int size
    );

    @Operation(
            summary = "스펙 추가",
            description = "아카이브에 새로운 스펙을 추가합니다. 스펙 URL은 실제 존재하는 URL이어야 하며, 스펙 문서 타입은 SWAGGER, REST_DOCS, GIT_BOOK 중 하나여야 합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공적으로 추가됨"),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 Argument",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Argument의 형식 오류 또는 누락",
                                            value = "{ " +
                                                    "   \"errorCode\": \"INVALID_ARG\"" +
                                                    "}"),
                                    @ExampleObject(
                                            name = "입력된 스펙 URL에 존재하지 않거나 접근할 수 없는 URL이 포함",
                                            value = "{ " +
                                                    "   \"errorCode\": \"INVALID_SPEC_URL\", " +
                                                    "}"),
                                    @ExampleObject(
                                            name = "스펙 URL의 스펙이 스펙 타입이 일치하지 않는 경우",
                                            value = "{ " +
                                                    "   \"errorCode\": \"SPEC_TYPE_NOT_MATCHED\", " +
                                                    "}"),
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "권한 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "요청자의 아카이브가 아닌 경우",
                                            value = "{ " +
                                                    "   \"errorCode\": \"FORBIDDEN\"" +
                                                    "}"
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "아카이브를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "아카이브가 존재하지 않는 경우",
                                            value = "{ " +
                                                    "   \"errorCode\": \"ARCHIVE_NOT_FOUND\"" +
                                                    "}"
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "클라이언트와 서버 상태 충돌",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "PRO 플랜 구독자가 아니지만 아카이브 내 2개 이상의 스펙을 설정하는 경우",
                                            value = "{ " +
                                                    "   \"errorCode\": \"ARCHIVE_SPEC_LIMIT_EXCEEDED\"" +
                                                    "}"
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "서버 내부 오류",
                                            value = "{ " +
                                                    "   \"errorCode\": \"INTERNAL_SERVER_ERROR\"" +
                                                    "}"
                                    )
                            }
                    )
            )
    })
    @PostMapping("/archives/{archive-id}/specs")
    ResponseEntity<Void> addSpecToArchive(
            @Schema(description = "아카이브 ID(UUID)", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable("archive-id") String archiveId,
            @RequestBody AddSpecRequest request);

    @Operation(
            summary = "스펙 삭제",
            description = "아카이브에서 지정된 스펙을 삭제합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "성공적으로 삭제됨"),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 Argument",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Argument의 형식 오류 또는 누락",
                                            value = "{ " +
                                                    "   \"errorCode\": \"INVALID_ARG\"" +
                                                    "}"),
                                    @ExampleObject(
                                            name = "스펙이 아카이브에 존재하지 않는 경우",
                                            value = "{ " +
                                                    "   \"errorCode\": \"ARCHIVE_SPEC_NOT_MATCHED\"" +
                                                    "}")
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "권한 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "요청자의 아카이브가 아닌 경우",
                                            value = "{ " +
                                                    "   \"errorCode\": \"FORBIDDEN\"" +
                                                    "}"
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "리소스를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "아카이브가 존재하지 않는 경우",
                                            value = "{ " +
                                                    "   \"errorCode\": \"ARCHIVE_NOT_FOUND\"" +
                                                    "}"
                                    ),
                                    @ExampleObject(
                                            name = "스펙이 존재하지 않는 경우",
                                            value = "{ " +
                                                    "   \"errorCode\": \"SPEC_NOT_FOUND\"" +
                                                    "}"
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "서버 내부 오류",
                                            value = "{ " +
                                                    "   \"errorCode\": \"INTERNAL_SERVER_ERROR\"" +
                                                    "}"
                                    )
                            }
                    )
            )
    })
    @DeleteMapping("/archives/{archive-id}/specs/{spec-id}")
    ResponseEntity<Void> deleteSpecFromArchive(
            @Schema(description = "아카이브 ID(UUID)", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable("archive-id") String archiveId,
            @Schema(description = "스펙 ID(UUID)", example = "123e4567-e89b-12d3-a456-426614174001")
            @PathVariable("spec-id") String specId
    );
}
