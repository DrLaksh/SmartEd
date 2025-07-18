package EasyBytes.SpringBoot.SchoolApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity // if same name then this is enough to match object to table
@Table(name = "contact_msg") // mention as name of object lass and table is different
//Adding named Queries here
@NamedQueries({
        @NamedQuery(name = "Contact.findOpenMsgs", query = "SELECT c FROM Contact c WHERE c.status = :status"),
        @NamedQuery(name = "Contact.updateMsgStatus" , query = "UPDATE Contact c SET c.status = ?1 WHERE c.contactId = ?2")
})
public class Contact extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GenericGenerator() Depreciated method, don't use this instead use Identity as generation type in value
    @Column(name = "contact_id")
    private int contactId;

    @NotBlank(message = "Name must be not blank")
    @Size(min =3 , message = "Please provide full name")
    private String name;

    @NotBlank(message = "Mobile Number must not be blank")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be of 10 digits")
//    (regexp = "(^$|[0-9]{10})") ^$ means no character,  | and, [0-9] only 0 to 9 is allowed , {10} data must be 10 values
    private String mobileNum;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please Provide a valid email")
    private String email;

    @NotBlank(message = "Subject must not be Blank")
    @Size(min = 5, message = "Define proper Subject atleast more than a word long")
    private String subject;

    @NotBlank(message = "message must not be blank")
    @Size(min=10, message = "Provide the message in detail ")
    private String message;

    private String status;

}
