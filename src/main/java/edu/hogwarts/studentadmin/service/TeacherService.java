package edu.hogwarts.studentadmin.service;

import edu.hogwarts.studentadmin.api.dto.teachers.TeacherRequestDTO;
import edu.hogwarts.studentadmin.api.dto.teachers.TeacherResponseDTO;
import edu.hogwarts.studentadmin.models.EmpType;
import edu.hogwarts.studentadmin.models.Teacher;
import edu.hogwarts.studentadmin.repositories.TeacherRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<TeacherResponseDTO> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(this::convertTeacherToResponseDTO)
                .collect(Collectors.toList());
    }

    public ResponseEntity<TeacherResponseDTO> getTeacherById(int id) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(id);

        if (teacherOptional.isPresent()) {
            Teacher teacher = teacherOptional.get();
            TeacherResponseDTO responseDTO = convertTeacherToResponseDTO(teacher);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    // Convert from RequestDTO to Entity
    public Teacher convertTeacherRequestDTOToEntity(TeacherRequestDTO teacherRequestDTO) {
        Teacher teacher = new Teacher();
        teacher.setFirstName(teacherRequestDTO.getFirstName());
        teacher.setMiddleName(teacherRequestDTO.getMiddleName());
        teacher.setLastName(teacherRequestDTO.getLastName());
        teacher.setDateOfBirth(teacherRequestDTO.getDateOfBirth());
        teacher.setHouse(teacherRequestDTO.getHouse());
        teacher.setHeadOfHouse(teacherRequestDTO.isHeadOfHouse());
        teacher.setEmployment(EmpType.valueOf(teacherRequestDTO.getEmployment()));
        teacher.setEmploymentStart(teacherRequestDTO.getEmploymentStart());
        teacher.setEmploymentEnd(teacherRequestDTO.getEmploymentEnd());

        return teacher;
    }

    // Convert Entity to ResponseDTO
    private TeacherResponseDTO convertTeacherToResponseDTO(Teacher teacher) {
        TeacherResponseDTO responseDTO = new TeacherResponseDTO();
        responseDTO.setId(teacher.getId());
        responseDTO.setFirstName(teacher.getFirstName());
        responseDTO.setMiddleName(teacher.getMiddleName());
        responseDTO.setLastName(teacher.getLastName());
        responseDTO.setDateOfBirth(teacher.getDateOfBirth());
        responseDTO.setHouse(teacher.getHouse());
        responseDTO.setHeadOfHouse(teacher.isHeadOfHouse());
        responseDTO.setEmployment(teacher.getEmployment().name());
        responseDTO.setEmploymentStart(teacher.getEmploymentStart());
        responseDTO.setEmploymentEnd(teacher.getEmploymentEnd());

        return responseDTO;
    }
}
