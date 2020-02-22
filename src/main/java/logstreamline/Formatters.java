package logstreamline;

import java.time.format.DateTimeFormatter;

public enum Formatters {
    SECOND("yyyy/MM/dd | HH:mm:ss"), MINUTE("yyyy/MM/dd | HH:mm"), HOUR("yyyy/MM/dd | HH"), DAY("yyyy/MM/dd"), MONTH("yyyy/MM"), YEAR("yyyy");
    private String pattern;


    Formatters(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }

    public DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern(pattern);
    }
}
