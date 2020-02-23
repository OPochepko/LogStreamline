package logstreamline.model.splitter;

import logstreamline.model.fileline.UserDateTimeMessageFileLine;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


class LogLineSplitterTest {

    LogLineSplitter sut = new LogLineSplitter();


    @Test
    void apply_givenLineAndUserDateTimeMessageLine_LineShouldBeSplittedCorrectlyToCreateNewUserDateTimeMessageInstanceSameAsGiven() {
        //given
        String line = "[main] DEBUG - User : ElonTusk; 2020-04-26T09:48:55.7372445 - ElonTusk sold another Tesla.";
        String user = "ElonTusk";
        String fileLine = "[main] DEBUG - User : ElonTusk; 2020-04-26T09:48:55.7372445 - ElonTusk sold another Tesla.";
        String message = "ElonTusk sold another Tesla.";
        LocalDateTime dateTime = LocalDateTime.parse("2020-04-26T09:48:55.7372445");
        UserDateTimeMessageFileLine fileLine1 = new UserDateTimeMessageFileLine(user, message, dateTime, fileLine);
        //when
        UserDateTimeMessageFileLine splitedFileLine = sut.apply(line);
        //then
        assertThat(splitedFileLine).isEqualTo(fileLine1);

    }
}