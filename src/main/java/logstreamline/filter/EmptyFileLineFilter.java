package logstreamline.filter;

import java.util.function.Predicate;

public class EmptyFileLineFilter<T> implements Predicate<T> {

    @Override
    public boolean test(T t) {
        return true;
    }
}
