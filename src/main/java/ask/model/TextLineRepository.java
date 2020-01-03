package ask.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TextLineRepository extends CrudRepository<TextLine, String> {
}
