package logstreamline.aggregator;

import logstreamline.fileline.FileLine;

import java.util.concurrent.atomic.AtomicInteger;

public interface StringAtomicToMapAggregator<T extends FileLine> extends ToMapAggregator<T, String, AtomicInteger> {
    default StringAtomicToMapAggregator<T> andThen(StringAtomicToMapAggregator<? super T> after) {
        return (l, r) -> {
            accept(l, r);
            after.accept(l, r);
        };
    }
}
