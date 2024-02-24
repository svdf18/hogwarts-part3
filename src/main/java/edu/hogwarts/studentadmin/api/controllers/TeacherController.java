package edu.hogwarts.studentadmin.api.controllers;

import edu.hogwarts.studentadmin.api.dto.teachers.TeacherResponseDTO;
import edu.hogwarts.studentadmin.models.Teacher;
import edu.hogwarts.studentadmin.repositories.TeacherRepository;
import edu.hogwarts.studentadmin.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;
    private final TeacherRepository teacherRepository;

    public TeacherController(TeacherService teacherService,
            TeacherRepository teacherRepository) {
        this.teacherService = teacherService;
        this.teacherRepository = teacherRepository;
    }

    @GetMapping
    public List<TeacherResponseDTO> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> getTeacherById(@PathVariable int id) {
        return teacherService.getTeacherById(id);
    }

    @GetMapping
    public List<Teacher> getAll() {
        return teacherRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getById(@PathVariable int id) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(id);
        return teacherOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        Teacher createdTeacher = teacherRepository.save(teacher);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeacher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable int id, @RequestBody Teacher updatedTeacher) {
        Optional<Teacher> existingTeacherOptional = teacherRepository.findById(id);

        if (existingTeacherOptional.isPresent()) {
            Teacher existingTeacher = existingTeacherOptional.get();
            existingTeacher.setFirstName(updatedTeacher.getFirstName());
            existingTeacher.setMiddleName(updatedTeacher.getMiddleName());
            existingTeacher.setLastName(updatedTeacher.getLastName());
            existingTeacher.setDateOfBirth(updatedTeacher.getDateOfBirth());
            existingTeacher.setHouse(updatedTeacher.getHouse());
            existingTeacher.setHeadOfHouse(updatedTeacher.isHeadOfHouse());
            existingTeacher.setEmployment(updatedTeacher.getEmployment());
            existingTeacher.setEmploymentStart(updatedTeacher.getEmploymentStart());
            existingTeacher.setEmploymentEnd(updatedTeacher.getEmploymentEnd());

            teacherRepository.save(existingTeacher);
            return ResponseEntity.ok(existingTeacher);
        } else {
            updatedTeacher.setId(id);
            Teacher newTeacher = teacherRepository.save(updatedTeacher);
            return ResponseEntity.status(HttpStatus.CREATED).body(newTeacher);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Teacher> deleteTeacher(@PathVariable int id) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(id);

        if (teacherOptional.isPresent()) {
            Teacher teacher = teacherOptional.get();
            teacherRepository.delete(teacher);
            return ResponseEntity.ok(teacher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

