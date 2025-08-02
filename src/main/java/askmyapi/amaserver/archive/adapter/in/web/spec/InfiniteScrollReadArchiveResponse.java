package askmyapi.amaserver.archive.adapter.in.web.spec;

import askmyapi.amaserver.archive.adapter.in.web.ReadArchiveResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "무한 스크롤 응답")
public class InfiniteScrollReadArchiveResponse {

    @ArraySchema(schema = @Schema(implementation = ReadArchiveResponse.class))
    private List<ReadArchiveResponse> data;

    @Schema(description = "다음 페이지 존재 여부", example = "true")
    private boolean hasNext;

    public InfiniteScrollReadArchiveResponse(List<ReadArchiveResponse> data, boolean hasNext) {
        this.data = data;
        this.hasNext = hasNext;
    }
}