package EasyBytes.SpringBoot.SchoolApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping({"/home","","/"})
    //here we define multiple mapping for home
    public String displayHomePage(Model model){
        model.addAttribute("username","Lakshya ");
        return "home.html";
    }
}
