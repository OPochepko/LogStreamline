package logcreater;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestParser {
    public static void main(String[] args) {
        String stringToSearch = "[main] DEBUG - User : ElonTusk; 2020-04-19T00:13:25.2080918 - ElonTusk found Smidgen.";

        Pattern p1 = Pattern.compile(": (.*?);");   // the pattern to search for
        Matcher m1 = p1.matcher(stringToSearch);
        Pattern p2 = Pattern.compile("; (.*?) -");   // the pattern to search for
        Matcher m2 = p2.matcher(stringToSearch);
        Pattern p3 = Pattern.compile("[0-9]\\s-\\s(.*?)\\.");   // the pattern to search for
        Matcher m3 = p3.matcher(stringToSearch);

        if (m1.find()) {
            String theGroup = m1.group(1);
            System.out.format("'%s'\n", theGroup);
        }
        if (m2.find()) {
            String theGroup = m2.group(1);
            System.out.format("'%s'\n", theGroup);
        }
        if (m3.find()) {
            String theGroup = m3.group(1);
            System.out.format("'%s'\n", theGroup);
        }
    }
}
