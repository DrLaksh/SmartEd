package EasyBytes.SpringBoot.SchoolApp.model;

import EasyBytes.SpringBoot.SchoolApp.annotation.FieldsValueMatch;
import EasyBytes.SpringBoot.SchoolApp.annotation.PasswordValidator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.apache.logging.log4j.message.Message;

import java.util.HashSet;
import java.util.Set;

@Data // lombok
@Entity // person table in db
@FieldsValueMatch.List({ //validation on password and email , mention outside
        @FieldsValueMatch(field = "pwd", fieldMatch = "confirmPwd",message = "Passwords do not match!"),
        @FieldsValueMatch(field = "email", fieldMatch = "confirmEmail",message = "Emails do not match!")})
public class Person extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personId;
    @NotBlank(message = "Name must not be blank")
    @Size(min = 3 , message = "use full name")
    private String name;
    @NotBlank(message = "Mobile number should not be blank")
    @Size(min = 10, message = "Mobile number must be 10 digits long")
    private String mobileNumber;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Confirm email must not be blank")
    @Email(message = "Please provide a valid confirm email address")
    @Transient// by this the fields is not considered by JPA in db operations
    @JsonIgnore
    private String confirmEmail;
    @NotBlank(message =  "Password Must not be Blank")
    @Size(min = 5 , message = "Password must be 5 characters long")
    @PasswordValidator
    @JsonIgnore
    private String pwd;
    @NotBlank(message =  "Confirm Password Must not be Blank")
    @Size(min = 5 , message = "Confirm password must be 5 characters long")
    @Transient
    @JsonIgnore
    private String confirmPwd;
    //1:1 relationship
    @OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.PERSIST, targetEntity = Roles.class)
    //eager , load the details automatically when it loads
    //PRESIST , save roles when person is saved
    //join , role_id - column in person , roleId - column inside role , nullable means permanently there
    @JoinColumn(name = "role_id" , referencedColumnName = "roleId" , nullable = false)
    private Roles roles;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL,targetEntity = Address.class)
    //all - all means all operation shows on address
    @JoinColumn(name = "address_id" , referencedColumnName = "addressId" , nullable = true)
    //not required can be null too
    private Address address;

    //for many to one eazyClass

    @ManyToOne(fetch = FetchType.LAZY,optional = true)//optional as this can be blank too
    @JoinColumn(name = "class_id", referencedColumnName = "classId", nullable = true)
    private EazyClass eazyClass;

    //for many to many courses class

    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.PERSIST)
    @JoinTable(name = "person_courses",
            joinColumns = {@JoinColumn(name = "person_id",referencedColumnName = "personId")},
            inverseJoinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "courseId")}
    )
    private Set<Courses> courses = new HashSet<>();
}
