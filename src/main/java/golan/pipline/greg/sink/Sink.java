package golan.pipline.greg.sink;

public interface Sink<T> {
    void write(T value);
}
