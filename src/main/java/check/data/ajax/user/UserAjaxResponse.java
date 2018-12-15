package check.data.ajax.user;

import check.data.db.domain.User;

public class UpdateUserAjaxResponse {
    User userResponse;

    public UpdateUserAjaxResponse(){

    }

    public User getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(User userResponse) {
        this.userResponse = userResponse;
    }
}
