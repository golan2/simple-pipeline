package golan.pipline.greg.parser;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Parser<T> {
    T parse(String s) throws JsonProcessingException;
}
