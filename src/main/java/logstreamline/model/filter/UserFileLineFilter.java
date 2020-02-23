package logstreamline.model.filter;

import logstreamline.model.fileline.UserDateTimeMessageFileLine;
/**
 * UserDateTimeMessageFileLineFilter implementation for filter UserDateTimeMessageFileLine instances by user field.
 */
public class UserFileLineFilter implements UserDateTimeMessageFileLineFilter<UserDateTimeMessageFileLine> {

    private final String user;

    public UserFileLineFilter(String user) {
        this.user = user;
    }

    @Override
    public boolean test(UserDateTimeMessageFileLine fileLine) {
        return fileLine.getUser().equals(user);
    }


}
