package software.crldev.elrondspringbootdemo.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {

  @org.springframework.web.bind.annotation.ExceptionHandler
  public ResponseEntity<String> handle(Exception e) {
    log.error(e.getMessage());
    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
