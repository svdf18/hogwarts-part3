package edu.hogwarts.studentadmin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class House {

    public enum HouseName {
        GRYFFINDOR, HUFFLEPUFF, RAVENCLAW, SLYTHERIN;
    }

    public enum HouseColors {
        RED, GOLD, YELLOW, BLACK, BLUE, SILVER, GREEN;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private HouseName name;
    private String founder;
    @ElementCollection(targetClass = HouseColors.class)
    @CollectionTable(name = "house_colors", joinColumns = @JoinColumn(name = "house_id"))
    @Enumerated(EnumType.STRING)
    private List<HouseColors> colors;

    @JsonIgnore
    @OneToMany(mappedBy = "house")
    private List<Student> students;

    public House(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HouseName getName() {
        return name;
    }

    public void setName(HouseName name) {
        this.name = name;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public List<HouseColors> getColors() {
        return colors;
    }

    public void setColors(List<HouseColors> colors) {
        this.colors = colors;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        if (students == null) {
            students = new ArrayList<>();
        }
        students.add(student);
    }
}
