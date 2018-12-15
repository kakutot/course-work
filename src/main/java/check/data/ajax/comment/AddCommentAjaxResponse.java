package check.data.ajax.comment;

import check.data.ajax.ResponseType;
import check.data.db.domain.Comment;

import java.util.List;

public class AddCommentAjaxResponse {
    ResponseType responseType;
    List<Comment> comments;

    public AddCommentAjaxResponse(){
    }

    public AddCommentAjaxResponse(ResponseType responseType, List<Comment> comments) {
        this.responseType = responseType;
        this.comments = comments;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
