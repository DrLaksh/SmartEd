package EasyBytes.SpringBoot.SchoolApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

//@Data // this create issue with many:many relations , stackOverflowError issue , instead use getter and setter
@Getter
@Setter
@Entity
@Table(name = "class") //name od db and class is different
public class EazyClass extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int classId;

    @NotBlank(message = "Name must not be Blank ")
    @Size(min = 3,message = "Enter full name")
    private String name;
    //one to many scenario , one class have many persons
    @OneToMany(mappedBy = "eazyClass" , fetch = FetchType.LAZY, cascade = CascadeType.PERSIST , targetEntity = Person.class)
    //lazy - load when needed , PRESIST , save both class in single transaction ,

    private Set<Person> persons;

}
