package edu.hogwarts.studentadmin.api.controllers;

import edu.hogwarts.studentadmin.api.dto.teachers.TeacherRequestDTO;
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

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<TeacherResponseDTO> getAllTeachers() {
        return teacherService.getAllTeachers();
    }
    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> getTeacherById(@PathVariable int id) {
        return teacherService.getTeacherById(id);
    }

    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody TeacherRequestDTO teacherRequestDTO) {
        return teacherService.createTeacher(teacherRequestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> updateTeacher(@PathVariable int id, @RequestBody TeacherRequestDTO updatedTeacherRequestDTO) {
        return teacherService.updateTeacher(id, updatedTeacherRequestDTO);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> patchTeacher(@PathVariable int id, @RequestBody TeacherRequestDTO patchTeacherRequestDTO) {
        return teacherService.patchTeacher(id, patchTeacherRequestDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Teacher> deleteTeacher(@PathVariable int id) {
        return teacherService.deleteTeacher(id);
    }
}

