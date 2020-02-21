package logstreamline.filter;

import logstreamline.fileline.TestLogFileLine;

import java.util.function.Predicate;

public class UserFileLineFilter implements Predicate<TestLogFileLine> {

    private final String user;

    public UserFileLineFilter(String user) {
        this.user = user;
    }

    @Override
    public boolean test(TestLogFileLine testLogFileLine) {
        return testLogFileLine.getUser().equals(user);
    }


}
