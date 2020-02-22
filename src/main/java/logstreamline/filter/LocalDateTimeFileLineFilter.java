package logstreamline.filter;

import logstreamline.fileline.UserDateTimeMessageFileLine;

import java.time.LocalDateTime;
import java.util.function.Predicate;

public class LocalDateTimeFileLineFilter implements Predicate<UserDateTimeMessageFileLine> {

    private final LocalDateTime from;
    private final LocalDateTime to;

    public LocalDateTimeFileLineFilter(LocalDateTime from, LocalDateTime to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean test(UserDateTimeMessageFileLine userDateTimeMessageFileLine) {
        return userDateTimeMessageFileLine.getDateTime().isAfter(from) && userDateTimeMessageFileLine.getDateTime().isBefore(to);
    }


}
