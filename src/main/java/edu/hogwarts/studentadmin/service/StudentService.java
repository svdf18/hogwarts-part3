package edu.hogwarts.studentadmin.service;

import edu.hogwarts.studentadmin.api.dto.students.StudentRequestDTO;
import edu.hogwarts.studentadmin.api.dto.students.StudentResponseDTO;
import edu.hogwarts.studentadmin.models.House;
import edu.hogwarts.studentadmin.models.Student;
import edu.hogwarts.studentadmin.repositories.HouseRepository;
import edu.hogwarts.studentadmin.repositories.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final HouseRepository houseRepository;

    public StudentService(StudentRepository studentRepository, HouseRepository houseRepository) {
        this.studentRepository = studentRepository;
        this.houseRepository = houseRepository;
    }

    public List<StudentResponseDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertStudentToResponseDTO)
                .collect(Collectors.toList());
    }

    public ResponseEntity<StudentResponseDTO> getStudentById(int id) {
        Optional<Student> studentOptional = studentRepository.findById(id);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            StudentResponseDTO responseDTO = convertStudentToResponseDTO(student);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Student> createStudent(StudentRequestDTO studentRequestDTO) {
        if (studentRequestDTO.getFullName() != null) {
            // Creating student with full name and house
            String[] nameParts = extractNameParts(studentRequestDTO.getFullName());

            Student student = new Student();
            student.setFirstName(nameParts[0]);
            student.setMiddleName(nameParts[1]);
            student.setLastName(nameParts[2]);
            student.setHouse(houseRepository.findByName(studentRequestDTO.getHouse()).orElse(null));

            Student createdStudent = studentRepository.save(student);
            return ResponseEntity.of(Optional.of(createdStudent));
        } else {
            // Creating student with detailed fields
            House house = houseRepository.findByName(studentRequestDTO.getHouse()).orElse(null);

            if (house != null) {
                Student student = convertStudentRequestDTOToEntity(studentRequestDTO, house);
                Student createdStudent = studentRepository.save(student);
                return ResponseEntity.of(Optional.of(createdStudent));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
    }
    public ResponseEntity<Student> updateStudent(int id, StudentRequestDTO updatedStudentRequestDTO) {
        Optional<Student> existingStudentOptional = studentRepository.findById(id);

        if (existingStudentOptional.isPresent()) {
            Student existingStudent = existingStudentOptional.get();

            House updatedHouse = houseRepository.findByName(updatedStudentRequestDTO.getHouse()).orElse(null);
            existingStudent.setHouse(updatedHouse);

            Student updatedStudent = convertStudentRequestDTOToEntity(updatedStudentRequestDTO, updatedHouse);
            // Save the entity
            updatedStudent.setId(existingStudent.getId()); // Set the ID to update the existing entity
            updatedStudent = studentRepository.save(updatedStudent);

            return ResponseEntity.of(Optional.of(updatedStudent));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Student> patchStudent(int id, StudentRequestDTO patchStudentRequestDTO) {
        Optional<Student> existingStudentOptional = studentRepository.findById(id);

        if (existingStudentOptional.isPresent()) {
            Student existingStudent = existingStudentOptional.get();
            existingStudent.setPrefect(patchStudentRequestDTO.isPrefect());
            existingStudent.setSchoolYear(patchStudentRequestDTO.getSchoolYear());
            updateGraduationFields(existingStudent, patchStudentRequestDTO.getGraduationYear());

            studentRepository.save(existingStudent);
            return ResponseEntity.ok(existingStudent);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    public ResponseEntity<Student> deleteStudent(int id) {
        Optional<Student> studentOptional = studentRepository.findById(id);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            studentRepository.delete(student);
            return ResponseEntity.of(Optional.of(student));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Convert from RequestDTO to Entity
    public Student convertStudentRequestDTOToEntity(StudentRequestDTO studentRequestDTO, House house) {
        Student student = new Student();
        student.setFirstName(studentRequestDTO.getFirstName());
        student.setMiddleName(studentRequestDTO.getMiddleName());
        student.setLastName(studentRequestDTO.getLastName());
        student.setHouse(house);
        student.setDateOfBirth(studentRequestDTO.getDateOfBirth());
        student.setPrefect(studentRequestDTO.isPrefect());
        student.setEnrollmentYear(studentRequestDTO.getEnrollmentYear());
        student.setGraduationYear(studentRequestDTO.getGraduationYear());
        student.setGraduated(studentRequestDTO.isGraduated());
        student.setSchoolYear(studentRequestDTO.getSchoolYear());

        return student;
    }

    // Convert a Student Entity to StudentResponseDTO
    public StudentResponseDTO convertStudentToResponseDTO(Student student) {
        StudentResponseDTO responseDTO = new StudentResponseDTO();
        responseDTO.setId(student.getId());
        responseDTO.setFirstName(student.getFirstName());
        responseDTO.setMiddleName(student.getMiddleName());
        responseDTO.setLastName(student.getLastName());
        responseDTO.setDateOfBirth(student.getDateOfBirth());
        responseDTO.setHouse(student.getHouse().getName());
        responseDTO.setPrefect(student.isPrefect());
        responseDTO.setEnrollmentYear(student.getEnrollmentYear());
        responseDTO.setGraduationYear(student.getGraduationYear());
        responseDTO.setGraduated(student.isGraduated());
        responseDTO.setSchoolYear(student.getSchoolYear());

        return responseDTO;
    }

    // Convert a List of Student entities to a List of StudentResponseDTOs
    public List<StudentResponseDTO> convertStudentsToResponseDTOList(List<Student> students) {
        return students.stream()
                .map(this::convertStudentToResponseDTO)
                .collect(Collectors.toList());
    }

    // Helper method to extract first, middle, and last names
    private String[] extractNameParts(String fullName) {
        String[] nameParts = new String[3];

        int firstSpaceIndex = fullName.indexOf(" ");
        int lastSpaceIndex = fullName.lastIndexOf(" ");

        nameParts[0] = fullName.substring(0, firstSpaceIndex);

        if (firstSpaceIndex != lastSpaceIndex) {
            nameParts[1] = fullName.substring(firstSpaceIndex + 1, lastSpaceIndex);
            nameParts[2] = fullName.substring(lastSpaceIndex + 1);
        } else {
            nameParts[1] = null;
            nameParts[2] = fullName.substring(firstSpaceIndex + 1);
        }

        return nameParts;
    }

    // Helper method to set graduated based on graduationYear
    private void updateGraduationFields(Student student, Integer graduationYear) {
        if (graduationYear != null) {
            student.setGraduationYear(graduationYear);
            student.setGraduated(graduationYear != 0);
        }
    }
}
