package com.perinity.challenge.services;

import com.perinity.challenge.entities.Job;
import com.perinity.challenge.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public boolean finalizeJob(Long id) throws Exception {
        try{
                Job j = jobRepository.findById(id).orElseThrow(()->new Exception("job not found"));
                j.setFinished(true);
                jobRepository.save(j);
                return true;
        }catch (Exception e) {
            return false;
        }
    }
}
