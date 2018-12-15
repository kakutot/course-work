package check.data.ajax;

import check.data.db.domain.Comment;

import java.util.List;
import java.util.Map;

public class MoreCommentAjaxResponse {
    String msg;
    List<Comment> comments;
    Map<String, String> errorMessages;

    public MoreCommentAjaxResponse(String msg, List<Comment> comments, Map<String, String> errorMessages) {
        this.msg = msg;
        this.comments = comments;
        this.errorMessages = errorMessages;
    }

    public MoreCommentAjaxResponse() {
    }

    public MoreCommentAjaxResponse(String msg, List<Comment> comments) {
        this.msg = msg;
        this.comments = comments;
    }

    public Map<String, String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(Map<String, String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
