package check.data.ajax;

public class DeleteCommentAjaxResponse {
    ResponseType responseType;

    public DeleteCommentAjaxResponse() {
    }

    public DeleteCommentAjaxResponse(ResponseType responseType) {
        this.responseType = responseType;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }
}
