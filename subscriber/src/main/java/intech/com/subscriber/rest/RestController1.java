package intech.com.subscriber.rest;

import intech.com.subscriber.model.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("subscriber")
public class RestController1 {

    @PostMapping(value = "post/message", produces = "application/json")
    public void post(@RequestBody Message message){
        System.out.println(message.toString());
    }
}
