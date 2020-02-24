package logstreamline.service;

import logstreamline.model.FilterTimeUnit;
import logstreamline.model.LogStreamline;
import logstreamline.model.aggregator.StringAtomicToMapAggregator;
import logstreamline.model.aggregator.StringAtomicToMapAggregatorImpl;
import logstreamline.model.fileline.UserDateTimeMessageFileLine;
import logstreamline.model.filter.LocalDateTimeFileLineFilter;
import logstreamline.model.filter.MessageFleLineFilter;
import logstreamline.model.filter.UserDateTimeMessageFileLineFilter;
import logstreamline.model.filter.UserFileLineFilter;
import logstreamline.model.splitter.LineSplitter;
import logstreamline.model.splitter.LogLineSplitter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class LogStreamLineServiceImpl implements LogStreamLineService {

    private UserDateTimeMessageFileLineFilter<UserDateTimeMessageFileLine> filter;

    private StringAtomicToMapAggregator<UserDateTimeMessageFileLine> aggregator;

    private Map<String, AtomicInteger> result;

    private LineSplitter<UserDateTimeMessageFileLine> splitter;

    private PrintWriter pw;

    private void setAggregator(StringAtomicToMapAggregator<UserDateTimeMessageFileLine> aggregator) {
        this.aggregator = aggregator;
    }

    @Override
    public void run(String logInputPath, String resultOutPath, String filterUser, String filterFromDate,
                    String filterToDate, String filterMessage, boolean aggregateByUser, String aggregateTimeUnit, int threadNum) throws IOException {

        formFilter(filterUser, filterFromDate, filterToDate, filterMessage);
        formAggregator(aggregateByUser, aggregateTimeUnit);
        result = new ConcurrentHashMap<>();
        splitter = new LogLineSplitter();
        pw = new PrintWriter(Files.newBufferedWriter(Path.of(resultOutPath)));
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadNum);
        try (Stream<Path> paths = Files.walk(Path.of(logInputPath))) {
            paths.filter(Files::isRegularFile)
                    .forEach(inputFile -> {
                        executor.execute(new LogStreamline(pw, result, inputFile, filter, splitter, aggregator));
                    });
            executor.shutdown();
            while (!executor.isTerminated()) {
            }
            printResult();
        }
    }

    private void formAggregator(Boolean aggregateByUser, String aggregateTimeUnit) {
        if (aggregateByUser) addAggregator(new StringAtomicToMapAggregatorImpl<>(v -> v.getUser()));
        if (aggregateTimeUnit != null)
            addAggregator(new StringAtomicToMapAggregatorImpl<>(v ->
                    v.getDateTime().format(FilterTimeUnit.valueOf(aggregateTimeUnit).getFormatter())));
    }

    private void addAggregator(StringAtomicToMapAggregator<UserDateTimeMessageFileLine> aggregator) {
        if (this.aggregator == null) setAggregator(aggregator);
        else setAggregator(this.aggregator.andThen(aggregator));
    }

    private void formFilter(String filterUser, String filterFromDate, String filterToDate, String filterMessage) {
        if (filterUser != null) addFilter(new UserFileLineFilter(filterUser));
        addFilter(new LocalDateTimeFileLineFilter(LocalDateTime.parse(filterFromDate)
                , LocalDateTime.parse(filterToDate)));
        addFilter(new MessageFleLineFilter(filterMessage));
    }

    private void addFilter(UserDateTimeMessageFileLineFilter<UserDateTimeMessageFileLine> filter) {
        if (this.filter == null) setFilter(filter);
        else this.filter = this.filter.and(filter);
    }

    private void setFilter(UserDateTimeMessageFileLineFilter<UserDateTimeMessageFileLine> filter) {
        this.filter = filter;
    }

    private void printResult() {
        Map<String, AtomicInteger> sortedResult = new TreeMap<>(result);
        for (Map.Entry<String, AtomicInteger> entry : sortedResult.entrySet()) {
            System.out.println(entry.getKey() + ":      " + entry.getValue());

        }
    }
}
