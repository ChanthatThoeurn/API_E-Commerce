package ecomes.iteecomest.feature.file;
import ecomes.iteecomest.feature.file.dto.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/files")
public class FileUploadController {
    private final FileUploadService fileUploadService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileUploadResponse upload(
            @RequestPart("file") MultipartFile file) {
        return fileUploadService.upload(file);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            value = "/multiple",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public List<FileUploadResponse> uploadMultiple(
            @RequestParam("files") MultipartFile[] files) {
        return fileUploadService.uploadMultiFile(files);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{name}")
    public void deleteFile(
            @PathVariable String name) {
        fileUploadService.deleteFile(name);
    }
}
