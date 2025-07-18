package EasyBytes.SpringBoot.SchoolApp.service;
import EasyBytes.SpringBoot.SchoolApp.constants.contactStatusConstants;
import EasyBytes.SpringBoot.SchoolApp.model.Person;
import EasyBytes.SpringBoot.SchoolApp.model.Roles;
import EasyBytes.SpringBoot.SchoolApp.repository.PersonRepository;
import EasyBytes.SpringBoot.SchoolApp.repository.RolesRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createNewPerson(@Valid Person person) {
        boolean isSaved = false;
        Roles roles = rolesRepository.getByRoleName(contactStatusConstants.STUDENT_ROLE);
        person.setRoles(roles);
        person.setPwd(passwordEncoder.encode(person.getPwd()));//changes for encryption
        person = personRepository.save(person);
        if (null != person && person.getPersonId() > 0){
            isSaved = true;
        }
        return isSaved;
    }
}
