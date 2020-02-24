package logstreamline.filter;

import logstreamline.fileline.UserDateTimeMessageFileLine;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class UserFileLineFilterTest {

    UserFileLineFilter sut;

    @Test
    void test_givenUserDateTimeMessageFileLineAndMessageFileLineFIlter_shouldReturnTrue() {
        //given
        String line = "[main] DEBUG - User : ElonTusk; 2020-04-26T09:48:55.7372445 - ElonTusk sold another Tesla.";
        String user = "ElonTusk";
        String fileLine = "[main] DEBUG - User : ElonTusk; 2020-04-26T09:48:55.7372445 - ElonTusk sold another Tesla.";
        String message = "ElonTusk sold another Tesla.";
        LocalDateTime dateTime = LocalDateTime.parse("2020-04-26T09:48:55.7372445");
        LocalDateTime dateFrom = LocalDateTime.parse("2019-04-26T09:48:55.7372445");
        LocalDateTime dateTo = LocalDateTime.parse("2021-04-26T09:48:55.7372445");
        UserDateTimeMessageFileLine userDateTimeFileLine = new UserDateTimeMessageFileLine(user, message, dateTime, fileLine);
        sut = new UserFileLineFilter("ElonTusk");
        //when
        Boolean isFiltered = sut.test(userDateTimeFileLine);
        //then
        assertThat(isFiltered).isTrue();
    }
}