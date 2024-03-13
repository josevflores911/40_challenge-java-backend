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
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/jobs")
public class JobController {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private JobService jobService;

    @PostMapping
    public ResponseEntity<?> addJob(@RequestBody final JobDto job) throws Exception {
        try {
            final User u = userRepository.findById(job.getUser_id()).orElseThrow(()->new Exception("user not found"));
            final Department d = departmentRepository.findById(job.getDepartment_id()).orElseThrow(()->new Exception("department not found"));
            Job j = new Job(u,d,job.getJobTitle(),job.getJobDescription(),job.getJobExpectedDate(),job.getDuration());
            jobRepository.save(j);

            return Optional.of(job)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/finalize/{id}")
    public ResponseEntity<?> updateFinalizeJob(@PathVariable Long id) throws Exception {

        if(jobService.finalizeJob(id))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}
