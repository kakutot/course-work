package check.data.ajax.faculty;

import check.data.db.domain.Faculty;

public class FacultyAjaxRequest {
    Faculty faculty;

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
}
