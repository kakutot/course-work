package check.data.db.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
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

    /*
    @OneToMany(mappedBy="department")
    private Set<Teacher> teachers = new HashSet<Teacher>(0);
    */
    @JsonIgnoreProperties("department")
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.REFRESH,orphanRemoval = true,mappedBy="department")
    private Set<User> users = new HashSet<>(0);

    public Department() {}
    public Department(String deptName) {
        this.deptName = deptName;
    }
    public int getId() {
        return deptId;
    }

    public void setId(int id) {
        this.deptId = id;
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

    public String toString() {
        return "Department [id=" + deptId+ ", deptName=" + deptName + ", faculty=" + faculty + "]";
    }

}
