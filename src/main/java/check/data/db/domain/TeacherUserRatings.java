package check.data.db.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Teacher_User_Ratings")
public class Teacher_User_Ratings{
    @EmbeddedId
    public CompositeId compositeId;

    @Column(name = "isNeg")
    public Boolean isNeg;

    @Column(name = "isPos")
    public Boolean isPos;

    public Teacher_User_Ratings() {
    }

    public Teacher_User_Ratings(CompositeId compositeId, Boolean isNeg, Boolean isPos) {
        this.compositeId = compositeId;
        this.isNeg = isNeg;
        this.isPos = isPos;
    }

    public CompositeId getCompositeId() {
        return compositeId;
    }

    public void setCompositeId(CompositeId compositeId) {
        this.compositeId = compositeId;
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

    public static class CompositeId implements Serializable {
        @NotNull
        @Size(max = 20)
        private String teacherId;

        @NotNull
        @Size(max = 20)
        private String userId;

        public CompositeId() {
        }

        public String getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(String teacherId) {
            this.teacherId = teacherId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CompositeId that = (CompositeId) o;
            return Objects.equals(teacherId, that.teacherId) &&
                    Objects.equals(userId, that.userId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(teacherId, userId);
        }
    }
}
