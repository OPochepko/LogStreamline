package logstreamline.model.splitter;

import java.util.function.Function;

/**
 * Functional interface with function method apply(String string) that should provide conversion part of given string to T type instance
 *
 * @param <R> - type of result
 */
@FunctionalInterface
public interface LineSplitter<R> extends Function<String, R> {
    /**
     * Applies function to given argument
     *
     * @param string - function parameter
     * @return
     */
    R apply(String string);

}
