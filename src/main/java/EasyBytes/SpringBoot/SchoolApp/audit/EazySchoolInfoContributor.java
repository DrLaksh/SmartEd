package EasyBytes.SpringBoot.SchoolApp.audit;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
//this class is for info in actuator
public class EazySchoolInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        Map<String , String> eazyMap = new HashMap<String,String>();
        eazyMap.put("App Name" , "EazySchool");
        eazyMap.put("App Description" , "Eazy School App by Lakshya Acharya");
        eazyMap.put("App Version","1.1.5.0");
        eazyMap.put("Contact Email ","lakshyaacharya.engineer@gmail.com");
        eazyMap.put("Contact Number ","+917999415859");
        builder.withDetail("EazySchool-Info",eazyMap);

    }
}
