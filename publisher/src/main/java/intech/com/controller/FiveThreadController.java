package intech.com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import intech.com.exception.ThreadsRunningException;
import intech.com.model.Message;
import intech.com.model.ThreadFunction;
import intech.com.model.ThreadList;
import intech.com.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Controller
public class FiveThreadController implements ThreadController {

    public static final Logger logger = LoggerFactory.getLogger(FiveThreadController.class);

    @Autowired
    private MessageGenerator messageController;

    @Autowired
    private MessageRepository messageRepository;

    private ThreadList threadList;

    private boolean isThreadsRunning = false;

    private ThreadList createThreadList() {
        final ThreadFunction threadFunction = () -> {
            final Message newMassage = messageRepository.save(messageController.generatedMessage());
            final URI localhostURI = URI.create("http://localhost:8086/subscriber/post/message");
            final RestTemplate restTemplate = new RestTemplate();
            final HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            final String json = objectToStringJson(newMassage);
            HttpEntity<String> httpEntity = new HttpEntity<>(json, httpHeaders);
            restTemplate.exchange(localhostURI, HttpMethod.POST, httpEntity, Message.class);
            logger.info("Generate and sent " + newMassage.toString() + " to subscriber");
        };
        return new ThreadList(5, threadFunction);
    }

    public void startTreads() {
        if (isThreadsRunning)
            throw new ThreadsRunningException("Threads are already running, only 5 threads can be run at a time!");
        threadList = createThreadList();
        logger.info("Start pushing messages");
        for (Thread thread : threadList.getThreadList())
            thread.start();
        isThreadsRunning = true;
    }

    public void stopThreads(final boolean stopTreads) {
        threadList.setStopTreads(stopTreads);
        isThreadsRunning = false;
    }

    private String objectToStringJson(Object object) {
        String json = null;
        try {
            json =  new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

}
