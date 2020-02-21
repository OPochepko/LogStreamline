package logstreamline;

public enum Formatters {
    SECOND( "yyyy/MM/dd | HH:mm:ss"), MINUTE("yyyy/MM/dd | HH:mm"), HOUR("yyyy/MM/dd | HH"),DAY("yyyy/MM/dd"), MONTH("yyyy/MM");
    private String pattern;


    private Formatters(String pattern){
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
