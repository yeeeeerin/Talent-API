package talent.com.auth.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadDownloadService {

    String storeFile(MultipartFile file);

    Resource loadFileAsResource(String fileName);

}
