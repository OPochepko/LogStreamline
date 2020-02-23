package logstreamline;

import logstreamline.aggregator.StringAtomicToMapAggregator;
import logstreamline.aggregator.StringAtomicToMapAggregatorImpl;
import logstreamline.fileline.UserDateTimeMessageFileLine;
import logstreamline.filter.LocalDateTimeFileLineFilter;
import logstreamline.filter.MessageFleLineFilter;
import logstreamline.filter.UserDateTimeMessageFileLineFilter;
import logstreamline.filter.UserFileLineFilter;
import logstreamline.splitter.LogLineSplitter;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@CommandLine.Command(name = "streamline", mixinStandardHelpOptions = true, description = {"This application can be used " +
        "for processing log files by filtering and aggregation. " + "\n" +
        "Filters: " + "\n" + "You can use three kinds of filters(filter by User, filter by time period and by message pattern();" + "\n" +
        "Aggregators: " + "\n" + " You can aggregate filtered data by User or by time Unit (e.g. SECONDS, MINUTES and other up to DAYS " +
        "ChronoUnit values)" + "\n" +
        "For example:" + "\n" + " -in F:\\Temp\\LogStreamline\\testlogs -out F:\\Temp\\LogStreamline\\result.log -fu Mr.Meeseeks -at DAYS -ft 2020-05-02T06:12:01 -ff 2020-02-17T06:12:01 -tn 1" + "\n" +
        "will get all log message with user Mr.Meeseeks recorded from 2020-02-17T06:12:01 to 2020-05-02T06:12:01 and write to file result.log " + "\n" +
        "aggregated by users and days result will be printed to console"

})
public class Configurator implements Callable<Integer> {

    @CommandLine.Option(names = {"-in", "-inputPath"}, description = "Path to input(File or Directory)")
    private String logInputPath;

    @CommandLine.Option(names = {"-out", "-outputPath"}, description = "Path to output(File)")
    private String resultOutPath;

    @CommandLine.Option(names = {"-fu", "-filterByUser"}, description = "User name to filter with")
    private String filterUser;

    @CommandLine.Option(names = {"-ff", "-filterFrom"}, description = "Date and Time in LocalDateTime format for filter " +
            "(from)", defaultValue = "+999999999-12-31T23:59:59.999999999")
    private String filterFromDate;

    @CommandLine.Option(names = {"-ft", "-filterTo"}, description = "Date and Time in LocalDateTime format for filter " +
            "(to)", defaultValue = "-999999999-01-01T00:00:00")
    private String filterToDate;

    @CommandLine.Option(names = {"-fm", "-filterMessage"}, description = "Regex for filter by message with", defaultValue = "(?s).*")
    private String filterMessage;

    @CommandLine.Option(names = {"-au", "-aggregateByUser"}, description = "Enable aggregation of result by user(at least " +
            "one aggregation should be used)", defaultValue = "true")
    private boolean aggregateByUser;

    @CommandLine.Option(names = {"-at", "-aggregateTime"}, description = "Time unit to aggregate by time units(e.g. " +
            "SECONDS, MINUTES and other ChronoUnit values up to DAYS)")
    private String aggregateTimeUnit;

    @CommandLine.Option(names = {"-tn", "-threadNumber"}, description = "Number of threads to work", defaultValue = "1")
    private int threadNum;
    private UserDateTimeMessageFileLineFilter<UserDateTimeMessageFileLine> filter;
    private StringAtomicToMapAggregator<UserDateTimeMessageFileLine> aggregator;
    private Map<String, AtomicInteger> result = new ConcurrentHashMap<>();

    public void setFilter(UserDateTimeMessageFileLineFilter<UserDateTimeMessageFileLine> filter) {
        this.filter = filter;
    }

    @Override
    public Integer call() throws Exception {

        //init
        LogLineSplitter splitter = new LogLineSplitter();
        formFilter();
        formAggregator();
        //
        PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Path.of(resultOutPath)));

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadNum);
        try (Stream<Path> paths = Files.walk(Path.of(logInputPath))) {
            paths.filter(Files::isRegularFile)
                    .forEach(inputFile -> {
                        executor.execute(new LogStreamline(pw, result, inputFile, filter, splitter, aggregator));
                    });

            executor.shutdown();

            while (!executor.isTerminated()) {
            }

            System.out.println(result);

            return null;
        }
    }

    private void formAggregator() {
        if (aggregateByUser) addAggregator(new StringAtomicToMapAggregatorImpl<>(v -> v.getUser()));
        if (aggregateTimeUnit != null)
            addAggregator(new StringAtomicToMapAggregatorImpl<>(v ->
                    v.getDateTime().truncatedTo(ChronoUnit.valueOf(aggregateTimeUnit)).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
    }

    private void addAggregator(StringAtomicToMapAggregator<UserDateTimeMessageFileLine> aggregator) {
        if (this.aggregator == null) setAggregator(aggregator);
        else setAggregator(this.aggregator.andThen(aggregator));
    }

    private void formFilter() {
        if (filterUser != null) addFilter(new UserFileLineFilter(filterUser));
        addFilter(new LocalDateTimeFileLineFilter(LocalDateTime.parse(filterFromDate)
                , LocalDateTime.parse(filterToDate)));
        addFilter(new MessageFleLineFilter(filterMessage));
    }

    private void addFilter(UserDateTimeMessageFileLineFilter<UserDateTimeMessageFileLine> filter) {
        if (this.filter == null) setFilter(filter);
        else this.filter = this.filter.and(filter);
    }

    public void setAggregator(StringAtomicToMapAggregator<UserDateTimeMessageFileLine> aggregator) {
        this.aggregator = aggregator;
    }


}
