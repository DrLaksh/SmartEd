package EasyBytes.SpringBoot.SchoolApp.annotation;

import EasyBytes.SpringBoot.SchoolApp.validations.FieldsValueMatcherValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented//added in java docs
@Constraint(validatedBy = FieldsValueMatcherValidator.class)
//define which class will validate this annotation
@Target({ElementType.TYPE})
//WILL TELL , WHERE TO USE THIS ANNOTATION , HERE ITS USE ON Type-class , interface and enum
@Retention(RetentionPolicy.RUNTIME)
//when will it call and use , and till when its alive
public @interface FieldsValueMatch { // @interface used to tell its annotation defined
    Class<?>[ ] groups() default {};
//bean validation group feature , define in all annotations
    Class<? extends Payload>[] payload() default {};
//Extra Metadata
    String message() default "Fields values don't match ! ";
//Error message
    String field();
//first field to compare
    String fieldMatch();
//second field to compare
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List{
        FieldsValueMatch[] value();
    }
    //here this annotations are repeated to use this for multiple value as passed in List

}
