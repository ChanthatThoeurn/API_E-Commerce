package ecomes.iteecomest.feature.file;
import ecomes.iteecomest.feature.file.dto.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {
    @Value("${file.storage-location}")
    private String fileLocation;

    @Value("${file.base-uri}")
    private String baseUri;
    @Override
    public FileUploadResponse upload(MultipartFile file) {
        // file information
        String fileName = UUID.randomUUID().toString();

        String originalFileName = file.getOriginalFilename();

        if (originalFileName == null || !originalFileName.contains(".")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid file name"
            );
        }

        String ext = originalFileName.substring(
                originalFileName.lastIndexOf(".") + 1
        );

        fileName += "." + ext;
        Path path = Paths.get(fileLocation + fileName);

        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "File has been failed to upload");
        }
        return FileUploadResponse.builder()
                .name(fileName)
                .size(file.getSize())
                .mediaType(file.getContentType())
                .uri(baseUri+"/"+fileName)
                .build();
    }

    @Override
    public List<FileUploadResponse> uploadMultiFile(MultipartFile[] files) {
        List<FileUploadResponse> responses = new ArrayList<>();
        for (MultipartFile file : files) {

            String originalName = file.getOriginalFilename();

            if (originalName == null || !originalName.contains(".")) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Invalid file name"
                );
            }

            String ext = originalName.substring(
                    originalName.lastIndexOf(".") + 1
            );

            String fileName = UUID.randomUUID() + "." + ext;

            Path path = Paths.get(fileLocation, fileName);

            try {
                Files.createDirectories(path.getParent());
                Files.copy(file.getInputStream(), path,
                        StandardCopyOption.REPLACE_EXISTING);
                responses.add(
                        FileUploadResponse.builder()
                                .name(fileName)
                                .size(file.getSize())
                                .mediaType(file.getContentType())
                                .uri(fileLocation + fileName)
                                .build()
                );

            } catch (IOException e) {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Failed to upload file: " + originalName
                );
            }
        }

        return responses;
    }

    @Override
    public void deleteFile(String fileName) {
        Path path = Paths.get(fileLocation + fileName);
        try{
            boolean deleted = Files.deleteIfExists(path);
            if(!deleted){
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "File not found");
            }
        }catch (IOException e){
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to delete file: " + fileName
            );
        }

    }

}
