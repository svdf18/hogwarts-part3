package edu.hogwarts.studentadmin.api.controllers;

import edu.hogwarts.studentadmin.api.dto.students.*;
import edu.hogwarts.studentadmin.models.House;
import edu.hogwarts.studentadmin.models.Student;
import edu.hogwarts.studentadmin.repositories.HouseRepository;
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

    private StudentRepository studentRepository;
    private HouseRepository houseRepository;
    private StudentService studentService;

    public StudentController(StudentRepository studentRepository,
                             HouseRepository houseRepository,
                             StudentService studentService) {
        this.studentRepository = studentRepository;
        this.houseRepository = houseRepository;
        this.studentService = studentService;
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

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody StudentDTO studentDTO) {
        House house = houseRepository.findByName(studentDTO.getHouse()).orElse(null);

        if (house != null) {
            // Convert DTO to entity
            Student student = new Student(
                    studentDTO.getFirstName(),
                    studentDTO.getMiddleName(),
                    studentDTO.getLastName(),
                    house,
                    studentDTO.getEnrollmentYear(),
                    studentDTO.getSchoolYear(),
                    studentDTO.getGraduationYear(),
                    studentDTO.isGraduated()
            );

            student.setPrefect(studentDTO.isPrefect());
            student.setDateOfBirth(studentDTO.getDateOfBirth());
            // Save the entity
            Student createdStudent = studentRepository.save(student);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/createWithFullName")
    public ResponseEntity<Student> createStudentWithFullName(@RequestBody StudentFullNameDTO studentFullNameDTO) {
        Student createdStudent = studentService.createStudentWithFullName(studentFullNameDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody StudentDTO updatedStudentDTO) {
        try {
            Optional<Student> existingStudentOptional = studentRepository.findById(id);

            if (existingStudentOptional.isPresent()) {
                Student existingStudent = existingStudentOptional.get();

                // Update fields from DTO
                existingStudent.setFirstName(updatedStudentDTO.getFirstName());
                existingStudent.setMiddleName(updatedStudentDTO.getMiddleName());
                existingStudent.setLastName(updatedStudentDTO.getLastName());
                existingStudent.setDateOfBirth(updatedStudentDTO.getDateOfBirth());
                existingStudent.setHouse(houseRepository.findByName(updatedStudentDTO.getHouse()).orElse(null));
                existingStudent.setPrefect(updatedStudentDTO.isPrefect());
                existingStudent.setEnrollmentYear(updatedStudentDTO.getEnrollmentYear());
                existingStudent.setGraduationYear(updatedStudentDTO.getGraduationYear());
                existingStudent.setGraduated(updatedStudentDTO.isGraduated());

                studentRepository.save(existingStudent);
                return ResponseEntity.ok(existingStudent);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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
