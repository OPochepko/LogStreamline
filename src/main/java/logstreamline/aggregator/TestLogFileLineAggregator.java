package logstreamline.aggregator;

import logstreamline.Formatters;
import logstreamline.fileline.TestLogFileLine;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class TestLogFileLineAggregator {

    public static Collector<TestLogFileLine, ?, Map<String, Integer>> byUsername() {
        return Collectors.toMap(TestLogFileLine::getUser, r -> 1, Integer::sum);
    }

    public static Collector<TestLogFileLine, ?, Map<String, Integer>> byTimePeriod(Formatters formatter) {
        return Collectors.toMap(r -> r.getDateTime().format(DateTimeFormatter.ofPattern(formatter.getPattern())), r -> 1, Integer::sum);
    }
//    public static Collector<TestLogFileLine, ?, Map<String, Map<String, Integer>>> byUserAndMoth(){
//        return Collectors.groupingBy(TestLogFileLine::getUser,Collectors.toMap(r -> r.getDateTime().format(DateTimeFormatter.ofPattern(Formatters.MONTH.getPattern()))
//        ,r-> 1,Integer::sum));
//    }
}
