package EasyBytes.SpringBoot.SchoolApp.repository;

import EasyBytes.SpringBoot.SchoolApp.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Integer> {
//removed all previous code

List<Contact> findByStatus(String status);

// Here first priority given to the @Query then JPA method name ,example for JPQL
//@Query("SELECT c FROM Contact  c WHERE c.status = :status") // : status is the status passed by the method , this takes the pagination from the method and use it
//Page<Contact> findByStatus(String status , Pageable pageable); // added for pagination and dynamic sorting

//Example for SQL Query
@Query(value = "SELECT * FROM contact_msg  WHERE status = :status" , nativeQuery = true)
    Page<Contact> findByStatuswithQuery(@Param("status") String state , Pageable pageable); //can use param to add name if not given

    @Transactional
    @Modifying // use this two annotation on all except select query
    @Query("UPDATE Contact c SET c.status = ?1 WHERE c.contactId = ?2 ") // ?1 is for status value as its 1 and 2 as id
    int updateStatusById(String status , int id);


//adding method findOpenMsgs and UpdateMgsStatus for nativeQuery Implementation

    Page<Contact> findOpenMsgs(@Param("status") String status , Pageable pageable);

    @Transactional
    @Modifying
    int updateMsgStatus(String status , int id );

}
