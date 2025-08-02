package askmyapi.amaserver.archive.adapter.in.web.spec;

import askmyapi.amaserver.archive.adapter.in.web.*;
import askmyapi.amaserver.util.InfiniteScroll;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Archive API", description = "아카이브 관련 API")
public interface ArchiveSpecs {

    @Operation(
            summary = "아카이브 생성",
            description = "지정된 이름과 스펙 목록으로 아카이브를 생성합니다. 스펙 타입은 SWAGGER, REST_DOCS 중 하나여야 합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "성공적으로 생성됨"),
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
                                    ),
                                    @ExampleObject(
                                            name = "스펙을 제한개수(5개) 이상 추가하려는 경우",
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
    ResponseEntity<Void> createArchive(
            CreateArchiveRequest request
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
    ResponseEntity<Void> updateArchive(
            String archiveId,
            UpdateArchiveRequest request
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
    ResponseEntity<Void> deleteArchive(
            String archiveId
    );

    @Operation(
            summary = "아카이브 조회",
            description = "요청자 소유의 아카이브 목록과 아카이브에 속한 스펙 목록을 조회합니다. 요청자 정보는 jwt 토큰에서 추출합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = InfiniteScrollReadArchiveResponse.class)
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
    ResponseEntity<InfiniteScroll<ReadArchiveResponse>> getArchives(
            @Schema(description = "검색 키워드 (선택)", example = "내 아카이브")
            String keyword,
            @Schema(description = "마지막으로 조회된 아카이브 ID(UUID) (최초 조회일 경우 포함하지 않음)", example = "123e4567-e89b-12d3-a456-426614174000")
            String lastId,
            @Schema(description = "조회할 아카이브 개수 (기본값: 10) (선택)", example = "10")
            int size
    );

    @Operation(
            summary = "스펙 추가",
            description = "아카이브에 새로운 스펙을 추가합니다. 스펙 URL은 실제 존재하는 URL이어야 하며, 스펙 문서 타입은 SWAGGER, REST_DOCS, GIT_BOOK 중 하나여야 합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "성공적으로 추가됨"),
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
                                    ),
                                    @ExampleObject(
                                            name = "스펙을 제한개수(5개) 이상 추가하려는 경우",
                                            value = "{ " +
                                                    "   \"errorCode\": \"ARCHIVE_SPEC_LIMIT_EXCEEDED\"" +
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
    ResponseEntity<Void> addSpecToArchive(
            @Schema(description = "아카이브 ID(UUID)", example = "123e4567-e89b-12d3-a456-426614174000") String archiveId,
            AddSpecRequest request);

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
    ResponseEntity<Void> deleteSpecFromArchive(
            @Schema(description = "아카이브 ID(UUID)", example = "123e4567-e89b-12d3-a456-426614174000")
            String archiveId,
            @Schema(description = "스펙 ID(UUID)", example = "123e4567-e89b-12d3-a456-426614174001")
            String specId
    );

    @Operation(
            summary = "아카이브 상세 조회",
            description = "지정된 아카이브 ID로 아카이브의 상세 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReadArchiveDetailResponse.class)
                    )),
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
    ResponseEntity<ReadArchiveDetailResponse> getArchiveDetail(
            @Schema(description = "아카이브 ID(UUID)", example = "123e4567-e89b-12d3-a456-426614174000")
            String archiveId
    );

    @Operation(
            summary = "아이콘 목록 조회",
            description = "사용 가능한 아이콘 목록을 조회합니다. 아이콘은 아카이브 생성 시 선택할 수 있는 아이콘입니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회됨",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ReadIconListResponse.class))
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
    ResponseEntity<List<ReadIconResponse>> getIconAll();
}
