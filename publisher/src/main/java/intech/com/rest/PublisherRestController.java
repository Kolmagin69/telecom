package intech.com.rest;

import intech.com.controller.ThreadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("publisher")
public class PublisherRestController {

    @Autowired
    private ThreadController threadController;

    @PostMapping("start")
    public String startMessage() {
        threadController.startTreads();
        return "Start " + threadController.threadsName();
    }
    
    @PostMapping("stop")
    public String stopMessage() {
        threadController.stopThreads(true);
        return "Start " + threadController.threadsName();
    }

}
