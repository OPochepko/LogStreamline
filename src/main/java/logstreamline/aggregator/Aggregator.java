package logstreamline.aggregator;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class Aggregator<T, V> implements BiConsumer<T, Map<V, AtomicInteger>> {

    private Function<T, V> function;

    public Aggregator(Function<T, V> function) {
        this.function = function;
    }

    @Override
    public void accept(T t, Map<V, AtomicInteger> map) {
        map.merge(function.apply(t), new AtomicInteger(1),
                (oldVal, newVal) -> new AtomicInteger(oldVal.addAndGet(newVal.get())));
    }
}