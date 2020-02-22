package logstreamline;

import logstreamline.aggregator.Aggregator;
import logstreamline.fileline.UserDateTimeMessageFileLine;
import logstreamline.filter.LocalDateTimeFileLineFilter;
import logstreamline.filter.UserFileLineFilter;
import logstreamline.splitter.TestLogLineSplitter;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

@CommandLine.Command(name = "streamline", mixinStandardHelpOptions = true, description = {"DESCRIPTION"})
public class Configurator implements Callable<Integer> {

    @CommandLine.Option(names = {"-in", "-inputPath"}, description = "path to input path(File or Directory)")
    private String logInputPath;

    @CommandLine.Option(names = {"-out", "-outputPath"}, description = "path to output File")
    private String resultOutPath;

    @CommandLine.Option(names = {"-fu", "-filterByUser"}, description = "User name for filter with")
    private String filterUser;

    @CommandLine.Option(names = {"-ff", "-filterFrom"}, description = "Date and Time in LocalDateTime format for filter (from)", defaultValue = "+999999999-12-31T23:59:59.999999999")
    private String filterFromDate;

    @CommandLine.Option(names = {"-ft", "-filterTo"}, description = "Date and Time in LocalDateTime format for filter (to)", defaultValue = "-999999999-01-01T00:00:00")
    private String filterToDate;

//    @CommandLine.Option(names = {"-fm","-filterMessage"}, description = "Regex for message to filter", defaultValue = "(?s).*")
//    private Spring filterMessage;

    @CommandLine.Option(names = {"-au", "-aggregateByUser"}, description = "Enable aggregation of result by user(at least one aggregation should be on)", defaultValue = "true")
    private boolean aggregateByUser;

    @CommandLine.Option(names = {"-at", "-aggregateTime"}, description = "Time unit to aggregate by (SECOND,MINUTE,DAY,MONTH,YEAR)")
    private String aggregateTimeUnit;

    @CommandLine.Option(names = {"-tn", "-threadNumber"}, description = "Number of threads to work", defaultValue = "1")
    private int threadNum;


    private Predicate filter;


    private BiConsumer aggregator;


//    private Aggregator aggregator;
//
//    TestLogStreamline streamline = new TestLogStreamline();

    @Override
    public Integer call() throws Exception {
        TestLogLineSplitter splitter = new TestLogLineSplitter();
        formFilter();
        formAggregator();

        TestLogStreamline.pw = new PrintWriter(Files.newBufferedWriter(Path.of(resultOutPath)));
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadNum);
        try (Stream<Path> paths = Files.walk(Path.of(logInputPath))) {
            paths.filter(Files::isRegularFile)
                    .forEach(inputFile -> {
                        TestLogStreamline testLogStreamline = new TestLogStreamline();
                        testLogStreamline.setFilter(filter);
                        testLogStreamline.setAggregator(aggregator);
                        testLogStreamline.setInputFilePath(inputFile);
                        executor.execute(testLogStreamline);
                    });
//        }
            executor.shutdown();
            while (!executor.isTerminated()) {
            }
            TestLogStreamline.printResult();
//
//
            System.out.println("-in :" + logInputPath + " -out :" + resultOutPath + " -fu :" + filterUser + " -ff :" + filterFromDate + " -ft :" + filterToDate
                    + " au:" + aggregateByUser + " -at :" + aggregateTimeUnit);
            return null;
        }
    }

    private void formAggregator() {
        if (aggregateByUser) addAggregator(new Aggregator<UserDateTimeMessageFileLine, String>(v -> v.getUser()));
        if (aggregateTimeUnit != null)
            addAggregator(new Aggregator<UserDateTimeMessageFileLine, String>(v -> v.getDateTime().format(Formatters.valueOf(aggregateTimeUnit).getFormatter())));
    }

    private void addAggregator(Aggregator<UserDateTimeMessageFileLine, String> aggregator) {
        if (this.aggregator == null) setAggregator(aggregator);
        else setAggregator(this.aggregator.andThen(aggregator));
    }

    private void formFilter() {
        if (filterUser != null) addFilter(new UserFileLineFilter(filterUser));
        addFilter(new LocalDateTimeFileLineFilter<UserDateTimeMessageFileLine>(LocalDateTime.parse(filterFromDate), LocalDateTime.parse(filterToDate)));
    }

    private void addFilter(Predicate filter) {
        if (this.filter == null) setFilter(filter);
        else this.filter = this.filter.and(filter);
    }

    public void setFilter(Predicate filter) {
        this.filter = filter;
    }

    public BiConsumer getAggregator() {
        return aggregator;
    }

    public void setAggregator(BiConsumer aggregator) {
        this.aggregator = aggregator;
    }
}
