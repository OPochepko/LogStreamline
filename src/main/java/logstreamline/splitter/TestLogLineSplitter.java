package logstreamline.splitter;

import logstreamline.fileline.TestLogFileLine;

import java.time.LocalDateTime;

public class TestLogLineSplitter implements LineSplitter<TestLogFileLine> {

    @Override
    public TestLogFileLine apply(String logLine) {

        String user = getUserSubstring(logLine);
        String message = getMessageSubstring(logLine);
        LocalDateTime dateTime = LocalDateTime.parse(getDateTimeSubstring(logLine));


        return new TestLogFileLine(user,message,dateTime,logLine);
    }

    private String getMessageSubstring(String string){
        return string.substring(string.lastIndexOf("-") + 2);
    }

    private String getDateTimeSubstring(String string){
        return string.substring(string.indexOf(";") + 2, string.lastIndexOf("-") - 1);
    }
     private String getUserSubstring(String string){
        return string.substring(string.indexOf(":") + 2,string.indexOf(";"));
     }


}
