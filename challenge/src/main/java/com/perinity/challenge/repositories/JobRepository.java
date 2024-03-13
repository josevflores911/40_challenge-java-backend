package com.perinity.challenge.repositories;

import com.perinity.challenge.entities.Department;
import com.perinity.challenge.entities.Job;
import com.perinity.challenge.entities.User;
import com.perinity.challenge.entities.dtos.JobDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @Modifying
    @Query("UPDATE Jobs j SET j.user = :user WHERE j.id = :jobId AND j.department = :userDepartment")
    int alocarPessoaNaTarefa(@Param("jobId") Long jobId,
                             @Param("user") User user,
                             @Param("userDepartment") Department userDepartment);


    @Query("SELECT j FROM Jobs j WHERE j.user IS NULL ORDER BY j.jobExpectedDate ASC")
    List<Job> listarTarefasPendentesComPrazosMaisAntigos();

    @Query("SELECT j FROM Jobs j WHERE j.user IS NULL ORDER BY j.jobExpectedDate ASC")
    List<Job> listarTarefasPendentesComPrazosMaisAntigosLimit3();


}
