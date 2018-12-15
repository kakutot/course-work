package check.data.ajax.user;

import check.data.db.domain.User;

public class UserAjaxRequest {
    User userRequest;
    String deptName;
    boolean isAdmin;

    public UserAjaxRequest(){

    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public User getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(User userRequest) {
        this.userRequest = userRequest;
    }
}
