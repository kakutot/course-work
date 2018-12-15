package check.repos;

import check.data.db.domain.Department;
import check.data.db.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<User,Integer> {
    User findByUsername(String username);
    List<User> findByUsernameContaining(String username);
    User findByEmailAddress(String emailAddress);
    User findByUserId(Integer userId);
    List<User> findByDepartment(Department department);
}
