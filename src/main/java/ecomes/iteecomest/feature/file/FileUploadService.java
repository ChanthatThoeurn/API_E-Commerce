package ecomes.iteecomest.feature.file;

import ecomes.iteecomest.feature.file.dto.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {
    FileUploadResponse upload(MultipartFile file);
    List<FileUploadResponse> uploadMultiFile(MultipartFile[] files);
    void deleteFile(String fileName);
}
