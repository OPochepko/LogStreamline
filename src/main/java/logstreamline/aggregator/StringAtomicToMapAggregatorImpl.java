package logstreamline.aggregator;

import logstreamline.fileline.FileLine;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class StringAtomicToMapAggregatorImpl<T extends FileLine> implements StringAtomicToMapAggregator<T> {

    private Function<T, String> argumentToMapKey;

    public StringAtomicToMapAggregatorImpl(Function<T, String> argumentToMapKey) {
        this.argumentToMapKey = argumentToMapKey;
    }

    public void accept(T t, Map<String, AtomicInteger> map) {
        map.merge(argumentToMapKey.apply(t), new AtomicInteger(1),
                (oldVal, newVal) -> new AtomicInteger(oldVal.addAndGet(newVal.get())));
    }



}
