package check.data.ajax.teacher;

import check.data.ajax.ResponseType;
import check.data.db.domain.Teacher;

public class TeacherAjaxResponse {
    Teacher teacher;
    ResponseType responseType;

    public TeacherAjaxResponse() {
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }
}
