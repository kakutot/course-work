package check.data.db.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    public int teacherId;

    @Column(name = "first_name",nullable = false)
    public String firstName;

    @Column(name = "last_name",nullable = false)
    public String lastName;

    @Column(name = "patr_name",nullable = false)
    public String patrName;

    @Column(name = "dob",nullable = false)
    public String dateOfBirth;

    @Column(name = "gender",nullable = false)
    public Boolean gender;

    @Column(name = "neg_rating",nullable = false)
    public int posRating;

    @Column(name = "pos_rating",nullable = false)
    public int negRating;

    @JsonIgnoreProperties("teachers")
    @ManyToOne()
    @JoinColumn(name="dept_id", nullable=false)
    public Department department;

    @JsonIgnoreProperties("teacher")
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.REFRESH,orphanRemoval = true,mappedBy="teacher")
    private Set<Comment> comments = new HashSet<>(0);

    public Teacher (){
    }

    public Teacher (String firstName){
        this.firstName = firstName;
    }
    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatrName() {
        return patrName;
    }

    public void setPatrName(String patrName) {
        this.patrName = patrName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public int getPosRating() {
        return posRating;
    }

    public void setPosRating(int posRating) {
        this.posRating = posRating;
    }

    public int getNegRating() {
        return negRating;
    }

    public void setNegRating(int negRating) {
        this.negRating = negRating;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patrName='" + patrName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", gender=" + gender +
                ", posRating=" + posRating +
                ", negRating=" + negRating +
                ", department=" + department +
                '}';
    }
}
