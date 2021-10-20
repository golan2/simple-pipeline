package golan.pipline.greg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

public class AllInOneClass {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        final ArrayList<String> source = new ArrayList<>();
        System.out.println(MAPPER.writeValueAsString(new Person("a", 0)));
        source.add("{\"name\":\"a\",\"age\":0}\n");
        source.add("{\"name\":\"b\",\"age\":1}\n");
        source.add("{\"name\":\"c\",\"age\":2}\n");
        source.add("{\"name\":\"d\",\"age\":3}\n");
        source.add("{\"name\":\"e\",\"age\":4}\n");
        source.add("{\"name\":\"f\",\"age\":5}\n");

        final Counters result = source
                .stream()
                .map(AllInOneClass::parseAndTransform)
                .map(AllInOneClass::debug)
                .reduce((a, b) -> {
                    final Counters res = new Counters(a.getSuccess() + b.getSuccess(), a.getFail() + b.getFail());
                    System.out.println("Reduce " + a + " , " + b + "  ==>  " + res);
                    return res;
                })
                .orElse(new Counters(0, 0));
        System.out.println(result);
    }

    private static Counters debug(Counters a) {
        System.out.println("[debug] " + a);
        return a;
    }

    private static Counters parseAndTransform(String s) {
        try {
            final Person person = MAPPER.readValue(s, Person.class);
            if ("c".equals(person.getName())) throw new IllegalArgumentException("C");
            return Counters.SUCCESS;
        } catch (Exception e) {
            return Counters.FAILURE;
        }
    }

    @Data
    @AllArgsConstructor
    private static class Counters {
        public static final Counters SUCCESS = new Counters(1,0);
        public static final Counters FAILURE = new Counters(0,1);

        private int success;
        private int fail;

        @Override
        public String toString() {
            return "(" + success + "," + fail + ')';
        }
    }
}
