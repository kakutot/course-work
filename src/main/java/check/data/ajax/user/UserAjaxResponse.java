package check.data.ajax.user;

import check.data.ajax.ResponseType;
import check.data.db.domain.User;

public class UserAjaxResponse {
    User userResponse;
    ResponseType responseType;

    public UserAjaxResponse(){
        responseType = ResponseType.OK;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public User getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(User userResponse) {
        this.userResponse = userResponse;
    }
}
