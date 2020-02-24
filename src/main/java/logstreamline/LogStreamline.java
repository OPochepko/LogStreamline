package logstreamline;

import logstreamline.aggregator.StringAtomicToMapAggregator;
import logstreamline.fileline.UserDateTimeMessageFileLine;
import logstreamline.filter.UserDateTimeMessageFileLineFilter;
import logstreamline.splitter.LineSplitter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LogStreamline implements Runnable {

    private final PrintWriter pw;

    private final Map<String, AtomicInteger> result;

    private final Path inputFilePath;

    private final UserDateTimeMessageFileLineFilter<UserDateTimeMessageFileLine> filter;

    private final LineSplitter<UserDateTimeMessageFileLine> splitter;

    private final StringAtomicToMapAggregator<UserDateTimeMessageFileLine> aggregator;

    public LogStreamline(PrintWriter pw,
                         Map<String, AtomicInteger> result,
                         Path inputFilePath,
                         UserDateTimeMessageFileLineFilter<UserDateTimeMessageFileLine> filter,
                         LineSplitter<UserDateTimeMessageFileLine> splitter,
                         StringAtomicToMapAggregator<UserDateTimeMessageFileLine> aggregator) {
        this.pw = pw;
        this.result = result;
        this.inputFilePath = inputFilePath;
        this.filter = filter;
        this.splitter = splitter;
        this.aggregator = aggregator;
    }

    @Override
    public void run() {
        try {
            long i = Files.lines(inputFilePath)
                    .map(splitter)
                    .filter(filter)
                    .peek(r -> pw.println(r.getFileLine()))
                    .peek(r -> aggregator.accept(r, result))
                    .count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.flush();
    }





}
