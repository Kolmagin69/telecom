package intech.com.controller;

import intech.com.model.Action;
import intech.com.model.Message;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

@Controller
public class RandomMessageGenerator implements MessageGenerator {

    @Override
    public  Message generatedMessage() {
        final Message message = new Message();
        message.setMsisdn(getRandomInt());
        message.setAction(Action.getRandomAction());
        message.setTimestamp(System.currentTimeMillis());
        return message;

    }

    private int getRandomInt() {
        final Random random = new Random();
        final int i = random.nextInt();
        return i >= 0 ? i : -1 * i;
    }

}
