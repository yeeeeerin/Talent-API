package talent.com.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import talent.com.auth.exception.FileDownloadException;
import talent.com.auth.exception.FileUploadException;
import talent.com.auth.properties.FileUploadProperties;
import talent.com.auth.service.FileUploadDownloadService;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileUploadDownloadServiceImpl implements FileUploadDownloadService {


    private final Path fileLocation;

    @Autowired
    public FileUploadDownloadServiceImpl(FileUploadProperties prop) {
        fileLocation = Paths.get(prop.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileLocation);
        } catch (Exception e) {
            throw new FileUploadException("파일을 업로드할 디렉토리를 생성하지 못했습니다.", e);
        }
    }


    @Override
    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // 파일명에 부적합 문자가 있는지 확인한다.
            if (fileName.contains("..")) {
                throw new FileUploadException("파일명에 부적합 문자가 포함되어 있습니다. " + fileName);
            }

            Path targetLocation = fileLocation.resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName)
                    .toUriString();

            return fileDownloadUri;
        } catch (Exception e) {
            throw new FileUploadException("[" + fileName + "] 파일 업로드에 실패하였습니다. 다시 시도하십시오.", e);
        }


    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = fileLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.");
            }
        } catch (MalformedURLException e) {
            throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.", e);
        }
    }


}
