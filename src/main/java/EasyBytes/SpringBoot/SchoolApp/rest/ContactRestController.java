package EasyBytes.SpringBoot.SchoolApp.rest;


import EasyBytes.SpringBoot.SchoolApp.model.Contact;
import EasyBytes.SpringBoot.SchoolApp.model.Response;
import EasyBytes.SpringBoot.SchoolApp.repository.ContactRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import EasyBytes.SpringBoot.SchoolApp.constants.contactStatusConstants;

import javax.print.attribute.standard.Media;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
//@Controller // code at end  for contact restController
@RestController
@RequestMapping(path = "/api/contact")
//@RequestMapping(path = "/api/contact",produces = {MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE})
//this converts the output of the URL from JSOn to XML and any required format is allowed by help of jackson library
@CrossOrigin(origins = "*")
public class ContactRestController {

    @Autowired
    ContactRepository contactRepository;

    @GetMapping("/getMessagesByStatus")
    //@ResponseBody// this will return result without View , but not used in @RestController
    public List<Contact> getMessagesByStatus(@RequestParam(name = "status") String status){ // this return list of open/close status contact_msg
        return contactRepository.findByStatus(status);
    }

    // using by @RequestBody

    @GetMapping("/getAllMsgsByStatus")
    //@ResponseBody
    public List<Contact> getAllMsgsByStatus(@RequestBody Contact contact){
        if (null != contact && null != contact.getStatus()){
            return contactRepository.findByStatus(contact.getStatus());
        }
        else{
            return List.of();
        }
    }
//this post mapping helps to get the saveMsg value and save it into db via contactRepository
    @PostMapping("/saveMsg")
    public ResponseEntity<Response> saveMsg(@RequestHeader("invocationFrom") String invocationFrom , @Valid @RequestBody Contact contact){
        log.info(String.format("Header invocationFrom = %s" , invocationFrom));//this used to check the header info
        contactRepository.save(contact);
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message Saved Successfully");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMsgSaved","true")
                .body(response);//always pass whatever passed in the code
    }
//This is to delete the data from db via deleteById

    @DeleteMapping("/deleteMsg")
    public ResponseEntity<Response> deleteMsg(RequestEntity<Contact> requestEntity){
        HttpHeaders headers = requestEntity.getHeaders();
        headers.forEach((key,value) -> {
            log.info(String.format("Header '%s' = %s" , key , value.stream().collect(Collectors.joining("|"))));
        }); // taking multiple headers iterate them and create it as the key and value and join values with | sign
        Contact contact = requestEntity.getBody();
        contactRepository.deleteById(contact.getContactId());
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message Deleted Successfully");
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }
//updating the close open status to close via rest APi request

    @PatchMapping("/closeMsg")
    public ResponseEntity<Response> closeMsg(@RequestBody Contact contactRequest){
        Response response = new Response();
        Optional<Contact> contact = contactRepository.findById(contactRequest.getContactId());
        if(contact.isPresent()){
            contact.get().setStatus(contactStatusConstants.CLOSE);
            contactRepository.save(contact.get());
        }
        else {
            response.setStatusCode("400");
            response.setStatusMsg("Invalid Contact ID received");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        response.setStatusCode("200");
        response.setStatusMsg("Message Closed Successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


}
//@GetMapping("/getMessagesByStatus")
//@ResponseBody// this will return result without View
//public List<Contact> getMessagesByStatus(@RequestParam(name = "status") String status){ // this return list of open/close status contact_msg
//    return contactRepository.findByStatus(status);
//}
//
//// using by @RequestBody
//
//@GetMapping("/getAllMsgsByStatus")
//@ResponseBody
//public List<Contact> getAllMsgsByStatus(@RequestBody Contact contact){
//    if (null != contact && null != contact.getStatus()){
//        return contactRepository.findByStatus(contact.getStatus());
//    }
//    else{
//        return List.of();
//    }
//}
