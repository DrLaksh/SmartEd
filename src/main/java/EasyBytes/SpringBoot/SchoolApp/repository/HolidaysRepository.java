package EasyBytes.SpringBoot.SchoolApp.repository;

import EasyBytes.SpringBoot.SchoolApp.model.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//change class to interface and extends CRUD repository
public interface HolidaysRepository extends CrudRepository<Holiday,String> {


}
