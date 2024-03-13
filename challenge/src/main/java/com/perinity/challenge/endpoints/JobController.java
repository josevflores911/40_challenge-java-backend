package com.perinity.challenge.endpoints;

import com.perinity.challenge.entities.Department;
import com.perinity.challenge.entities.Job;
import com.perinity.challenge.entities.User;

import com.perinity.challenge.entities.dtos.JobDto;
import com.perinity.challenge.entities.dtos.UserDto;
import com.perinity.challenge.repositories.DepartmentRepository;
import com.perinity.challenge.repositories.JobRepository;
import com.perinity.challenge.repositories.UserRepository;
import com.perinity.challenge.services.JobService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tarefas")
public class JobController {
    @Autowired
    private JobService jobService;

    @PostMapping
    public ResponseEntity<JobDto> addJob(@RequestBody final JobDto job) throws Exception {
        try {
            jobService.addJob(job);
            return Optional.of(job)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/finalizar/{id}")
    @Transactional
    public ResponseEntity<?> updateFinalizeJob(@PathVariable Long id) throws Exception {
        if(jobService.finalizeJob(id))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PutMapping("/alocar/{departmentId}")
    @Transactional
    public ResponseEntity<Integer> updateAllocateJob(@PathVariable Long departmentId) throws Exception {
        int value = jobService.allocateJob(departmentId);
        return ResponseEntity.ok(value);
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<JobDto>> getAllUsers() {
        List<JobDto> incompleteJobs =jobService.getIncompleteJobs();
        return ResponseEntity.ok(incompleteJobs);
    }
}
