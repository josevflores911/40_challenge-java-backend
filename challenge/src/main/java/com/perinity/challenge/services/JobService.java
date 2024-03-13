package com.perinity.challenge.services;

import com.perinity.challenge.entities.Department;
import com.perinity.challenge.entities.Job;
import com.perinity.challenge.entities.User;
import com.perinity.challenge.entities.dtos.JobDto;
import com.perinity.challenge.repositories.DepartmentRepository;
import com.perinity.challenge.repositories.JobRepository;
import com.perinity.challenge.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;


    @Transactional
    public Job addJob(JobDto jobDto) throws Exception {
        try {
            User user = null;
            if (jobDto.getUser_id() != null) {
                user = userRepository.findById(jobDto.getUser_id())
                        .orElseThrow(() -> new Exception("User not found"));
            }

            Department department = departmentRepository.findById(jobDto.getDepartment_id())
                    .orElseThrow(() -> new Exception("Department not found"));

            Job job = new Job(user, department, jobDto.getJobTitle(), jobDto.getJobDescription(),
                    jobDto.getJobExpectedDate(), jobDto.getDuration());

            return jobRepository.save(job);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error adding job", e);
        }
    }
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

    @Transactional
    public int allocateJob(Long departmentId) throws Exception {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new Exception("Department not found"));

        User user = userRepository.findAllByDepartment(department).stream().findAny()
                .orElseThrow(() -> new Exception("User not found"));

        return jobRepository.alocarPessoaNaTarefa(departmentId,user,department);

    }

    public List<JobDto> getIncompleteJobs() {
        return jobRepository.listarTarefasPendentesComPrazosMaisAntigosLimit3().stream()
                .map(e -> new JobDto(e.getDepartment().getId(), e.getJobTitle(), e.getJobDescription(),
                        e.getJobExpectedDate(), e.getDuration(), e.isFinished()))
                .collect(Collectors.toList());
    }
}
