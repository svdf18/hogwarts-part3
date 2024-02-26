package edu.hogwarts.studentadmin.service;

import edu.hogwarts.studentadmin.api.dto.teachers.TeacherRequestDTO;
import edu.hogwarts.studentadmin.api.dto.teachers.TeacherResponseDTO;
import edu.hogwarts.studentadmin.models.EmpType;
import edu.hogwarts.studentadmin.models.House;
import edu.hogwarts.studentadmin.models.Teacher;
import edu.hogwarts.studentadmin.repositories.HouseRepository;
import edu.hogwarts.studentadmin.repositories.TeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final HouseRepository houseRepository;

    public TeacherService(TeacherRepository teacherRepository,
                          HouseRepository houseRepository) {
        this.teacherRepository = teacherRepository;
        this.houseRepository = houseRepository;
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

    public ResponseEntity<Teacher> createTeacher(TeacherRequestDTO teacherRequestDTO) {
        House house = houseRepository.findByName(teacherRequestDTO.getHouse()).orElse(null);
        if (house != null) {
            Teacher teacher = convertTeacherRequestDTOToEntity(teacherRequestDTO, house);
            Teacher createdTeacher = teacherRepository.save(teacher);
            return ResponseEntity.of(Optional.of(createdTeacher));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<TeacherResponseDTO> updateTeacher(int id, TeacherRequestDTO updatedTeacherRequestDTO) {
        Optional<Teacher> existingTeacherOptional = teacherRepository.findById(id);

        if (existingTeacherOptional.isPresent()) {
            Teacher existingTeacher = existingTeacherOptional.get();

            House updatedHouse = houseRepository.findByName(updatedTeacherRequestDTO.getHouse()).orElse(null);
            existingTeacher.setHouse(updatedHouse);

            Teacher updatedTeacher = convertTeacherRequestDTOToEntity(updatedTeacherRequestDTO, updatedHouse);
            // Save the entity
            updatedTeacher.setId(existingTeacher.getId()); // Set the ID to update the existing entity
            updatedTeacher = teacherRepository.save(updatedTeacher);

            TeacherResponseDTO responseDTO = convertTeacherToResponseDTO(updatedTeacher);

            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<TeacherResponseDTO> patchTeacher(int id, TeacherRequestDTO patchTeacherRequestDTO) {
        Optional<Teacher> existingTeacherOptional = teacherRepository.findById(id);

        if (existingTeacherOptional.isPresent()) {
            Teacher existingTeacher = existingTeacherOptional.get();
            existingTeacher.setHeadOfHouse(patchTeacherRequestDTO.isHeadOfHouse());

            EmpType employment = EmpType.valueOf(patchTeacherRequestDTO.getEmployment());
            existingTeacher.setEmployment(employment);

            existingTeacher.setEmploymentEnd(patchTeacherRequestDTO.getEmploymentEnd());


            teacherRepository.save(existingTeacher);
            TeacherResponseDTO responseDTO = convertTeacherToResponseDTO(existingTeacher);

            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Teacher> deleteTeacher(int id) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(id);

        if (teacherOptional.isPresent()) {
            Teacher teacher = teacherOptional.get();
            teacherRepository.delete(teacher);
            return ResponseEntity.ok(teacher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Convert from RequestDTO to Entity
    public Teacher convertTeacherRequestDTOToEntity(TeacherRequestDTO teacherRequestDTO, House house) {
        Teacher teacher = new Teacher();
        teacher.setFirstName(teacherRequestDTO.getFirstName());
        teacher.setMiddleName(teacherRequestDTO.getMiddleName());
        teacher.setLastName(teacherRequestDTO.getLastName());
        teacher.setDateOfBirth(teacherRequestDTO.getDateOfBirth());
        teacher.setHouse(house);
        teacher.setHeadOfHouse(teacherRequestDTO.isHeadOfHouse());
        teacher.setEmployment(EmpType.valueOf(teacherRequestDTO.getEmployment()));
        teacher.setEmploymentStart(teacherRequestDTO.getEmploymentStart());
        teacher.setEmploymentEnd(teacherRequestDTO.getEmploymentEnd());

        return teacher;
    }

    // Convert Entity to ResponseDTO
    public TeacherResponseDTO convertTeacherToResponseDTO(Teacher teacher) {
        TeacherResponseDTO responseDTO = new TeacherResponseDTO();
        responseDTO.setId(teacher.getId());
        responseDTO.setFirstName(teacher.getFirstName());
        responseDTO.setMiddleName(teacher.getMiddleName());
        responseDTO.setLastName(teacher.getLastName());
        responseDTO.setDateOfBirth(teacher.getDateOfBirth());
        responseDTO.setHouse(teacher.getHouse().getName());
        responseDTO.setHeadOfHouse(teacher.isHeadOfHouse());
        responseDTO.setEmployment(teacher.getEmployment().name());
        responseDTO.setEmploymentStart(teacher.getEmploymentStart());
        responseDTO.setEmploymentEnd(teacher.getEmploymentEnd());

        return responseDTO;
    }
}


