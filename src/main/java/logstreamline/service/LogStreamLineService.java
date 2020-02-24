package logstreamline.service;

public interface LogStreamLineService {
    void run(String logInputPath, String resultOutPath, String filterUser, String filterFromDate,
             String filterToDate, String filterMessage, boolean aggregateByUser, String aggregateTimeUnit, int threadNum) throws Exception;
}
