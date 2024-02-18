package edu.hogwarts.studentadmin.repositories;

import edu.hogwarts.studentadmin.models.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class InitData implements CommandLineRunner {

    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;
    private HouseRepository houseRepository;
    private CourseRepository courseRepository;

    public InitData(StudentRepository studentRepository,
                    TeacherRepository teacherRepository,
                    HouseRepository houseRepository,
                    CourseRepository courseRepository){
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.houseRepository = houseRepository;
        this.courseRepository = courseRepository;
    }

    public void run(String... args){
        System.out.println("InitData is running");

        //INIT Houses
        House house1 = new House();
        house1.setName(House.HouseName.GRYFFINDOR);
        house1.setFounder("Godric Gryffindor");
        house1.setColors(List.of(House.HouseColors.RED, House.HouseColors.GOLD));
        houseRepository.save(house1);

        House house2 = new House();
        house2.setName(House.HouseName.HUFFLEPUFF);
        house2.setFounder("Helga Hufflepuff");
        house2.setColors(List.of(House.HouseColors.YELLOW, House.HouseColors.BLACK));
        houseRepository.save(house2);

        House house3 = new House();
        house3.setName(House.HouseName.RAVENCLAW);
        house3.setFounder("Rowena Ravenclaw");
        house3.setColors(List.of(House.HouseColors.BLUE, House.HouseColors.SILVER));
        houseRepository.save(house3);

        House house4 = new House();
        house4.setName(House.HouseName.SLYTHERIN);
        house4.setFounder("Salazar Slytherin");
        house4.setColors(List.of(House.HouseColors.GREEN, House.HouseColors.SILVER));
        houseRepository.save(house4);

        //INIT Students
        Student student1 = new Student();
        student1.setFirstName("Harry");
        student1.setMiddleName("James");
        student1.setLastName("Potter");
        student1.setDateOfBirth(LocalDate.of(1980, 7, 31));
        student1.setHouse(house1);
        student1.setPrefect(true);
        student1.setEnrollmentYear(1991);
        student1.setGraduationYear(1997);
        student1.setGraduated(true);

        studentRepository.save(student1);

        Student student2 = new Student();
        student2.setFirstName("Cedric");
        student2.setLastName("Diggory");
        student2.setDateOfBirth(LocalDate.of(1979, 10, 1));
        student2.setHouse(house2);
        student2.setPrefect(true);
        student2.setEnrollmentYear(1991);
        student2.setGraduationYear(1997);
        student2.setGraduated(true);

        studentRepository.save(student2);

        Student student3 = new Student();
        student3.setFirstName("Luna");
        student3.setLastName("Lovegood");
        student3.setDateOfBirth(LocalDate.of(1981, 2, 13));
        student3.setHouse(house3);
        student3.setPrefect(true);
        student3.setEnrollmentYear(1991);
        student3.setGraduationYear(1997);
        student3.setGraduated(true);

        studentRepository.save(student3);

        Student student4 = new Student();
        student4.setFirstName("Draco");
        student4.setMiddleName("Lucius");
        student4.setLastName("Malfoy");
        student4.setDateOfBirth(LocalDate.of(1980, 6, 5)); // June 5, 1980
        student4.setHouse(house4);
        student4.setPrefect(true);
        student4.setEnrollmentYear(1991);
        student4.setGraduationYear(1997);
        student4.setGraduated(true);

        studentRepository.save(student4);

        //INIT Teachers
        Teacher teacher1 = new Teacher();
        teacher1.setFirstName("Minerva");
        teacher1.setMiddleName("McGonagall");
        teacher1.setLastName("McGonagall");
        teacher1.setDateOfBirth(LocalDate.of(1935, 10, 4));
        teacher1.setHouse("Gryffindor");
        teacher1.setHeadOfHouse(true);
        teacher1.setEmployment(EmpType.TENURED);
        teacher1.setEmploymentStart(LocalDate.of(1956, 9, 1));

        teacherRepository.save(teacher1);

        Teacher teacher2 = new Teacher();
        teacher2.setFirstName("Sybill");
        teacher2.setMiddleName("Patricia");
        teacher2.setLastName("Trelawney");
        teacher2.setDateOfBirth(LocalDate.of(1963, 3, 9));
        teacher2.setHouse("Ravenclaw");
        teacher2.setHeadOfHouse(false);
        teacher2.setEmployment(EmpType.TEMPORARY);
        teacher2.setEmploymentStart(LocalDate.of(1995, 1, 15));
        teacher2.setEmploymentEnd(LocalDate.of(1996, 12, 31));

        teacherRepository.save(teacher2);

        Teacher teacher3 = new Teacher();
        teacher3.setFirstName("Pomona");
        teacher3.setMiddleName("Sprout");
        teacher3.setLastName("Sprout");
        teacher3.setDateOfBirth(LocalDate.of(1941, 5, 15));
        teacher3.setHouse("Hufflepuff");
        teacher3.setHeadOfHouse(true);
        teacher3.setEmployment(EmpType.DISCHARGED);
        teacher3.setEmploymentStart(LocalDate.of(1969, 9, 1));
        teacher3.setEmploymentEnd(LocalDate.of(1997, 6, 30));

        teacherRepository.save(teacher3);

        Teacher teacher4 = new Teacher();
        teacher4.setFirstName("Severus");
        teacher4.setLastName("Snape");
        teacher4.setDateOfBirth(LocalDate.of(1960, 1, 9));
        teacher4.setHouse("Slytherin");
        teacher4.setHeadOfHouse(false);
        teacher4.setEmployment(EmpType.PROBATION);
        teacher4.setEmploymentStart(LocalDate.of(1996, 9, 1));
        teacher4.setEmploymentEnd(LocalDate.of(1997, 6, 30)); // Probationary period ended

        teacherRepository.save(teacher4);

        // INIT Courses
        Course emptyCourse = new Course();
        emptyCourse.setSubject("Empty Course");
        emptyCourse.setSchoolYear(2024);
        emptyCourse.setCurrent(true);
        courseRepository.save(emptyCourse);

        // INIT Populated Course
        Course populatedCourse = new Course();
        populatedCourse.setSubject("Populated Course");
        populatedCourse.setSchoolYear(2024);
        populatedCourse.setCurrent(true);
        populatedCourse.setTeacher(teacher1);
        populatedCourse.setStudents(List.of(student1, student2));
        courseRepository.save(populatedCourse);
    }
}
