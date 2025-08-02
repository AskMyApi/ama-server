package askmyapi.amaserver.archive.adapter.in.web.spec;

import askmyapi.amaserver.archive.adapter.in.web.ReadIconResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "아이콘 목록 읽기 응답")
public class ReadIconListResponse extends ArrayList<ReadIconResponse> {
}
