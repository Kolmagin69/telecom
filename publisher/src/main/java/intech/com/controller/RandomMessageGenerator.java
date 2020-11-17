package intech.com.controller;

import intech.com.model.Action;
import intech.com.model.Message;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.util.Random;

@Controller
public class RandomMessageGenerator implements MessageGenerator {

    @Override
    public Message generatedMessage() {
        return new Message(getRandomInt(),
                Action.getRandomAction(), 
                new Timestamp(System.currentTimeMillis()));
    }

    private int getRandomInt() {
        final Random random = new Random();
        int i = random.nextInt();
        return i >= 0 ? i : -1 * i;
    }

}
