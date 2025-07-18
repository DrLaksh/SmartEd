package EasyBytes.SpringBoot.SchoolApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Address  extends  BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;

    @NotBlank(message = "Address must not be blank")
    @Size(min = 5 , message = "mention the address with landmark")
    private String address1;


    private String address2;

    @NotBlank(message = "City must not be blank")
    @Size(min = 3 , message = "City name must be long 3 characters long")
    private String city;

    @NotBlank(message = "State name must not be blank")
    @Size(min = 3 , message = "Use full state name ")
    private String state;

    @NotBlank(message = "Zip code must not be blank")
    @Pattern(regexp = "(^$|[0-9]{6})" , message = "Zip code must be 6 digits")
    private String zipCode;


}
