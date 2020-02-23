package logstreamline.model.splitter;

import logstreamline.model.fileline.UserDateTimeMessageFileLine;

import java.time.LocalDateTime;

/**
 * Implementation of LineSplitter for UserDateTieMessageFileLine. Provide extraction of values for new UserDateTimeMessageFileLine instance
 * (mapper in some way)
 */
public class LogLineSplitter implements LineSplitter<UserDateTimeMessageFileLine> {
    /**
     * @param fileLine String that include values of UserDateTimeMessageFileLine instance
     * @return UserDateTimeMessageFileLine instance according to substring parameters
     */
    @Override
    public UserDateTimeMessageFileLine apply(String fileLine) {
        String user = getUserSubstring(fileLine);
        String message = getMessageSubstring(fileLine);
        LocalDateTime dateTime = LocalDateTime.parse(getDateTimeSubstring(fileLine));
        return new UserDateTimeMessageFileLine(user, message, dateTime, fileLine);
    }

    private String getMessageSubstring(String string) {
        return string.substring(string.lastIndexOf("-") + 2);
    }

    private String getDateTimeSubstring(String string){
        return string.substring(string.indexOf(";") + 2, string.lastIndexOf("-") - 1);
    }

    private String getUserSubstring(String string){
        return string.substring(string.indexOf(":") + 2,string.indexOf(";"));
    }


}
