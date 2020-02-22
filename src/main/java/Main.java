import logstreamline.Configurator;
import picocli.CommandLine;

public class Main {
    public static void main(String[] args) throws Exception {
        new CommandLine(new Configurator()).execute(args);
    }
}
