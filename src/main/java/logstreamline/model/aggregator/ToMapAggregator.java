package logstreamline.model.aggregator;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Extension of BiConsumer. Represents an operation that accepts two input arguments (second one implements Map interface)
 * and returns no result.
 * This is functional interface with functional method accept
 *
 * @param <T> - type of first argument
 * @param <K> - type of Map key
 * @param <V> - type of Map value
 */
@FunctionalInterface
public interface ToMapAggregator<T, K, V> extends BiConsumer<T, Map<K, V>> {

}
