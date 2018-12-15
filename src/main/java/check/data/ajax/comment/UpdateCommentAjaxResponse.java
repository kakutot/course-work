package check.data.ajax.comment;

import check.data.ajax.ResponseType;

public class UpdateCommentAjaxResponse {
    private ResponseType responseType;
    private String msg;

    public UpdateCommentAjaxResponse() {
    }

    public UpdateCommentAjaxResponse(ResponseType responseType, String msg) {
        this.responseType = responseType;
        this.msg = msg;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
