package logstreamline.filter;

import logstreamline.fileline.UserDateTimeMessageFileLine;

public class MessageFleLineFilter implements UserDateTimeMessageFileLineFilter<UserDateTimeMessageFileLine> {
    private final String regex;

    public MessageFleLineFilter(String regex) {
        this.regex = regex;
    }

    @Override
    public boolean test(UserDateTimeMessageFileLine fileLine) {
        return fileLine.getMessage().matches(regex);
    }
}
