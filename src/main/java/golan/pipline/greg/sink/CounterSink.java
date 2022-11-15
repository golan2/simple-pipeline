package golan.pipline.greg.sink;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
public class CounterSink<T> implements Sink<T> {
    private final String name;
    private final AtomicInteger counter = new AtomicInteger();

    @Override
    public void write(T value) {
        log.info("{}: {}", name, counter.incrementAndGet());
    }
}
