package EasyBytes.SpringBoot.SchoolApp.controller;

import EasyBytes.SpringBoot.SchoolApp.model.Person;
import EasyBytes.SpringBoot.SchoolApp.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ExceptionTypeFilter;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class DashboardController {

    @Autowired
    PersonRepository personRepository; // very imp for person values

    @Value("${eazySchool.pageSize}")
    private int defaultPageSize;

    @Value("${eazySchool.contact.successMsg}")
    private String message;

    @Autowired
    Environment environment;

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication, HttpSession session)  { // this http session helps to get session information and use it inside the controller
        Person person = personRepository.readByEmail(authentication.getName());//code for profile details
        model.addAttribute("username", person.getName());//if use authentication it will show email , so change it to person
        model.addAttribute("roles", authentication.getAuthorities().toString());
        //throw new Exception("Bad request for login");
        //adding courses functionality here
        if (person.getEazyClass() != null && person.getEazyClass().getName() != null){
            model.addAttribute("enrolledClass" , person.getEazyClass().getName());
        }
        session.setAttribute("loggedInPerson",person);
        return "dashboard.html";
    }

    //logging config

    private void logMessages(){
        log.error("Error message from dashBoard page");
        log.warn("Warn message from dashBoard page");
        log.info("Info message from dashBoard page");
        log.debug("Debug message from dashBoard page");
        log.trace("Trace message from dashBoard page");
//changes to see the @Value
        log.error("PageSize value with @Value : " +defaultPageSize);
        log.error("Message value with @Value : "+message);
        //changes for the environment variable
        log.error("defaultPage Size via Environment is : "+environment.getProperty("eazySchool.pageSize"));
        log.error("System Env property  Size via Environment is : "+environment.getProperty("JAVA_HOME"));

    }

}