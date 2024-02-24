package edu.hogwarts.studentadmin.api.controllers;

import edu.hogwarts.studentadmin.api.dto.students.*;
import edu.hogwarts.studentadmin.models.Student;
import edu.hogwarts.studentadmin.repositories.StudentRepository;
import edu.hogwarts.studentadmin.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;
    private final StudentService studentService;

    public StudentController(StudentRepository studentRepository,
                             StudentService studentService) {
        this.studentRepository = studentRepository;
        this.studentService = studentService;
    }

    // TODO: map, stream for findAll etc on studentDTO instead of student -> ryk det i service!

    @GetMapping
    public List<StudentResponseDTO> getAllStudents() {
        return studentService.getAllStudents();
    }
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getById(@PathVariable int id) {
        return studentService.getStudentById(id);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody StudentRequestDTO studentRequestDTO) {
        return studentService.createStudent(studentRequestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody StudentRequestDTO updatedStudentRequestDTO) {
        return studentService.updateStudent(id, updatedStudentRequestDTO);
    }

    @PatchMapping ("/{id}")
    public ResponseEntity<Student> patchStudent(@PathVariable int id, @RequestBody StudentRequestDTO patchStudentRequestDTO) {
        return studentService.patchStudent(id, patchStudentRequestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable int id) {
        return studentService.deleteStudent(id);
    }
}
