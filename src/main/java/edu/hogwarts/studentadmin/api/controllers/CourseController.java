package edu.hogwarts.studentadmin.api.controllers;

import edu.hogwarts.studentadmin.api.dto.courses.CourseResponseDTO;
import edu.hogwarts.studentadmin.api.dto.students.StudentResponseDTO;
import edu.hogwarts.studentadmin.api.dto.teachers.TeacherResponseDTO;
import edu.hogwarts.studentadmin.models.Course;
import edu.hogwarts.studentadmin.models.Student;
import edu.hogwarts.studentadmin.models.Teacher;
import edu.hogwarts.studentadmin.repositories.CourseRepository;
import edu.hogwarts.studentadmin.repositories.StudentRepository;
import edu.hogwarts.studentadmin.repositories.TeacherRepository;
import edu.hogwarts.studentadmin.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    private final CourseService courseService;

    public CourseController(CourseRepository courseRepository,
                            TeacherRepository teacherRepository,
                            StudentRepository studentRepository,
                            CourseService courseService){
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.courseService = courseService;
    }

    // get all courses
    @GetMapping
    public List<CourseResponseDTO> getAllCourses(){
        return courseService.getAllCourses();
    }

    // get course by id
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getById(@PathVariable int id){
        return courseService.getCourseById(id);
    }

    // get teacher for a course
    @GetMapping("/{id}/teacher")
    public ResponseEntity<TeacherResponseDTO> getTeacherByCourseId(@PathVariable int id) {
        return courseService.getTeacherByCourseId(id);
    }

    // get students for a course
    @GetMapping("{id}/students")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByCourseId(@PathVariable int id) {
        return courseService.getStudentsByCourseId(id);
    }

    // post new course
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course newCourse) {
        Course savedCourse = courseRepository.save(newCourse);
        return ResponseEntity.ok(savedCourse);
    }

    // update course details
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable int id, @RequestBody Course updatedCourse) {
        Optional<Course> existingCourseOptional = courseRepository.findById(id);

        if (existingCourseOptional.isPresent()) {
            Course existingCourse = existingCourseOptional.get();
            existingCourse.setSubject(updatedCourse.getSubject());
            existingCourse.setSchoolYear(updatedCourse.getSchoolYear());
            existingCourse.setCurrent(updatedCourse.isCurrent());

            Course savedCourse = courseRepository.save(existingCourse);
            return ResponseEntity.ok(savedCourse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // update teacher for a course (only works w. existing teachers - but u only need id!)
    @PutMapping("/{id}/teacher")
    public ResponseEntity<Course> setTeacherForCourse(@PathVariable int id, @RequestBody Teacher newTeacher) {
        Optional<Course> courseOptional = courseRepository.findById(id);

        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();

            Optional<Teacher> existingTeacherOptional = teacherRepository.findById(newTeacher.getId());

            if (existingTeacherOptional.isPresent()) {
                Teacher existingTeacher = existingTeacherOptional.get();

                course.setTeacher(existingTeacher);

                Course savedCourse = courseRepository.save(course);

                return ResponseEntity.ok(savedCourse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // update students for a course (only id, but you need all previous id's as well ...)
    @PutMapping("/{id}/students")
    public ResponseEntity<Course> setStudentsForCourse(@PathVariable int id, @RequestBody List<Student> newStudents) {
        Optional<Course> courseOptional = courseRepository.findById(id);

        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();

            List<Student> existingStudents = studentRepository.findAllById(
                    newStudents.stream().map(Student::getId).collect(Collectors.toList())
            );
                course.setStudents(existingStudents);

                Course savedCourse = courseRepository.save(course);

                return ResponseEntity.ok(savedCourse);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

    // delete course (you have to press a couple of times in postman ¯\_(ツ)_/¯
    @DeleteMapping("/{id}")
    public ResponseEntity<Course> deleteCourse(@PathVariable int id) {
        Optional<Course> courseOptional = courseRepository.findById(id);

        if (courseOptional.isPresent()) {
            Course courseToDelete = courseOptional.get();

            Course deletedCourse = new Course();
            deletedCourse.setId(courseToDelete.getId());
            deletedCourse.setSubject(courseToDelete.getSubject());
            deletedCourse.setSchoolYear(courseToDelete.getSchoolYear());
            deletedCourse.setCurrent(courseToDelete.isCurrent());
            deletedCourse.setTeacher(courseToDelete.getTeacher());
            deletedCourse.setStudents(courseToDelete.getStudents());

            // Delete the course
            courseRepository.delete(courseToDelete);

            return ResponseEntity.ok(deletedCourse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // delete teacher from course
    @DeleteMapping("/{id}/teacher")
    public ResponseEntity<Course> removeTeacherFromCourse(@PathVariable int id) {
        Optional<Course> courseOptional = courseRepository.findById(id);

        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();

            if (course.getTeacher() != null) {

                Teacher removedTeacher = course.getTeacher();

                course.setTeacher(null);

                Course savedCourse = courseRepository.save(course);

                return ResponseEntity.ok(savedCourse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // delete students from course
    @DeleteMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<Course> removeStudentFromCourse(
            @PathVariable int courseId,
            @PathVariable int studentId) {

        Optional<Course> courseOptional = courseRepository.findById(courseId);

        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();

            List<Student> students = course.getStudents();

            Optional<Student> studentToRemove = students.stream()
                    .filter(student -> student.getId() == studentId)
                    .findFirst();

            if (studentToRemove.isPresent()) {
                Student removedStudent = studentToRemove.get();

                students.remove(removedStudent);

                Course savedCourse = courseRepository.save(course);

                return ResponseEntity.ok(savedCourse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

