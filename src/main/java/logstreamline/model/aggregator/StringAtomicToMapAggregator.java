package logstreamline.model.aggregator;

import logstreamline.model.fileline.FileLine;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Extension of BiConsumer. Represents an operation that accepts two input arguments (second one implements Map<String,AtomicInteger>
 * interface and returns no result.
 * This is functional interface with functional method accept
 *
 * @param <T> - type of first argument
 */
@FunctionalInterface
public interface StringAtomicToMapAggregator<T extends FileLine> extends ToMapAggregator<T, String, AtomicInteger> {
    /**
     * Returns a composed StringAtomicToMapAggregator that performs, in sequence, this
     * operation followed by the after operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed StringAtomicToMapAggregator that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default StringAtomicToMapAggregator<T> andThen(StringAtomicToMapAggregator<? super T> after) {
        Objects.requireNonNull(after);
        return (l, r) -> {
            accept(l, r);
            after.accept(l, r);
        };
    }
}
