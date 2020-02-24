package logstreamline.service;

import logstreamline.AggregateTimeUnit;

import java.nio.file.Path;
import java.time.LocalDateTime;

public interface LogStreamLineService {
    void run(Path logInputPath, Path resultOutPath, String filterUser, LocalDateTime filterFromDate,
             LocalDateTime filterToDate, String filterMessage, boolean aggregateByUser, AggregateTimeUnit aggregateTimeUnit, int threadNum) throws Exception;
}
