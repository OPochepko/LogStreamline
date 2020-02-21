package logstreamline.filter;

import logstreamline.fileline.TestLogFileLine;

import java.util.function.Predicate;

public class MessageFleLineFilter implements Predicate<TestLogFileLine> {
    private final String regex;

    public MessageFleLineFilter(String regex) {
        this.regex = regex;
    }

    @Override
    public boolean test(TestLogFileLine testLogFileLine) {
        return testLogFileLine.getMessage().matches(regex);
    }
}
