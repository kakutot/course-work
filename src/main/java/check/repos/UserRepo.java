package check.repos;

import check.data.db.domain.Department;
import check.data.db.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Integer> {
    User findByUsername(String username);
    List<User> findByUsernameContaining(String username);
    User findByEmailAddress(String emailAddress);
}
