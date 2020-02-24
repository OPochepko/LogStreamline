package logstreamline;

import logstreamline.service.LogStreamLineService;
import logstreamline.service.LogStreamLineServiceImpl;
import picocli.CommandLine;

import java.util.concurrent.Callable;


@CommandLine.Command(name = "streamline", mixinStandardHelpOptions = true,
        header = "    __                _____ __                            ___          \n" +
                "   / /   ____  ____ _/ ___// /_________  ____ _____ ___  / (_)___  ___ \n" +
                "  / /   / __ \\/ __ `/\\__ \\/ __/ ___/ _ \\/ __ `/ __ `__ \\/ / / __ \\/ _ \\\n" +
                " / /___/ /_/ / /_/ /___/ / /_/ /  /  __/ /_/ / / / / / / / / / / /  __/\n" +
                "/_____/\\____/\\__, //____/\\__/_/   \\___/\\__,_/_/ /_/ /_/_/_/_/ /_/\\___/ \n" +
                "            /____/                                                     ",

        description = {"This application can be used " +
                "for processing log files by filtering and aggregation. " + "\n" +
                "Filters: " + "\n" +
                "You can use three kinds of filters(filter by User, filter by time period and by message pattern();" + "\n" +
                "Aggregators: " + "\n" +
                "You can aggregate filtered data by User or by time Unit (SECONDS, MINUTES, HOURS, DAYS, MONTHS, YEARS)" + "\n" +
                "For example:" + "\n" +
                "   -in F:\\Temp\\LogStreamline\\testlogs -out F:\\Temp\\LogStreamline\\result.log -fu ElonTusk -at DAYS -ft 2020-05-02T06:12:01 -ff 2020-02-17T06:12:01 -tn 1" + "\n" +
                "will get all log message with user ElonTusk recorded from 2020-02-17T06:12:01 to 2020-05-02T06:12:01 and write to file result.log " + "\n" +
                "aggregated by users and days result will be printed to console" + "\n" +
                "Example of the format StreamLine can work with by default:" + "" + "\n" +
                "'[main] DEBUG - User : ElonTusk; 2020-02-29T06:49:16.9736776 - ElonTusk bought Flurbo.'"

        })
public class LogStreamLineCommand implements Callable<Integer> {

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

    @CommandLine.Option(names = {"-au", "-aggregateByUser"}, description = "Enable aggregation of result by user", defaultValue = "true")
    private boolean aggregateByUser;

    @CommandLine.Option(names = {"-at", "-aggregateTime"}, description = "Time unit to aggregate by (" +
            "SECONDS, MINUTES, HOURS, DAYS, MONTHS, YEARS)")
    private String aggregateTimeUnit;

    @CommandLine.Option(names = {"-tn", "-threadNumber"}, description = "Number of threads to work", defaultValue = "1")
    private int threadNum;

    public static void main(String[] args) throws Exception {
        new CommandLine(new LogStreamLineCommand()).execute(args);
    }

    @Override
    public Integer call() throws Exception {
        LogStreamLineService service = new LogStreamLineServiceImpl();
        service.run(logInputPath, resultOutPath, filterUser, filterFromDate, filterToDate, filterMessage, aggregateByUser,
                aggregateTimeUnit, threadNum);
        return 1;
    }

}
