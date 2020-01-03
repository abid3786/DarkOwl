package ask.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.jdbc.core.RowCallbackHandler;

@Component
public class UrlFileProcessor {

    @Autowired
    private TextLineRepository textLineRepository;

    @Autowired
    private DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private long recordCount = 0;


    /**
     * This method uses Files Streaming mechanism to retrieve
     * one line at at time from the uploaded file and processes it
     * @param filePath path to the uplodated file
     * @return time in milliseconds it took to process all lines in the file
     */
    public long processFile(Path filePath) {
        long timeStarted = System.currentTimeMillis();
        long timeElapsed = 0;

        try (Stream<String> stream = Files.lines(filePath)) {
            stream.parallel().forEach(sl -> {
                Progress.incrementProcessed();
                if((sl != null) && (!"".equals(sl.trim()))) {
                    try {
                        textLineRepository.save(new TextLine(sl));
                    } catch (Throwable sicv) {
                        System.out.println("Duplicate line - " + sl);
                        //ignored
                    }
                }
            });
            writeFile();
            timeElapsed = System.currentTimeMillis() - timeStarted;
            Progress.setTime(timeElapsed);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return timeElapsed;
    }

    public void countLines(Path filePath) {
        try (Stream<String> stream = Files.lines(filePath)) {
            stream.parallel().forEach(sl -> {
                Progress.incrementCount();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long writeFile() {
        String fullFileName = this.getDataFile();
        this.recordCount = 0;
        try (FileOutputStream outputStream = new FileOutputStream(fullFileName)) {
            jdbcTemplate.query(new StatementStream("SELECT TEXT_LINE FROM TEXT_LINE"), new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet resultSet) throws SQLException {
                    if (resultSet != null) {
                        do {
                            recordCount++;
                            try {
                                outputStream.write(resultSet.getString(1).getBytes());
                                outputStream.write("\n".getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } while(resultSet.next());
                    }
                }
            });
        } catch (Exception fn) {
            fn.printStackTrace();
        }
        return recordCount;
    }

    public String getDataFile() {
        File dirLocation = new File("tmp/download_folder" + File.separator);
        if(! dirLocation.getAbsoluteFile().exists()) {
            dirLocation.mkdirs();
        }
        return dirLocation.getAbsolutePath() + File.separator + "FileContainsUniqueUrls.data";
    }

    public void deleteAll() {
        this.textLineRepository.deleteAll();
    }

}
