package golan.pipline.greg.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import golan.pipline.greg.Person;

public class PersonParser implements Parser<Person> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Person parse(String s) throws JsonProcessingException {
        return objectMapper.readValue(s, Person.class);
    }
}
