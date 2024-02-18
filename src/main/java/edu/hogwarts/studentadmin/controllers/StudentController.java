package edu.hogwarts.studentadmin.controllers;

import edu.hogwarts.studentadmin.models.House;
import edu.hogwarts.studentadmin.models.Student;
import edu.hogwarts.studentadmin.repositories.HouseRepository;
import edu.hogwarts.studentadmin.repositories.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    private StudentRepository studentRepository;
    private HouseRepository houseRepository;

    public StudentController(StudentRepository studentRepository, HouseRepository houseRepository) {
        this.studentRepository = studentRepository;
        this.houseRepository = houseRepository;
    }

    @GetMapping
    public List<Student> getAll(){
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable int id){
        Optional<Student> studentOptional = studentRepository.findById(id);
        return studentOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/house/{id}")
    public ResponseEntity<List<Student>> getStudentsByHouse(@PathVariable int id) {
        Optional<House> houseOptional = houseRepository.findById(id);

        if (houseOptional.isPresent()) {
            House house = houseOptional.get();
            List<Student> students = house.getStudents();
            return ResponseEntity.ok(students);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student, @RequestParam(name = "house_id") int houseId) {
        House house = houseRepository.findById(houseId).orElse(null);

        if (house != null) {
            student.setHouse(house);
            Student createdStudent = studentRepository.save(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student updatedStudent) {
        Optional<Student> existingStudentOptional = studentRepository.findById(id);

        if (existingStudentOptional.isPresent()) {
            Student existingStudent = existingStudentOptional.get();
            existingStudent.setFirstName(updatedStudent.getFirstName());
            existingStudent.setMiddleName(updatedStudent.getMiddleName());
            existingStudent.setLastName(updatedStudent.getLastName());
            existingStudent.setDateOfBirth(updatedStudent.getDateOfBirth());
            existingStudent.setHouse(updatedStudent.getHouse());
            existingStudent.setPrefect(updatedStudent.isPrefect());
            existingStudent.setEnrollmentYear(updatedStudent.getEnrollmentYear());
            existingStudent.setGraduationYear(updatedStudent.getGraduationYear());
            existingStudent.setGraduated(updatedStudent.isGraduated());

            studentRepository.save(existingStudent);
            return ResponseEntity.ok(existingStudent);
        } else {
            updatedStudent.setId(id);
            Student newStudent = studentRepository.save(updatedStudent);
            return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable int id) {
        Optional<Student> studentOptional = studentRepository.findById(id);

        if (studentOptional.isPresent()){
            Student student = studentOptional.get();
            studentRepository.delete(student);
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
