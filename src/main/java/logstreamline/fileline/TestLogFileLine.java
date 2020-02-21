package logstreamline.fileline;

import java.time.LocalDateTime;

public class TestLogFileLine {
    private final String user;
    private final String message;
    private final LocalDateTime dateTime;
    private final String fileLine;

    public TestLogFileLine(String user, String message, LocalDateTime dateTime, String fileLine) {

        this.user = user;
        this.message = message;
        this.dateTime = dateTime;
        this.fileLine = fileLine;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "TestLogFileLine{" +
                "user='" + user + '\'' +
                ", message='" + message + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
