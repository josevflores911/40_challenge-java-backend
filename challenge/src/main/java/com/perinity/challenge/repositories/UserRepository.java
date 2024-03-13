package com.perinity.challenge.repositories;

import com.perinity.challenge.entities.Department;
import com.perinity.challenge.entities.User;
import com.perinity.challenge.entities.dtos.UserAverageHoursDto;
import com.perinity.challenge.entities.dtos.UserDetailsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT NEW com.perinity.challenge.entities.dtos.UserDetailsDto(u.name, u.department.departmentName, COALESCE(SUM(j.duration), 0)) " +
            "FROM Users u " +
            "LEFT JOIN u.jobs j " +
            "GROUP BY u.name, u.department.departmentName")
    List<UserDetailsDto> findUserWithHours();

    @Query("SELECT NEW com.perinity.challenge.entities.dtos.UserAverageHoursDto(u.name, AVG(j.duration)) " +
            "FROM Users u " +
            "JOIN u.jobs j " +
            "WHERE u.name = :name " +
            "AND j.jobExpectedDate BETWEEN :startDate AND :endDate " +
            "GROUP BY u.id")
    List<UserAverageHoursDto> findUserByNameAndTime(
            @Param("name") String name,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    Optional<User> findAllByDepartment(Department department);

}
