package practice.fundingboost2.common.file;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    List<String> uploadMultipleFiles(List<MultipartFile> files);

    String uploadSingleFile(MultipartFile file);

    void deleteFiles(List<String> urls);
}
