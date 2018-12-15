package check.data.ajax.user;

import check.data.ajax.ResponseType;
import check.data.db.domain.User;

public class UpdateUserAjaxRequest {
    User userRequest;
    ResponseType responseType;

    public UpdateUserAjaxRequest(){

    }

    public UpdateUserAjaxRequest(User userRequest, ResponseType responseType) {
        this.userRequest = userRequest;
        this.responseType = responseType;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public User getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(User userRequest) {
        this.userRequest = userRequest;
    }
}
