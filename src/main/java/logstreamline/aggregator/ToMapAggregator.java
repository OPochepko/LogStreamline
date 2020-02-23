package logstreamline.aggregator;

import java.util.Map;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface ToMapAggregator<T, K, V> extends BiConsumer<T, Map<K, V>> {

}
