package intech.com.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.*;

public class ExceptionMessage {

    private final String message;

    private final HttpStatus httpStatus;

    private final ZonedDateTime dateTime;

    public ExceptionMessage(String message, HttpStatus httpStatus, ZonedDateTime dateTime) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.dateTime = dateTime;
    }

    public Map<String, String> toMap() {
        final Map<String, String> map = new TreeMap<>();
        map.put("message", message);
        map.put("httpStatus", httpStatus.toString());
        map.put("dateTime", dateTime.toString());
        return map;
    }

}
