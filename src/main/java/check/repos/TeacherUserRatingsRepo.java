package check.repos;

import check.data.db.domain.RatingsCompositeId;
import check.data.db.domain.TeacherUserRatings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherUserRatingsRepo extends
        JpaRepository<TeacherUserRatings, RatingsCompositeId> {

}
