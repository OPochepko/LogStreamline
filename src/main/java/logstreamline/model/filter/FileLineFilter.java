package logstreamline.model.filter;

import logstreamline.model.fileline.FileLine;

import java.util.function.Predicate;

@FunctionalInterface
public interface FileLineFilter<T extends FileLine> extends Predicate<T> {

}
