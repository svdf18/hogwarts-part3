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

    @PatchMapping ("/{id}/updatePrefect")
    public ResponseEntity<Student> updatePrefect(@PathVariable int id, @RequestBody UpdatePrefectDTO updatePrefectDTO){
        try {
            Optional<Student> existingStudentOptional = studentRepository.findById(id);

            if (existingStudentOptional.isPresent()) {
                Student existingStudent = existingStudentOptional.get();
                existingStudent.setPrefect(updatePrefectDTO.isPrefect());

                studentRepository.save(existingStudent);
                return ResponseEntity.ok(existingStudent);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    };

    @PatchMapping("/{id}/updateSchoolYear")
    public ResponseEntity<Student> updateSchoolYear(@PathVariable int id, @RequestBody UpdateSchoolYearDTO updateSchoolYearDTO){
        try {
            Optional<Student> existingStudentOptional = studentRepository.findById(id);

            if(existingStudentOptional.isPresent()) {
                Student existingStudent = existingStudentOptional.get();
                existingStudent.setSchoolYear(updateSchoolYearDTO.getSchoolYear());

                studentRepository.save(existingStudent);
                return ResponseEntity.ok(existingStudent);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    };

    @PatchMapping("/{id}/updateGraduation")
    public ResponseEntity<Student> updateGraduation(@PathVariable int id, @RequestBody UpdateGraduationDTO updateGraduationDTO) {
        try {
            Optional<Student> existingStudentOptional = studentRepository.findById(id);

            if (existingStudentOptional.isPresent()) {
                Student existingStudent = existingStudentOptional.get();

                existingStudent.setGraduationYear(updateGraduationDTO.getGraduationYear());

                existingStudent.setGraduated(Objects.nonNull(updateGraduationDTO.getGraduationYear()));

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
        return studentService.deleteStudent(id);
    }
}
