package intech.com.controller;

import intech.com.model.Action;
import intech.com.model.Message;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class RandomMessageController implements MessageController {

    private final AtomicInteger id  = new AtomicInteger(0);
    
    private final Random random = new Random();

    @Override
    public Message generatedMessage() {
        return new Message(id.getAndIncrement(),
                random.nextInt(1000000),
                Action.getRandomAction(), 
                new Timestamp(System.currentTimeMillis()));
    }
}
