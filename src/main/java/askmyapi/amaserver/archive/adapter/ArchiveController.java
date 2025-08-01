package askmyapi.amaserver.archive.adapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ArchiveController implements ArchiveSpecs{

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
    public ResponseEntity<Page<ReadArchiveResponse>> getArchives(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        return ResponseEntity.ok(
                new PageImpl<>(List.of(
                        new ReadArchiveResponse(
                                UUID.randomUUID().toString(),
                                "Archive Title",
                                "ICON001",
                                List.of(
                                        new ReadArchiveResponse.Spec(
                                            UUID.randomUUID().toString(),
                                            "Spec Title",
                                            "Spec Description",
                                            true
                                        ),
                                        new ReadArchiveResponse.Spec(
                                            UUID.randomUUID().toString(),
                                            "Another Spec Title",
                                            "Another Spec Description",
                                            false
                                        )
                                )
                        )
                ), PageRequest.of(page, size), ((long) page * size) + 1)
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
}
