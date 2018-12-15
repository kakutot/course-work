package check.data.db.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.xml.internal.ws.developer.Serialization;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Department",uniqueConstraints = {
        @UniqueConstraint(columnNames = "dept_name")})
public class Department {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "id",nullable = false)
    public int deptId;

    @Column(name = "dept_name",nullable = false)
    public String deptName;

    @JsonIgnoreProperties("departments")
    @ManyToOne()
    @JoinColumn(name="faculty_id", nullable=false)
    public Faculty faculty;

    @JsonIgnore()
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.DETACH,mappedBy="department")
    public Set<Teacher> teachers = new HashSet<>(0);

    @JsonIgnore()
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.DETACH,mappedBy="department")
    public Set<User> users = new HashSet<>(0);

    public Department() {}
    public Department(String deptName) {
        this.deptName = deptName;
    }
    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
    /*
    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

   public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
*/
    @PostRemove
    public void nullifyChilds(){
        users.forEach((user)->user.setDepartment(null));
        teachers.forEach((teacher)->teacher.setDepartment(null));
    }

}
