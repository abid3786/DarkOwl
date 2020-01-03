package ask.file.upload;

import ask.model.Progress;
import ask.model.UrlFileProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class is main controller, all functions that are publically
 * exposed via ReST interface are here.
 *
 * @author Abid Khan
 */

@RestController
public class FileHandlerController {

    @Autowired
    private FileHandlerService fileService;

    @Autowired
    private UrlFileProcessor urlFileProcessor;

    /*@GetMapping("/")
    public String index() {
        return "index.html";
    }*/

    @GetMapping("/status")
    public String status() {
        return Progress.status();
    }

    @PostMapping("/uploadFileForJavaStream")
    @ResponseStatus( HttpStatus.OK )
    public void uploadFileJavaStreaming(@RequestParam("urlFile") MultipartFile file) {

        String orgFileName = file.getOriginalFilename();
        if(orgFileName != null && (! "".equals(orgFileName.trim()))) {
            Progress.reset();
            orgFileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path savedLocation = this.fileService.uploadFile(orgFileName, file);
            this.urlFileProcessor.countLines(savedLocation);
            System.out.println("Time taken to process whole file (ms) " +
            this.urlFileProcessor.processFile(savedLocation));
        }
    }

    @RequestMapping("/download/{fileName:.+}")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response,
                             @PathVariable("fileName") String fileName) throws IOException {
        File f = new File(this.urlFileProcessor.getDataFile());
        if(f.getAbsoluteFile().exists()) {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + f.getName() + "\""));
            response.setContentLength((int) f.length());
            try (OutputStream os1 = response.getOutputStream()) {
                Files.copy(Paths.get(f.toURI()), os1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}