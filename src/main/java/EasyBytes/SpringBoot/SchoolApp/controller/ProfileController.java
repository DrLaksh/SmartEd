package EasyBytes.SpringBoot.SchoolApp.controller;

import EasyBytes.SpringBoot.SchoolApp.model.Address;
import EasyBytes.SpringBoot.SchoolApp.model.Person;
import EasyBytes.SpringBoot.SchoolApp.model.Profile;
import EasyBytes.SpringBoot.SchoolApp.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller("profileControllerBean")
public class ProfileController {

    @Autowired
    PersonRepository personRepository;

    @RequestMapping("/displayProfile")
    public ModelAndView displayMessages(Model model, HttpSession session){
        Person person = (Person) session.getAttribute("loggedInPerson");
        //code to send data to backend
        Profile profile = new Profile();
        //here we are passing the value to the person to profile to show
        profile.setName(person.getName());
        profile.setMobileNumber(person.getMobileNumber());
        profile.setEmail(person.getEmail());
        //if person hav address details then populate that to the dashboard too
        if(person.getAddress() != null && person.getAddress().getAddressId() > 0 ){
            profile.setAddress1(person.getAddress().getAddress1());
            profile.setAddress2(person.getAddress().getAddress2());
            profile.setCity(person.getAddress().getCity());
            profile.setState(person.getAddress().getState());
            profile.setZipCode(person.getAddress().getZipCode());
        }

        ModelAndView modelAndView = new ModelAndView("profile.html");
        modelAndView.addObject("profile",profile);//"profile is the object reference from profile.html thymeleaf object"
        return modelAndView;
    }

    @PostMapping(value = "/updateProfile")
    //method to update the data from profile to DB
    public String updateProfile(@Valid @ModelAttribute("profile") Profile profile , Errors errors , HttpSession session){

        if(errors.hasErrors()){
            return "profile.html";
        }
        Person person = (Person) session.getAttribute("loggedInPerson");
        person.setName(profile.getName());
        person.setEmail(profile.getEmail());
        person.setMobileNumber(profile.getMobileNumber());
        if (person.getAddress() == null || !(person.getAddress().getAddressId() > 0 )) {
            person.setAddress(new Address());
        }
        person.getAddress().setAddress1(profile.getAddress1());
        person.getAddress().setAddress2(profile.getAddress2());
        person.getAddress().setCity(profile.getCity());
        person.getAddress().setState(profile.getState());
        person.getAddress().setZipCode(profile.getZipCode());
        personRepository.save(person);
        session.setAttribute("loggedInPerson",person);
        return "redirect:/displayProfile";

    }


}