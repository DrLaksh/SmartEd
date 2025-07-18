package EasyBytes.SpringBoot.SchoolApp.repository;

import EasyBytes.SpringBoot.SchoolApp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
