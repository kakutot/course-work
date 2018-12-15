package check.data.db.domain;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Embeddable
public class RatingsCompositeId implements Serializable {
    @NotNull
    @Size(max = 20)
    private String teacherId;

    @NotNull
    @Size(max = 20)
    private String userId;

    public RatingsCompositeId() {
    }

    public RatingsCompositeId(String teacherId,String userId) {
        this.teacherId = teacherId;
        this.userId = userId;
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
}
