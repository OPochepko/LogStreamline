package logstreamline.filter;

import logstreamline.fileline.TestLogFileLine;

import java.time.LocalDateTime;
import java.util.function.Predicate;

public class LocalDateTimeFileLineFilter implements Predicate<TestLogFileLine> {
    private final LocalDateTime from;
    private final LocalDateTime to;

    public LocalDateTimeFileLineFilter(LocalDateTime from, LocalDateTime to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean test(TestLogFileLine testLogFileLine) {
        return testLogFileLine.getDateTime().isAfter(from) && testLogFileLine.getDateTime().isBefore(to);
    }


}
