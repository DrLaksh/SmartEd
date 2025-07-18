package EasyBytes.SpringBoot.SchoolApp.repository;

import EasyBytes.SpringBoot.SchoolApp.model.EazyClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EazyClassRepository extends JpaRepository<EazyClass , Integer> {

}
