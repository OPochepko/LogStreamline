package logstreamline.model.fileline;

abstract public class FileLine {
    private final String fileLine;

    public FileLine(String fileLine) {
        this.fileLine = fileLine;
    }

    public String getFileLine() {
        return fileLine;
    }

}
