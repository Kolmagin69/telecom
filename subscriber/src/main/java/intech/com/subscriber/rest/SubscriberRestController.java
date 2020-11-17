package intech.com.subscriber.rest;

import intech.com.subscriber.controller.MessageController;
import intech.com.subscriber.model.ActionEntity;
import intech.com.subscriber.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("subscriber")
public class SubscriberRestController {

    public static final Logger logger = LoggerFactory.getLogger(SubscriberRestController.class);

    @Autowired
    private MessageController messageController;

    @PostMapping(value = "post/message", produces = "application/json")
    public @ResponseBody ActionEntity post(@RequestBody final Message message){
        final ActionEntity action = messageController.checkActionAndSave(message);
        logger.info("Save " + action.toString());
        return action;
    }

}
