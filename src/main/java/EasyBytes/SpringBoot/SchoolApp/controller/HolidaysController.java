package EasyBytes.SpringBoot.SchoolApp.controller;

import EasyBytes.SpringBoot.SchoolApp.model.Holiday;
import EasyBytes.SpringBoot.SchoolApp.repository.HolidaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Controller
public class HolidaysController {

    @Autowired
    private HolidaysRepository holidaysRepository;

    @GetMapping("/holidays/{display}")
    //here adding the requestParam annotatons for URL and filtering
    public String displayHolidays(@PathVariable String display, Model model){
        //logic to control the flow of the condition from backend to frontend
        if (null != display && display.equals("all")){
            model.addAttribute("festival",true);
            model.addAttribute("federal",true);
        } else if (null != display && display.equals("federal")) {
            model.addAttribute("federal",true);
        } else if (null != display && display.equals("festival")) {
        model.addAttribute("festival",true);
    }

        Iterable<Holiday> holidays = holidaysRepository.findAll();
        //findall take iterable and changing iterable to list via StreamSupport

        List<Holiday> holidayList = StreamSupport.stream(holidays.spliterator(),false).collect(Collectors.toList());

          Holiday.Type[] types = Holiday.Type.values();
          for (Holiday.Type type : types){
              model.addAttribute(type.toString(),
                      (holidayList.stream().filter(holiday -> holiday.getType().equals(type)).
                              collect(Collectors.toList())));
          }
          return "holidays.html";
    }

}
