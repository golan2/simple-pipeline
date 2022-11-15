package golan.pipline.greg.source;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@NoArgsConstructor
public class LocalFileSource implements Source, AutoCloseable {
    private static final int BULK_SIZE = 3;
    private static final String FILENAME = "input.txt";

    private BufferedReader bufferedReader;

    public void begin() throws FileNotFoundException {
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(FILENAME);
        if (inputStream == null) throw new FileNotFoundException("Can't find " + FILENAME + " in classpath");
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public List<String> fetch() throws IOException {
        final ArrayList<String> bulk = new ArrayList<>(BULK_SIZE);
        String line;
        while ( (line = this.bufferedReader.readLine()) != null) {
            bulk.add(line);
            if (bulk.size() >= BULK_SIZE) break;
        }
        return bulk;
    }

    @Override
    public void close() throws IOException {
        if (this.bufferedReader != null) {
            this.bufferedReader.close();
        }
    }
}
