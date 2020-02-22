package logstreamline.fileline;

import java.time.LocalDateTime;

public class UserDateTimeMessageFileLine {
    private final String user;
    private final String message;
    private LocalDateTime dateTime;
    private final String fileLine;

    public UserDateTimeMessageFileLine(String user, String message, LocalDateTime dateTime, String fileLine) {

        this.user = user;
        this.message = message;
        this.dateTime = dateTime;
        this.fileLine = fileLine;
    }

    public String getFileLine() {
        return fileLine;
    }

    void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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
                "fileLine='" + fileLine + '\'' +
                '}';
    }
}
