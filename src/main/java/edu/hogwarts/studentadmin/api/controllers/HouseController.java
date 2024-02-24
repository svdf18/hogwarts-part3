package edu.hogwarts.studentadmin.api.controllers;

import edu.hogwarts.studentadmin.api.dto.houses.HouseResponseDTO;
import edu.hogwarts.studentadmin.api.dto.students.StudentResponseDTO;
import edu.hogwarts.studentadmin.models.House;
import edu.hogwarts.studentadmin.models.Student;
import edu.hogwarts.studentadmin.repositories.HouseRepository;
import edu.hogwarts.studentadmin.service.HouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/houses")
public class HouseController {


    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping
    public List<HouseResponseDTO> getAllHouses() {
        return houseService.getAllHouses();
    }

    @GetMapping("/{name}")
    public ResponseEntity<HouseResponseDTO> getHouseByName(@PathVariable String name) {
        return houseService.getHouseByName(name);
    }

    @GetMapping("/{name}/students")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByHouseName(@PathVariable String name) {
        List<StudentResponseDTO> studentResponseDTOs = houseService.getStudentsByHouseName(name);
        return ResponseEntity.ok(studentResponseDTOs);
    }
}