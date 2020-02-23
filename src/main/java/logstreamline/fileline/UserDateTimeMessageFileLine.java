package logstreamline.fileline;

import java.time.LocalDateTime;

public class UserDateTimeMessageFileLine extends FileLine {
    private final String user;
    private final String message;
    private LocalDateTime dateTime;

    public UserDateTimeMessageFileLine(String user, String message, LocalDateTime dateTime, String fileLine) {
        super(fileLine);

        this.user = user;
        this.message = message;
        this.dateTime = dateTime;
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


}
