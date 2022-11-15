package golan.pipline.greg.pipeline;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PipelineStep<S,T> {

    private final PipelineStep<T,?> nextStep;

    public void put(S item) {

    }
}
