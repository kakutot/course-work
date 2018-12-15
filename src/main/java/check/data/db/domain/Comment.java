package check.data.db.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "id",nullable = false)
    public int commentId;

    @JsonIgnoreProperties("comments")
    @ManyToOne()
    @JoinColumn(name="teacher_id", nullable=false)
    public Teacher teacher;

    @JsonIgnoreProperties("comments")
    @ManyToOne()
    @JoinColumn(name="user_id", nullable=false)
    public User user;

    @Column(name = "message",nullable=false)
    public String message;

    @Column(name = "comment_date",nullable = false)
    public String commentDate;

    public Comment() {
    }

    public Comment(Teacher teacher, User user, String message,String commentDate) {
        this.teacher = teacher;
        this.user = user;
        this.message = message;
        this.commentDate = commentDate;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", message='" + message + '\'' +
                ", commentDate='" + commentDate + '\'' +
                '}';
    }
}
