package logstreamline;

import logstreamline.fileline.TestLogFileLine;
import logstreamline.splitter.LineSplitter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.stream.Collector;

public class TestLogStreamline implements Callable<Integer> {
    //    @Option(names = {"-i","-inputFile"}, description = "path of input file", interactive = true)
    private Path inputFilePath;

    //    @Option(names = {"-o","-outputFile"}, description = "path of output file", interactive = true)
    private Path outputFilePath;

    //    @Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message" )
    private boolean helpRequested = false;


    private Predicate<TestLogFileLine> filter;

    private Collector<TestLogFileLine, ?, Map<String, Integer>> collector;

    private LineSplitter<TestLogFileLine> splitter;

    public void addFilter(Predicate<TestLogFileLine> predicate) {
        if (filter == null) setFilter(predicate);
        else filter = filter.and(predicate);
    }

    @Override
    public Integer call() throws Exception {

        Map<String, Integer> result = Files.lines(inputFilePath)
                .map(splitter)
                .filter(filter)
                .peek(System.out::println)
                .collect(collector);
        print(result);
        return 1;
    }

    public void setInputFilePath(Path inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    public void setOutputFilePath(Path outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    public void setFilter(Predicate<TestLogFileLine> filter) {
        this.filter = filter;
    }

    public void setCollector(Collector<TestLogFileLine, ?, Map<String, Integer>> collector) {
        this.collector = collector;
    }

    public void setSplitter(LineSplitter<TestLogFileLine> splitter) {
        this.splitter = splitter;
    }

    private void print(Map<String, Integer> i) {

        for (Map.Entry<String, ?> entry : i.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());

        }
    }
}
