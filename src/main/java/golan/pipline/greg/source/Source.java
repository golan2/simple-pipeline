package golan.pipline.greg.source;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface Source {
    void begin() throws FileNotFoundException;
    List<String> fetch() throws IOException;
}
