package check.data.db.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Teacher_User_Ratings")
public class TeacherUserRatings {
    @EmbeddedId
    public RatingsCompositeId compositeId;

    @Column(name = "is_neg")
    public Boolean isNeg;

    @Column(name = "is_pos")
    public Boolean isPos;

    public TeacherUserRatings(RatingsCompositeId compositeId, Boolean isNeg, Boolean isPos) {
        this.compositeId = compositeId;
        this.isNeg = isNeg;
        this.isPos = isPos;
    }

    public TeacherUserRatings() {
    }

    public Boolean getNeg() {
        return isNeg;
    }

    public void setNeg(Boolean neg) {
        isNeg = neg;
    }

    public Boolean getPos() {
        return isPos;
    }

    public void setPos(Boolean pos) {
        isPos = pos;
    }

    public RatingsCompositeId getCompositeId() {
        return compositeId;
    }

    public void setCompositeId(RatingsCompositeId compositeId) {
        this.compositeId = compositeId;
    }
}
