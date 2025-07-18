package EasyBytes.SpringBoot.SchoolApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // for getter setter
@AllArgsConstructor // for all constructor
@NoArgsConstructor // for no constructor
public class Response {

    private String statusCode; // use to show if save or not
    private String statusMsg; // data


}
