import logstreamline.TestLogStreamline;
import logstreamline.aggregator.TestLogFileLineAggregator;
import logstreamline.filter.LocalDateTimeFileLineFilter;
import logstreamline.filter.MessageFleLineFilter;
import logstreamline.splitter.TestLogLineSplitter;

import java.nio.file.Path;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws Exception {

        TestLogStreamline testLogStreamline = new TestLogStreamline();
        testLogStreamline.setSplitter(new TestLogLineSplitter());
        testLogStreamline.addFilter(new MessageFleLineFilter("^[^\\d].*"));
        testLogStreamline.addFilter(new LocalDateTimeFileLineFilter(LocalDateTime.parse("2020-05-17T06:12:01"), LocalDateTime.parse("2020-12-17T06:12:01")));
        testLogStreamline.setCollector(TestLogFileLineAggregator.byUsername());
        testLogStreamline.setInputFilePath(Path.of("F:\\Temp\\LogStreamline\\TestLog.log"));
        testLogStreamline.setOutputFilePath(Path.of("F:\\Temp\\LogStreamline\\TestLogAnswer.log"));

        testLogStreamline.call();
    }
}
