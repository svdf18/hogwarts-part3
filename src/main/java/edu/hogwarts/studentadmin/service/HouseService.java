package edu.hogwarts.studentadmin.service;

import edu.hogwarts.studentadmin.api.dto.houses.HouseResponseDTO;
import edu.hogwarts.studentadmin.api.dto.students.StudentResponseDTO;
import edu.hogwarts.studentadmin.models.House;
import edu.hogwarts.studentadmin.models.Student;

import edu.hogwarts.studentadmin.repositories.HouseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HouseService {

    private final HouseRepository houseRepository;
    private final StudentService studentService; // Inject StudentService

    public HouseService(HouseRepository houseRepository, StudentService studentService) {
        this.houseRepository = houseRepository;
        this.studentService = studentService;
    }

    public List<HouseResponseDTO> getAllHouses() {
        return houseRepository.findAll().stream().map(this::convertHouseToResponseDTO).collect(Collectors.toList());
    }

    public ResponseEntity<HouseResponseDTO> getHouseByName(String name) {
        Optional<House> houseOptional = houseRepository.findByName(name.toUpperCase());

        return houseOptional.map(house -> ResponseEntity.ok(convertHouseToResponseDTO(house)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public List<StudentResponseDTO> getStudentsByHouseName(String name) {
        Optional<House> houseOptional = houseRepository.findByName(name.toUpperCase());

        return houseOptional.map(house -> convertStudentsToResponseDTOs(house.getStudents()))
                .orElse(Collections.emptyList());
    }
    private List<StudentResponseDTO> convertStudentsToResponseDTOs(List<Student> students) {
        return students.stream().map(studentService::convertStudentToResponseDTO).collect(Collectors.toList());
    }

    private HouseResponseDTO convertHouseToResponseDTO(House house) {
        HouseResponseDTO responseDTO = new HouseResponseDTO();
        responseDTO.setName(house.getName());
        responseDTO.setFounder(house.getFounder());
        responseDTO.setColor1(house.getColor1());
        responseDTO.setColor2(house.getColor2());

        return responseDTO;
    }
}
