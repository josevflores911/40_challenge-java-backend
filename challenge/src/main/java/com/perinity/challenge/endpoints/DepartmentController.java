package com.perinity.challenge.endpoints;

import com.perinity.challenge.entities.Department;
import com.perinity.challenge.entities.Job;
import com.perinity.challenge.entities.User;
import com.perinity.challenge.entities.dtos.DepartmentDto;

import com.perinity.challenge.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    private DepartmentRepository departmentRepository;

    @PostMapping
    public ResponseEntity<?> addDepartment(@RequestBody final DepartmentDto department) throws Exception {
        try {
            Department d = new Department(department.getDepartmentName());
            departmentRepository.save(d);

            return Optional.of(department)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
