package edu.hogwarts.studentadmin.models;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "house_name")
    private House house;


    private boolean prefect;
    private int enrollmentYear;
    private int graduationYear;
    private boolean graduated;

    private int schoolYear;

    public Student(){};

    public Student(String firstName, String middleName, String lastName, House house, int enrollmentYear, int schoolYear, int graduationYear, boolean graduated) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.house = house;
        this.enrollmentYear = enrollmentYear;
        this.schoolYear = schoolYear;
        this.graduationYear = graduationYear;
        this.graduated = graduated;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public boolean isPrefect() {
        return prefect;
    }

    public void setPrefect(boolean prefect) {
        this.prefect = prefect;
    }

    public int getEnrollmentYear() {
        return enrollmentYear;
    }

    public void setEnrollmentYear(int enrollmentYear) {
        this.enrollmentYear = enrollmentYear;
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(int graduationYear) {
        this.graduationYear = graduationYear;
    }

    public boolean isGraduated() {
        return graduated;
    }

    public void setGraduated(boolean graduated) {
        this.graduated = graduated;
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }
}
