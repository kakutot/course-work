package check.data.ajax.comment;

import check.data.ajax.ResponseType;

public class DeleteCommentAjaxResponse {
    private ResponseType responseType;

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
