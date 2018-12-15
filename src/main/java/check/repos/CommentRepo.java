package check.repos;

import check.data.db.domain.Teacher;
import check.data.db.domain.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import  check.data.db.domain.Comment;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Integer> {
    List<Comment> findAllByTeacher(Teacher teacher, Sort sort);
    List<Comment> findAllByUser(User user, Sort sort);
}
