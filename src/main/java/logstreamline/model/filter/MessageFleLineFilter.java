package logstreamline.model.filter;

import logstreamline.model.fileline.UserDateTimeMessageFileLine;
/**
 * UserDateTimeMessageFileLineFilter implementation  for filter UserDateTimeMessageFileLine instances by message field by matching to given regex..
 */
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
