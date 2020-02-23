package logstreamline.model.fileline;

import com.google.common.base.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDateTimeMessageFileLine)) return false;
        UserDateTimeMessageFileLine fileLine = (UserDateTimeMessageFileLine) o;
        return Objects.equal(user, fileLine.user) &&
                Objects.equal(message, fileLine.message) &&
                Objects.equal(dateTime, fileLine.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(user, message, dateTime);
    }
}
