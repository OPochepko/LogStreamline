package logstreamline.splitter;

import logstreamline.fileline.UserDateTimeMessageFileLine;

import java.time.LocalDateTime;

public class LogLineSplitter implements LineSplitter<UserDateTimeMessageFileLine> {

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
