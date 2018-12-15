package check.repos;

import check.data.db.domain.Faculty;
import check.data.db.domain.Teacher;
import check.data.db.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeacherRepo extends CrudRepository<Teacher,Integer> {
    List<Teacher> findByFirstNameContaining (String firstName);
}
