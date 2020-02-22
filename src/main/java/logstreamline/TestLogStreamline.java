package logstreamline;

import logstreamline.fileline.UserDateTimeMessageFileLine;
import logstreamline.splitter.LineSplitter;
import logstreamline.splitter.TestLogLineSplitter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collector;

public class TestLogStreamline implements Runnable {


    public static PrintWriter pw;
    static Map<String, AtomicInteger> result = new ConcurrentHashMap<>();
    //    @Option(names = {"-i","-inputFile"}, description = "path of input file", interactive = true)
    private Path inputFilePath;

    //    @Option(names = {"-o","-outputFile"}, description = "path of output file", interactive = true)
    private Path outputFilePath;

    //    @Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message" )
    private boolean helpRequested = false;


    private Predicate<UserDateTimeMessageFileLine> filter;

    private Collector<UserDateTimeMessageFileLine, ?, Map<String, AtomicInteger>> collector;

    private LineSplitter<UserDateTimeMessageFileLine> splitter = new TestLogLineSplitter();
    private BiConsumer<UserDateTimeMessageFileLine, Map<String, AtomicInteger>> aggregator;

    public static void printResult() {
        Map<String, AtomicInteger> sortedResult = new TreeMap<>(result);
        for (Map.Entry<String, AtomicInteger> entry : sortedResult.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());

        }
    }

    public static void setPw(PrintWriter pw) {
        TestLogStreamline.pw = pw;
    }

    public void addFilter(Predicate<UserDateTimeMessageFileLine> predicate) {
        if (filter == null) setFilter(predicate);
        else filter = filter.and(predicate);
    }

    @Override
    public void run() {
        try {
            long i = Files.lines(inputFilePath)
                    .map(splitter)
                    .filter(filter)
//                    .peek(System.out::println)
                    .peek(pw::println)
                    .peek(r -> aggregator.accept(r, result))
//                    .peek(r -> result.merge(r.getUser(), new AtomicInteger(1),
//                            (oldVal, newVal) -> new AtomicInteger(oldVal.addAndGet(newVal.get()))))
                    .count();

        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.flush();
    }

    public void setAggregator(BiConsumer<UserDateTimeMessageFileLine, Map<String, AtomicInteger>> aggregator) {
        this.aggregator = aggregator;
    }

    public Path getInputFilePath() {
        return inputFilePath;
    }

    public void setInputFilePath(Path inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    public Path getOutputFilePath() {
        return outputFilePath;
    }

    public void setOutputFilePath(Path outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    public boolean isHelpRequested() {
        return helpRequested;
    }

    public Predicate<UserDateTimeMessageFileLine> getFilter() {
        return filter;
    }

    public void setFilter(Predicate<UserDateTimeMessageFileLine> filter) {
        this.filter = filter;
    }

    public Collector<UserDateTimeMessageFileLine, ?, Map<String, AtomicInteger>> getCollector() {
        return collector;
    }

    public void setCollector(Collector<UserDateTimeMessageFileLine, ?, Map<String, AtomicInteger>> collector) {
        this.collector = collector;
    }

    public LineSplitter<UserDateTimeMessageFileLine> getSplitter() {
        return splitter;
    }

    public void setSplitter(LineSplitter<UserDateTimeMessageFileLine> splitter) {
        this.splitter = splitter;
    }


}
