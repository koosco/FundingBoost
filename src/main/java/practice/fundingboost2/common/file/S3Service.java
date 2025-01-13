package practice.fundingboost2.common.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service implements FileService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public List<String> uploadMultipleFiles(List<MultipartFile> files) {
        return files.stream()
            .map(file -> {
                try {
                    return uploadSingleFile(file);
                } catch (Exception e) {
                    log.error("Failed to upload file: {}", file.getOriginalFilename());
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .toList();
    }

    @Override
    public String uploadSingleFile(MultipartFile file) {
        validateFile(file);
        ObjectMetadata meta = getMetadata(file);

        String key = generateKeyName(file.getOriginalFilename());
        try (InputStream inputStream = file.getInputStream()) {
            amazonS3.putObject(bucket, key, inputStream, meta);
            return amazonS3.getUrl(bucket, key).toString();
        } catch (Exception e) {
            log.error("Error during file upload: {}", e.getMessage(), e);
            throw new RuntimeException("File upload failed", e);
        }
    }

    private static ObjectMetadata getMetadata(MultipartFile file) {
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType(file.getContentType());
        meta.setContentLength(file.getSize());
        return meta;
    }

    private static void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty() || file.getOriginalFilename() == null) {
            throw new RuntimeException();
        }
    }

    private String generateKeyName(String originalFilename) {
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        return UUID.randomUUID() + extension;
    }

    @Override
    public void deleteFiles(List<String> urls) {
        urls.forEach(url -> {
            String key = url.substring(url.lastIndexOf("/") + 1);
            amazonS3.deleteObject(bucket, key);
        });
    }
}