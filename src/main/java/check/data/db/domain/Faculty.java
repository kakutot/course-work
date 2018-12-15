package check.data.db.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "faculty",uniqueConstraints = {
        @UniqueConstraint(columnNames = "faculty_name")})
public class Faculty implements java.io.Serializable{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int facultyId;

    @Column(name = "faculty_name",nullable = false)
    private String facultyName;

    @JsonIgnoreProperties("faculty")
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.REFRESH,orphanRemoval = true,mappedBy="faculty")
    private Set<Department> departments = new HashSet<Department>(0);

    public Faculty() {};

    public Faculty(String facultyName) {
        this.facultyName = facultyName;
    }

    public int getFacultyId() {
        return facultyId;
    }
    public void setFacultyId(int facultyId) {
        this.facultyId= facultyId;
    }
    public String getFacultyName() {
        return facultyName;
    }
    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public Set<Department> getDepartments() {
        return departments;
    }
    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }
    @Override
    public String toString() {
        return "Faculty [id=" + facultyId+ ", facultyName=" + facultyName + "]";
    }

    @PostRemove
    public void nullifyChilds(){
        departments.forEach((dept)->dept.setFaculty(null));
    }
}