import logstreamline.Configurator;
import picocli.CommandLine;

public class Main {
    public static void main(String[] args) throws Exception {
        for (String s : args) {
            System.out.println(s);
        }
        new CommandLine(new Configurator()).execute(args);
//        new CommandLine(new Configurator()).execute("-in", "F:\\Temp\\LogStreamline\\testlogs",
//                "-out","F:\\Temp\\LogStreamline\\result.log","-fu","Mr. Meeseeks","-at","DAY","-ft","2020-03-02T06:12:01","-ff","2020-02-17T06:12:01", "-tn","5");
////        F:\Temp\LogStreamline\testlogs
//        Path folder = Path.of("F:\\Temp\\LogStreamline\\testlogs");
//        Path result = Path.of("F:\\Temp\\LogStreamline\\result.log");
//        TestLogStreamline.pw = new PrintWriter(Files.newBufferedWriter(result));
//        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
//        try (Stream<Path> paths = Files.walk(folder)) {
//            paths.filter(Files::isRegularFile)
//                    .forEach(r -> {
//                        TestLogStreamline testLogStreamline = new TestLogStreamline();
//                        testLogStreamline.setFilter(new LocalDateTimeFileLineFilter<UserDateTimeMessageFileLine>(LocalDateTime.parse("2020-02-17T06:12:01"), LocalDateTime.parse("2020-03-02T06:12:01")).and(new UserFileLineFilter("Mr. Meeseeks")));
//                        testLogStreamline.setAggregator(new Aggregator<UserDateTimeMessageFileLine, String>(v -> v.getDateTime().format(Formatters.DAY.getFormatter())).andThen(new Aggregator<UserDateTimeMessageFileLine, String>(v -> v.getUser())));
//                        testLogStreamline.setInputFilePath(r);
//                        executor.execute(testLogStreamline);
//                    });
//        }
//        executor.shutdown();
//        while (!executor.isTerminated()) {}
//        TestLogStreamline.printResult();
//
    }
}
