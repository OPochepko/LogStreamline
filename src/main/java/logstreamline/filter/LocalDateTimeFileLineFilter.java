package logstreamline.filter;

import logstreamline.fileline.UserDateTimeMessageFileLine;

import java.time.LocalDateTime;
import java.util.function.Predicate;

public class LocalDateTimeFileLineFilter<T extends UserDateTimeMessageFileLine> implements Predicate<T> {

    private final LocalDateTime from;
    private final LocalDateTime to;

    public LocalDateTimeFileLineFilter(LocalDateTime from, LocalDateTime to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean test(T userDateTimeMessageFileLine) {
        return userDateTimeMessageFileLine.getDateTime().isAfter(from) && userDateTimeMessageFileLine.getDateTime().isBefore(to);
    }


}
