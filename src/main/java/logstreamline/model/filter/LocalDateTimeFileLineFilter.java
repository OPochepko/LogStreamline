package logstreamline.model.filter;

import logstreamline.model.fileline.UserDateTimeMessageFileLine;

import java.time.LocalDateTime;

public class LocalDateTimeFileLineFilter implements UserDateTimeMessageFileLineFilter<UserDateTimeMessageFileLine> {

    private final LocalDateTime from;
    private final LocalDateTime to;

    public LocalDateTimeFileLineFilter(LocalDateTime from, LocalDateTime to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean test(UserDateTimeMessageFileLine fileLine) {
        return fileLine.getDateTime().isAfter(from) && fileLine.getDateTime().isBefore(to);
    }

}
