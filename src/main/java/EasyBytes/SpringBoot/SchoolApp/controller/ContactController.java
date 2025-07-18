package EasyBytes.SpringBoot.SchoolApp.controller;

import EasyBytes.SpringBoot.SchoolApp.model.Contact;
import EasyBytes.SpringBoot.SchoolApp.service.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@Controller
public class ContactController {



    @RequestMapping("/contact")
    public String displayContactPage(Model model){

        //changes for validation here
        model.addAttribute("contact" , new Contact());
        return "contact.html";
    }

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    //also changes here for the validation part

    @RequestMapping(value = "/saveMsg",method = POST )
    //here @Valid shows validation needed and ModelAttribute take model for the data
    public String saveMessage(@Valid @ModelAttribute("contact") Contact contact, Errors errors){
        //error validation for validation part
        if(errors.hasErrors()){
            log.error("Contact form validation failed due to :"+errors.toString());
            return  "contact.html";
        }

        //here the data is in controller layer
        contactService.saveMessagedetails(contact);

        return "redirect:/contact";
    }

    //creating the method to deal the display message functionality for admin

//    @RequestMapping("/displayMessages")
//    public ModelAndView displayMessages(Model model){
//        List<Contact> contactMsgs = contactService.findMsgsWithOpenStatus();
//        ModelAndView modelAndView = new ModelAndView("messages.html");
//        modelAndView.addObject("contactMsgs",contactMsgs);
//        return modelAndView;
//    }

    //changes done for pagination for contact_msg

    @RequestMapping("/displayMessages/page/{pageNum}")
    public ModelAndView displayMessages(Model model , @PathVariable(name = "pageNum") int pageNum , @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir){
        Page<Contact> msgPage = contactService.findMsgsWithOpenStatus(pageNum,sortField,sortDir);
        List<Contact> contactMsgs = msgPage.getContent();
        ModelAndView modelAndView = new ModelAndView("messages.html");
        model.addAttribute("currentPage" , pageNum);
        model.addAttribute("totalPages" , msgPage.getTotalPages());
        model.addAttribute("totalMsgs" , msgPage.getTotalElements());
        model.addAttribute("sortField" , sortField);
        model.addAttribute("sortDir" , sortDir);
        model.addAttribute("reverseSortDir" , sortDir.equals("asc") ? "desc" : "asc"); // order define by the value passed
        modelAndView.addObject("contactMsgs",contactMsgs);
        return modelAndView;
    }

    @RequestMapping(value = "/closeMsg",method = GET)
    public String closeMsg(@RequestParam int id){
        contactService.updateMsgStatus(id);
        return "redirect:/displayMessages/page/1?sortField=name&sortDir=desc;";
    }

//    @RequestMapping(value = "/OldMsg",method = GET)
//    public String OldMsg(@RequestParam int id , Authentication authentication){
//        contactService.updateMsgStatus_Old(id,authentication.getName());
//        return "redirect:/displayMessages";
//    }
}
