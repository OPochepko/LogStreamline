package logstreamline.filter;

import logstreamline.fileline.UserDateTimeMessageFileLine;

import java.time.LocalDateTime;

/**
 * UserDateTimeMessageFileLineFilter implementation  for filter UserDateTimeMessageFileLine instances by dateTime field.
 */
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
