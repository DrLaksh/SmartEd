package EasyBytes.SpringBoot.SchoolApp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "holidays")
//also define primary key as its mandatory in JPA
public class Holiday extends BaseEntity{
//change to without final as rowmapper is required to create object

    @Id
    private  String day;


    private  String reason;
//need to define the type od column as its enum we use enumerated

    @Enumerated(EnumType.STRING)
    private  Type type;

    public enum Type{
        FEDERAL,
        FESTIVAL
    }

}
