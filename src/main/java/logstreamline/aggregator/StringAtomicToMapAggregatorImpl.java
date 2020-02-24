package logstreamline.aggregator;

import logstreamline.fileline.FileLine;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * Implementation of StringAtomicToMapAggregator functional interface. Accepts two input arguments (second one implements Map interface)
 * and returns no result. Work by side affect that provide accumulation of quantity into second argument map.
 *
 * @param <T> - type of first argument
 */


public class StringAtomicToMapAggregatorImpl<T extends FileLine> implements StringAtomicToMapAggregator<T> {
    /**
     * Function that map first argument to String for subsequent accumulation
     */
    private Function<T, String> argumentToMapKey;

    public StringAtomicToMapAggregatorImpl(Function<T, String> argumentToMapKey) {
        this.argumentToMapKey = argumentToMapKey;
    }

    /**
     * functional method. That map first argument to String using argumentToMapKey and then accumulate quantity of this String to map
     *
     * @param t   - given parameter
     * @param map - map to accumulate
     */
    public void accept(T t, Map<String, AtomicInteger> map) {
        map.merge(argumentToMapKey.apply(t), new AtomicInteger(1),
                (oldVal, newVal) -> new AtomicInteger(oldVal.addAndGet(newVal.get())));
    }


}
