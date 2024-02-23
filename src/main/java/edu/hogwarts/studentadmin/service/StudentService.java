package edu.hogwarts.studentadmin.service;

import edu.hogwarts.studentadmin.api.dto.students.StudentFullNameDTO;
import edu.hogwarts.studentadmin.models.Student;
import edu.hogwarts.studentadmin.repositories.HouseRepository;
import edu.hogwarts.studentadmin.repositories.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private StudentRepository studentRepository;
    private HouseRepository houseRepository;

    public StudentService(StudentRepository studentRepository, HouseRepository houseRepository) {
        this.studentRepository = studentRepository;
        this.houseRepository = houseRepository;
    }

    public Student createStudentWithFullName(StudentFullNameDTO studentFullNameDTO) {
        // Extract and set first, middle, and last names
        String[] nameParts = extractNameParts(studentFullNameDTO.getFullName());

        Student student = new Student();
        student.setFirstName(nameParts[0]);
        student.setMiddleName(nameParts[1]);
        student.setLastName(nameParts[2]);
        student.setHouse(houseRepository.findByName(studentFullNameDTO.getHouse()).orElse(null));

        return studentRepository.save(student);
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
}
