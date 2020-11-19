package intech.com.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

@ControllerAdvice
public class ThreadsExceptionHandler {

    @ExceptionHandler(value = {ThreadsRunningException.class, ClientConnectException.class})
    public ModelAndView handleRuntimeException(final Exception ex) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        
        final ExceptionMessage message = new ExceptionMessage(
                ex.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("UTC+3")));
        final Map<String, String> map = message.toMap();
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

}
