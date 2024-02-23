package edu.hogwarts.studentadmin.api.dto.students;

public class StudentFullNameDTO {
    private String fullName;
    private String house;

    public StudentFullNameDTO(String fullName, String house) {
        this.fullName = fullName;
        this.house = house;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }
}
