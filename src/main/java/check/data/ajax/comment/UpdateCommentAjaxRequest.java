package check.data.ajax.comment;

public class UpdateCommentAjaxRequest {
    private String innerContent;
    private String username;

    public UpdateCommentAjaxRequest(String msg, String username) {
        this.innerContent = msg;
        this.username = username;
    }

    public UpdateCommentAjaxRequest(){
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UpdateCommentAjaxRequest(String msg) {
        this.innerContent = msg;
    }

    public String getInnerContent() {
        return innerContent;
    }

    public void setInnerContent(String innerContent) {
        this.innerContent = innerContent;
    }
}
