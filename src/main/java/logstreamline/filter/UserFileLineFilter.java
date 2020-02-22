package logstreamline.filter;

import logstreamline.fileline.UserDateTimeMessageFileLine;

import java.util.function.Predicate;

public class UserFileLineFilter implements Predicate<UserDateTimeMessageFileLine> {

    private final String user;

    public UserFileLineFilter(String user) {
        this.user = user;
    }

    @Override
    public boolean test(UserDateTimeMessageFileLine userDateTimeMessageFileLine) {
        return userDateTimeMessageFileLine.getUser().equals(user);
    }


}
