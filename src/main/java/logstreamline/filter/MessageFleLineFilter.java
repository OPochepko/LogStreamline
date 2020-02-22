package logstreamline.filter;

import logstreamline.fileline.UserDateTimeMessageFileLine;

import java.util.function.Predicate;

public class MessageFleLineFilter implements Predicate<UserDateTimeMessageFileLine> {
    private final String regex;

    public MessageFleLineFilter(String regex) {
        this.regex = regex;
    }

    @Override
    public boolean test(UserDateTimeMessageFileLine userDateTimeMessageFileLine) {
        return userDateTimeMessageFileLine.getMessage().matches(regex);
    }
}
