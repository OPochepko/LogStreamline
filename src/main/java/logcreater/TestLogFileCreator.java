package logcreater;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.time.LocalDateTime.now;

public class TestLogFileCreator {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(TestLogFileCreator.class);


        List<String> users = Arrays.asList("Birdperson", "Mr. Meeseeks","Elon Tusk", "Glar", "Izzy");
        List<String> actions = Arrays.asList("ate", "bought","sold", "found", "destroyed");
        List<String> objects = Arrays.asList("Plumbus", "Flurbo","Smidgen", "Glaagnar", "Glem");
        LocalDateTime now = now();


        for (int i = 0; i < 200; i++) {
            String randomUser = users.get(getRandomInt());
            logger.debug(String.format("User : %s; %s - %s %s %s.",
                    randomUser,
                    now().plusDays(i * getRandomInt()).plusHours(i + getRandomInt()).plusMinutes(i + getRandomInt())
                            .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    randomUser,
                    actions.get(getRandomInt()),
                    objects.get(getRandomInt())));


        }

    }
    private static int getRandomInt(){
        return ThreadLocalRandom.current().nextInt(0,4);
    }

}
