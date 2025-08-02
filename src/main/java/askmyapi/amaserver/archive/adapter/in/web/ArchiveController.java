package askmyapi.amaserver.archive.adapter.in.web;

import askmyapi.amaserver.archive.adapter.in.web.spec.ArchiveSpecs;
import askmyapi.amaserver.util.InfiniteScroll;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ArchiveController implements ArchiveSpecs {

    @Override
    @PostMapping("/archives")
    public ResponseEntity<Void> createArchive(@RequestBody CreateArchiveRequest request) {
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/archives/{archive-id}")
    public ResponseEntity<Void> updateArchive(
            @PathVariable("archive-id") String archiveId,
            @RequestBody UpdateArchiveRequest request) {
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/archives/{archive-id}")
    public ResponseEntity<Void> deleteArchive(
            @PathVariable("archive-id") String archiveId) {
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/archives")
    public ResponseEntity<InfiniteScroll<ReadArchiveResponse>> getArchives(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "last-id", required = false) String lastId,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        return ResponseEntity.ok(
                InfiniteScroll.<ReadArchiveResponse>builder()
                        .data(List.of(
                                        new ReadArchiveResponse(
                                                UUID.randomUUID().toString(),
                                                "Archive Title",
                                                "ICON001",
                                                "https://example.com/icon.svg"
                                        ),
                                        new ReadArchiveResponse(
                                                UUID.randomUUID().toString(),
                                                "Another Archive",
                                                "ICON002",
                                                "https://example.com/another-icon.svg"
                                        ),
                                        new ReadArchiveResponse(
                                                UUID.randomUUID().toString(),
                                                "Third Archive",
                                                "ICON003",
                                                "https://example.com/third-icon.svg"
                                        )
                                )
                        )
                        .hasNext(false)
                        .build()
        );
    }

    @Override
    @PostMapping("/archives/{archive-id}/specs")
    public ResponseEntity<Void> addSpecToArchive(
            @PathVariable("archive-id") String archiveId,
            @RequestBody AddSpecRequest request) {
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/archives/{archive-id}/specs/{spec-id}")
    public ResponseEntity<Void> deleteSpecFromArchive(
            @PathVariable("archive-id") String archiveId,
            @PathVariable("spec-id") String specId) {
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/archives/{archive-id}")
    public ResponseEntity<ReadArchiveDetailResponse> getArchiveDetail(
            @PathVariable("archive-id") String archiveId) {
        return ResponseEntity.ok(new ReadArchiveDetailResponse(
                archiveId,
                "내 아카이브",
                "ICON001",
                "https://example.com/icon.svg",
                List.of(
                        new ReadArchiveDetailResponse.Spec(
                                UUID.randomUUID().toString(),
                                "https://petstore.swagger.io/",
                                "SWAGGER",
                                true
                        ),
                        new ReadArchiveDetailResponse.Spec(
                                UUID.randomUUID().toString(),
                                "https://example.com/rest-docs",
                                "REST_DOCS",
                                false
                        )
                ))
        );
    }

    @Override
    @GetMapping("/archives/icons")
    public ResponseEntity<List<ReadIconResponse>> getIconAll() {
        return null;
    }
}
