import logstreamline.Formatters;
import logstreamline.TestLogStreamline;
import logstreamline.aggregator.StringAggregator;
import logstreamline.fileline.UserDateTimeMessageFileLine;
import logstreamline.filter.LocalDateTimeFileLineFilter;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws Exception {
//        F:\Temp\LogStreamline\testlogs
        Path folder = Path.of("F:\\Temp\\LogStreamline\\testlogs");
        Path result = Path.of("F:\\Temp\\LogStreamline\\result.log");
        PrintWriter pw = new PrintWriter(Files.newBufferedWriter(result));
        TestLogStreamline.pw = pw;
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(50);
        try (Stream<Path> paths = Files.walk(folder)) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(r -> {
                        TestLogStreamline testLogStreamline = new TestLogStreamline();
//                        testLogStreamline.addFilter(new UserFileLineFilter("Mr. Meeseeks"));
                        testLogStreamline.addFilter(new LocalDateTimeFileLineFilter(LocalDateTime.parse("2020-02-17T06:12:01"), LocalDateTime.parse("2020-03-02T06:12:01")));
                        testLogStreamline.setAggregator(new StringAggregator<UserDateTimeMessageFileLine>(v -> v.getDateTime().format(Formatters.DAY.getFormatter())));
                        testLogStreamline.setInputFilePath(r);
                        executor.execute(testLogStreamline);
                    });
        }

        executor.shutdown();

        while (!executor.isTerminated()) {
        }
        TestLogStreamline.printResult();

    }
}
