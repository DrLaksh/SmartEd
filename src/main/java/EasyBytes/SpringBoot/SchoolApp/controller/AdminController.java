package EasyBytes.SpringBoot.SchoolApp.controller;

import EasyBytes.SpringBoot.SchoolApp.model.Courses;
import EasyBytes.SpringBoot.SchoolApp.model.EazyClass;
import EasyBytes.SpringBoot.SchoolApp.model.Person;
import EasyBytes.SpringBoot.SchoolApp.repository.CoursesRepository;
import EasyBytes.SpringBoot.SchoolApp.repository.EazyClassRepository;
import EasyBytes.SpringBoot.SchoolApp.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    EazyClassRepository eazyClassRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CoursesRepository coursesRepository;

    @RequestMapping("/displayClasses")
    public ModelAndView displayClasses(Model model){
        //changes to show classes here
        List<EazyClass> eazyClasses = eazyClassRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("classes.html");
        //adding here to the ui , thymeleaf tag have easyClass tag defined
        modelAndView.addObject("eazyClasses" , eazyClasses);// attributeName is used by Thymeleaf
        modelAndView.addObject("eazyClass" , new EazyClass());
        return modelAndView;
    }

    @PostMapping("/addNewClass")
    public ModelAndView addNewClass(Model model , @ModelAttribute("eazyClass") EazyClass eazyClass){
        eazyClassRepository.save(eazyClass);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping("/deleteClass")
    public ModelAndView deleteClass(Model model , @RequestParam int id){
        Optional<EazyClass> eazyClass = eazyClassRepository.findById(id);
        for(Person person : eazyClass.get().getPersons()){
            person.setEazyClass(null);
            personRepository.save(person);
        }
        eazyClassRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @GetMapping("/displayStudents")
    public ModelAndView displayStudents(Model model , @RequestParam int classId , HttpSession session , @RequestParam(value = "error",required = false) String error){
        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("students.html");
        //get all the class details
        Optional<EazyClass> eazyClass = eazyClassRepository.findById(classId);
        modelAndView.addObject("eazyClass",eazyClass.get());//add the class objecy
        modelAndView.addObject("person",new Person());//add person object
        session.setAttribute("eazyClass",eazyClass.get());
        if (error != null ){
            errorMessage = "Invalid Email Entered !!";
            modelAndView.addObject("errorMessage",errorMessage);
        }
        return modelAndView;
    }

    @PostMapping("/addStudent")
    public ModelAndView addStudent(Model model , @ModelAttribute("person") Person person , HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        EazyClass eazyClass = (EazyClass) session.getAttribute("eazyClass");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if(personEntity == null || !(personEntity.getPersonId() > 0 )){
            modelAndView.setViewName("redirect:/admin/displayStudents?classId="+eazyClass.getClassId()+"&error=true");
            return modelAndView;
        }
        personEntity.setEazyClass(eazyClass);
        personRepository.save(personEntity);
        eazyClass.getPersons().add(personEntity);
        eazyClassRepository.save(eazyClass);
        modelAndView.setViewName("redirect:/admin/displayStudents?classId="+eazyClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/deleteStudent")
    public ModelAndView deleteStudent(Model model , @RequestParam int personId , HttpSession session){
        EazyClass eazyClass = (EazyClass) session.getAttribute("eazyClass");
        Optional<Person> person = personRepository.findById(personId);
        person.get().setEazyClass(null);
        eazyClass.getPersons().remove(person.get());
        EazyClass eazyClassSaved = eazyClassRepository.save(eazyClass);
        session.setAttribute("eazyClass",eazyClassSaved);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId="+eazyClass.getClassId());
        return modelAndView;
    }

//    @GetMapping("/displayCourses")
//    public ModelAndView displayCourses(Model model){
//        List<Courses> courses = coursesRepository.findAll();
//        ModelAndView modelAndView = new ModelAndView("courses_secure.html");
//        modelAndView.addObject("courses",courses);
//        modelAndView.addObject("course",new Courses());
//        return modelAndView;
//    }

    @GetMapping("/displayCourses") // doing changes for sorting , also in coursesRepository
    public ModelAndView displayCourses(Model model){
 //       List<Courses> courses = coursesRepository.findByOrderByName(); // adding static sorting part here
//        List<Courses> courses = coursesRepository.findAll();
        //Dynamic Sorting by Sort method
        List<Courses> courses = coursesRepository.findAll(Sort.by("name").ascending());
        ModelAndView modelAndView = new ModelAndView("courses_secure.html");
        modelAndView.addObject("courses",courses);
        modelAndView.addObject("course",new Courses());
        return modelAndView;
    }

    @PostMapping("/addNewCourse")
    public ModelAndView addNewCourse(Model model, @ModelAttribute("course") Courses courses){
        ModelAndView modelAndView = new ModelAndView();
        coursesRepository.save(courses);
        modelAndView.setViewName("redirect:/admin/displayCourses");
        return modelAndView;
    }

    @GetMapping("/viewStudents")
    public ModelAndView viewStudents(Model model , @RequestParam int id , HttpSession session
    , @RequestParam(value = "error" , required = false) String error){
        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("course_students.html");
        Optional<Courses> courses = coursesRepository.findById(id);
        modelAndView.addObject("courses",courses.get());
        modelAndView.addObject("person", new Person());
        //changes due to addStudentsToCourse Method , adding HttpSession Here and error
        session.setAttribute("courses",courses.get());
        if (error != null){
            errorMessage = "Invalid Email Entered !! ";
            modelAndView.addObject("errorMessage",errorMessage);
        }
        return modelAndView;

    }

    @PostMapping("/addStudentToCourse")
    public ModelAndView addStudentToCourse(Model model , @ModelAttribute("person") Person person , HttpSession session){
        ModelAndView modelAndView = new ModelAndView();

        Courses courses = (Courses) session.getAttribute("courses");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if(personEntity == null || !(personEntity.getPersonId()>0)){
            modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId()+"&error=true");
            return modelAndView;
        }
        personEntity.getCourses().add(courses);
        courses.getPersons().add(personEntity);
        personRepository.save(personEntity);
        session.setAttribute("courses",courses);
        modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId());
        return modelAndView;
    }

    @GetMapping("/deleteStudentFromCourse")
    public  ModelAndView deleteStudentFromCourse(Model model, @RequestParam int personId , HttpSession session){
        Courses courses = (Courses) session.getAttribute("courses");
        Optional<Person> person = personRepository.findById(personId);
        person.get().getCourses().remove(courses);
        courses.getPersons().remove(person);
        personRepository.save(person.get());
        session.setAttribute("courses",courses);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/viewStudents?id="+courses.getCourseId());
        return modelAndView;
    }

}
