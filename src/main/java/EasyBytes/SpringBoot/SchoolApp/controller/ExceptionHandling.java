package EasyBytes.SpringBoot.SchoolApp.controller;


//also called globalExceptionHandler

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice(annotations = Controller.class) // now only work for controller annotations
public class ExceptionHandling {

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception exception){
        ModelAndView errorPage = new ModelAndView();
        errorPage.setViewName("error");
        //by this , an error.html file is expected by spring
        errorPage.addObject("errorMsg",exception.getMessage());
        return errorPage;
    }
}
