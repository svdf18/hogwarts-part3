package edu.hogwarts.studentadmin.controllers;

import edu.hogwarts.studentadmin.models.House;
import edu.hogwarts.studentadmin.models.Student;
import edu.hogwarts.studentadmin.repositories.HouseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/houses")
public class HouseController {

    private final HouseRepository houseRepository;

    public HouseController(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @GetMapping
    public List<House> getAllHouses() {
        return houseRepository.findAll();
    }

    @GetMapping("/{name}")
    public ResponseEntity<House> getHouseByName(@PathVariable String name) {
        Optional<House> houseOptional = houseRepository.findByName(name.toUpperCase());

        return houseOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{name}/students")
    public ResponseEntity<List<Student>> getStudentsByHouseName(@PathVariable String name) {
        Optional<House> houseOptional = houseRepository.findByName(name.toUpperCase());

        return houseOptional.map(house -> ResponseEntity.ok(house.getStudents())).orElseGet(() -> ResponseEntity.notFound().build());
    }
}