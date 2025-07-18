package EasyBytes.SpringBoot.SchoolApp.repository;

import EasyBytes.SpringBoot.SchoolApp.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Integer> {

    // these are the derived name class , orderBy is required keyword by static class and and Jpa repository have page and sorting repo as child and by default sorting is ascending
    List<Courses> findByOrderByNameDesc();

    List<Courses> findByOrderByName();
}
