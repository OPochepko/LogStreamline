package logstreamline.aggregator;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class AtomicAggregator<T, V> implements BiConsumer<T, Map<V, AtomicInteger>> {

    private Function<T, V> argumentToMapKey;

    public AtomicAggregator(Function<T, V> argumentToMapKey) {
        this.argumentToMapKey = argumentToMapKey;
    }

    @Override
    public void accept(T t, Map<V, AtomicInteger> map) {
        map.merge(argumentToMapKey.apply(t), new AtomicInteger(1),
                (oldVal, newVal) -> new AtomicInteger(oldVal.addAndGet(newVal.get())));
    }
}
