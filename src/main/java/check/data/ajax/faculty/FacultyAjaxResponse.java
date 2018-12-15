package check.data.ajax.faculty;

import check.data.ajax.ResponseType;
import check.data.db.domain.Faculty;

public class FacultyAjaxResponse {
    ResponseType responseType;
    Faculty faculty;

    public FacultyAjaxResponse() {
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }
}
