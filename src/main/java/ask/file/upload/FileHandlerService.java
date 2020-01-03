package ask.file.upload;

import ask.excpt.UrlManagerException;
import ask.model.UrlFileProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileHandlerService {
    @Value("${file.upload-dir:${user.home}}")
    private String uploadDir;

    public Path uploadFile(String orgFileName , MultipartFile file) {
        Path copyLocation = null;
        try {
            File dirLocation = new File(this.uploadDir + File.separator);
            if(! dirLocation.getAbsoluteFile().exists()) {
                dirLocation.mkdirs();
            }
            copyLocation = Paths.get(File.createTempFile(StringUtils.cleanPath(orgFileName), ".urls", dirLocation).toURI());
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UrlManagerException("File could not be stored " + file.getOriginalFilename());
        }
        return copyLocation;
    }
}
