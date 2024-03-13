package com.perinity.challenge.repositories;

import com.perinity.challenge.entities.Department;
import com.perinity.challenge.entities.dtos.DepartmentSummaryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("SELECT NEW com.perinity.challenge.entities.dtos.DepartmentSummaryDto(d.departmentName, COUNT(u), COUNT(j)) " +
            "FROM Departments d " +
            "LEFT JOIN d.userList u " +
            "LEFT JOIN u.jobs j " +
            "GROUP BY d.id, d.departmentName")
    List<DepartmentSummaryDto> searchDepartmentWithUserAndJobs();

//works
//    @Query(value = "SELECT department_name FROM departments", nativeQuery = true)
//    List<String> searchDepartmentWithUserAndJobs();
}
