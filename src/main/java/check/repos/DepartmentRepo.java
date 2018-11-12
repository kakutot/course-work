package check.repos;

import check.data.db.domain.Department;
import check.data.db.domain.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface DepartmentRepo extends JpaRepository<Department,Integer> {
    List<Department> findByDeptNameContaining(String deptName);
    Department findDistinctByDeptName(String deptName);
}
