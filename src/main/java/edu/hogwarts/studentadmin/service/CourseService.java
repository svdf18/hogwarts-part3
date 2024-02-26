package edu.hogwarts.studentadmin.service;

import edu.hogwarts.studentadmin.api.dto.courses.CourseResponseDTO;
import edu.hogwarts.studentadmin.api.dto.students.StudentResponseDTO;
import edu.hogwarts.studentadmin.api.dto.teachers.TeacherResponseDTO;
import edu.hogwarts.studentadmin.models.Course;
import edu.hogwarts.studentadmin.models.Student;
import edu.hogwarts.studentadmin.repositories.CourseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {


    private final CourseRepository courseRepository;
    private final StudentService studentService;
    private final TeacherService teacherService;


    public CourseService(CourseRepository courseRepository, StudentService studentService, TeacherService teacherService) {
        this.courseRepository = courseRepository;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    public List<CourseResponseDTO> getAllCourses() {
        return courseRepository.findAll().stream().map(this::convertCourseToResponseDTO).collect(Collectors.toList());
    }

    public ResponseEntity<CourseResponseDTO> getCourseById(int id) {
        Optional<Course> courseOptional = courseRepository.findById(id);

        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            CourseResponseDTO responseDTO = convertCourseToResponseDTO(course);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<TeacherResponseDTO> getTeacherByCourseId(int id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        return courseOptional.map(course -> {
            TeacherResponseDTO teacherDTO = teacherService.convertTeacherToResponseDTO(course.getTeacher());
            return ResponseEntity.ok(teacherDTO);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<List<StudentResponseDTO>> getStudentsByCourseId(int id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        return courseOptional.map(course -> {
            List<StudentResponseDTO> studentDTOs = studentService.convertStudentsToResponseDTOList(course.getStudents());
            return ResponseEntity.ok(studentDTOs);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private CourseResponseDTO convertCourseToResponseDTO(Course course) {
        CourseResponseDTO responseDTO = new CourseResponseDTO();
        responseDTO.setId(course.getId());
        responseDTO.setSubject(course.getSubject());
        responseDTO.setSchoolYear(course.getSchoolYear());
        responseDTO.setCurrent(course.isCurrent());
        responseDTO.setTeacherId(course.getTeacher() != null ? course.getTeacher().getId() : null);
        responseDTO.setStudentId(course.getStudents().stream().map(Student::getId).collect(Collectors.toList()));

        return responseDTO;
    }
}
