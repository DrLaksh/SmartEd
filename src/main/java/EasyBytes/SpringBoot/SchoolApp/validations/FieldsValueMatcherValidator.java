package EasyBytes.SpringBoot.SchoolApp.validations;

import EasyBytes.SpringBoot.SchoolApp.annotation.FieldsValueMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FieldsValueMatcherValidator implements ConstraintValidator<FieldsValueMatch,Object> {

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldsValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraint) {
        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        //bean wrapper impl healps to deal with field value of objects , getPropertyValue , gets the value of object
        Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);
        if(fieldValue != null){
            return fieldValue.equals(fieldMatchValue);
        }else {
            return fieldMatchValue == null;
        }
    }
}
// if(fieldValue != null){
//            if (fieldValue.toString().startsWith("$2a")){//changes for encryption
//                return true;
//            }else{
//                return fieldValue.equals(fieldMatchValue);
//            }
//        }else {
//            return fieldMatchValue == null;
//        }