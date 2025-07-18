package EasyBytes.SpringBoot.SchoolApp.rest;

import EasyBytes.SpringBoot.SchoolApp.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
@Order(1)//top priority
public class GlobalExceptionRestController extends ResponseEntityExceptionHandler {
//ResponseEntityExceptionHandler used for APi request for invalid input Data

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Response response = new Response(status.toString(), ex.getBindingResult().toString());
        return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
    }




    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Response> exceptionHandler(Exception exception){ // used for invalid data format
        Response response = new Response("500", exception.getMessage());
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
