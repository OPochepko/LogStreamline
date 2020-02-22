package logstreamline.aggregator;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class StringAggregator<T> implements BiConsumer<T, Map<String, AtomicInteger>> {

    private Function<T, String> function;

    public StringAggregator(Function<T, String> function) {
        this.function = function;
    }

    @Override
    public void accept(T t, Map<String, AtomicInteger> map) {
        map.merge(function.apply(t), new AtomicInteger(1),
                (oldVal, newVal) -> new AtomicInteger(oldVal.addAndGet(newVal.get())));
    }
}
