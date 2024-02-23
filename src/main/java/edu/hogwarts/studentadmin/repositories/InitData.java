package edu.hogwarts.studentadmin.repositories;

import edu.hogwarts.studentadmin.models.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class InitData implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final HouseRepository houseRepository;
    private final CourseRepository courseRepository;

    public InitData(StudentRepository studentRepository,
                    TeacherRepository teacherRepository,
                    HouseRepository houseRepository,
                    CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.houseRepository = houseRepository;
        this.courseRepository = courseRepository;
    }

    public void run(String... args) {
        System.out.println("InitData is running");

        createHouses();
        createStudents();
        createTeachers();
        createCourses();
    }

    private void createHouses() {
        House house1 = new House("GRYFFINDOR", "Godric Gryffindor", "RED", "GOLD");
        houseRepository.save(house1);

        House house2 = new House("HUFFLEPUFF", "Helga Hufflepuff", "YELLOW", "BLACK");
        houseRepository.save(house2);

        House house3 = new House("RAVENCLAW", "Rowena Ravenclaw", "BLUE", "SILVER");
        houseRepository.save(house3);

        House house4 = new House("SLYTHERIN", "Salazar Slytherin", "GREEN", "SILVER");
        houseRepository.save(house4);
    }


    private void createStudents() {
        House gryffindor = houseRepository.findByName("GRYFFINDOR").orElseThrow();
        House hufflepuff = houseRepository.findByName("HUFFLEPUFF").orElseThrow();
        House ravenclaw = houseRepository.findByName("RAVENCLAW").orElseThrow();
        House slytherin = houseRepository.findByName("SLYTHERIN").orElseThrow();

        Student student1 = new Student("Harry", "James", "Potter", gryffindor, 1991, 1, 0, false);
        student1.setDateOfBirth(LocalDate.of(1980, 7, 31));
        student1.setPrefect(true);


        Student student2 = new Student("Hermione", "Jean", "Granger", gryffindor, 1991, 1, 0, false);
        student2.setDateOfBirth(LocalDate.of(1979, 9, 19));
        student2.setPrefect(true);


        Student student3 = new Student("Ron", "Bilius", "Weasley", gryffindor, 1991, 1, 0, false);
        student3.setDateOfBirth(LocalDate.of(1980, 3, 1));
        student3.setPrefect(false);


        Student student4 = new Student("Cedric", "", "Diggory", hufflepuff, 1991, 1, 0, false);
        student4.setDateOfBirth(LocalDate.of(1979, 10, 1));
        student4.setPrefect(true);

        Student student5 = new Student("Luna", "", "Lovegood", ravenclaw, 1991, 1, 0, false);
        student5.setDateOfBirth(LocalDate.of(1981, 2, 13));
        student5.setPrefect(true);

        Student student6 = new Student("Draco", "Lucius", "Malfoy", slytherin, 1991, 1, 0, false);
        student6.setDateOfBirth(LocalDate.of(1980, 6, 5));
        student6.setPrefect(true);

        Student student7 = new Student("Ginny", "Molly", "Weasley", gryffindor, 1991, 1, 0, false);
        student7.setDateOfBirth(LocalDate.of(1981, 8, 11));
        student7.setPrefect(true);

        Student student8 = new Student("Neville", "Frank", "Longbottom", gryffindor, 1991, 1, 0, false);
        student8.setDateOfBirth(LocalDate.of(1980, 7, 30));
        student8.setPrefect(false);

        Student student9 = new Student("Lavender", "Isobel", "Brown", gryffindor, 1991, 1, 0, false);
        student9.setDateOfBirth(LocalDate.of(1979, 5, 15));
        student9.setPrefect(false);

        Student student10 = new Student("Justin", "Finbar", "Finch-Fletchley", hufflepuff, 1991, 1, 0, false);
        student10.setDateOfBirth(LocalDate.of(1980, 10, 1));
        student10.setPrefect(false);

        Student student11 = new Student("Parvati", "Padma", "Patil", gryffindor, 1991, 1, 0, false);
        student11.setDateOfBirth(LocalDate.of(1979, 9, 20));
        student11.setPrefect(false);

        Student student12 = new Student("Millicent", "", "Bulstrode", slytherin, 1991, 1, 0, false);
        student12.setDateOfBirth(LocalDate.of(1979, 5, 1));
        student12.setPrefect(false);

        studentRepository.saveAll(List.of(
                student1, student2, student3, student4, student5, student6, student7, student8, student9, student10, student11, student12
        ));

//
//        studentRepository.saveAll(List.of(
//                student1, student2, student3, student4, student5, student6,
//                student7, student8, student9, student10, student11, student12
//        ));

    }

    private void createTeachers() {
        // Add teacher initialization logic here
    }

    private void createCourses() {
        // Add course initialization logic here
    }
}
