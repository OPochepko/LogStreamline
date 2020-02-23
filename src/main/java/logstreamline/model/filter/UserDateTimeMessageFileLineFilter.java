package logstreamline.model.filter;

import logstreamline.model.fileline.UserDateTimeMessageFileLine;

import java.util.function.Predicate;

@FunctionalInterface
public interface UserDateTimeMessageFileLineFilter<T extends UserDateTimeMessageFileLine> extends Predicate<T> {

    default UserDateTimeMessageFileLineFilter<T> and(UserDateTimeMessageFileLineFilter<? super T> other) {
        return (t) -> test(t) && other.test(t);
    }
}
