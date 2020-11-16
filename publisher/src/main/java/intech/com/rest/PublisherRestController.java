package intech.com.rest;

import intech.com.controller.ThreadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("publisher")
public class PublisherRestController {

    @Autowired
    private ThreadController threadController;
    
    @PostMapping("start")
    public void startMessage() {  
        threadController.startTreads();
    }
    
    @PostMapping("stop")
    public void stopMessage() {
        threadController.stopThreads(true);
    }
    

}
