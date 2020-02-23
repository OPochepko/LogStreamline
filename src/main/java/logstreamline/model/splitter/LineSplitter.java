package logstreamline.model.splitter;

import java.util.function.Function;
@FunctionalInterface
public interface LineSplitter<T> extends Function<String, T> {

    T apply(String string);

}
