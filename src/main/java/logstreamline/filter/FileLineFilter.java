package logstreamline.filter;

import logstreamline.fileline.FileLine;

import java.util.function.Predicate;

@FunctionalInterface
public interface FileLineFilter<T extends FileLine> extends Predicate<T> {

}
