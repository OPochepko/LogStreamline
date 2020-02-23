package logstreamline.model;

import java.time.format.DateTimeFormatter;

public enum FilterTimeUnit {
    SECONDS("yyyy/MM/dd | HH:mm:ss"), MINUTES("yyyy/MM/dd | HH:mm"), HOURS("yyyy/MM/dd | HH"), DAYS("yyyy/MM/dd"), MONTHS("yyyy/MM"), YEARS("yyyy");
    private String pattern;


    FilterTimeUnit(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }

    public DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern(pattern);
    }
}
