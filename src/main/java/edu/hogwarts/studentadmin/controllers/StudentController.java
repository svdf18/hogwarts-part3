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

    // doesnt work with new house_id.. fix later
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        House house = houseRepository.findByName(student.getHouse().getName()).orElse(null);

        if (house != null) {
            student.setHouse(house);
            Student createdStudent = studentRepository.save(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // doesnt work with new house_id.. fix later (nest house as an object in query and work from there)
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student updatedStudent) {
        try {
            Optional<Student> existingStudentOptional = studentRepository.findById(id);

            if (existingStudentOptional.isPresent()) {
                Student existingStudent = existingStudentOptional.get();
                existingStudent.updateFrom(updatedStudent);
                studentRepository.save(existingStudent);
                return ResponseEntity.ok(existingStudent);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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
