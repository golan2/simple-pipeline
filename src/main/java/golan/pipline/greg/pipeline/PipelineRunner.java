package golan.pipline.greg.pipeline;

import com.fasterxml.jackson.core.JsonProcessingException;
import golan.pipline.greg.Person;
import golan.pipline.greg.mapper.IncrementAge;
import golan.pipline.greg.mapper.Mapper;
import golan.pipline.greg.parser.Parser;
import golan.pipline.greg.sink.CounterSink;
import golan.pipline.greg.sink.Sink;
import golan.pipline.greg.source.Source;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class PipelineRunner {
    private final Source source;
    private final Parser<Person> parser;
    private final Sink<String> failed = new CounterSink<>("Failed");
    private final Sink<Person> success = new CounterSink<>("Success");
    private final Mapper<Person> incrementAge = new IncrementAge();

    public void go() {
        try {
            source.begin();
            while (true) {
                final List<String> bulk = source.fetch();

                bulk
                        .stream()
                        .map(this::parse)
                        .filter(Objects::nonNull)
                        .map(this::incrementAge);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Person parse(String s) {
        try {
            return parser.parse(s);
        } catch (JsonProcessingException e) {
            failed.write(s);
            return null;
        }
    }

    private Person incrementAge(Person p) {
        return this.incrementAge.apply(p);
    }
}
