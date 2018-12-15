package check.data.ajax.comment;

public class AddCommentAjaxRequest {
    private String msg;
    private String username;

    public AddCommentAjaxRequest(String msg, String username) {
        this.msg = msg;
        this.username = username;
    }

    public AddCommentAjaxRequest(){
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public AddCommentAjaxRequest(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
