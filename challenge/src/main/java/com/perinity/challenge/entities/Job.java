package com.perinity.challenge.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "Jobs")
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
public class Job implements Serializable {
    public Job(User user,Department department,String jobTitle,String jobDescription,LocalDateTime jobExpectedDate,int duration){
        this.user=user;
        this.department=department;
        this.jobTitle=jobTitle;
        this.jobDescription=jobDescription;
        this.jobExpectedDate=jobExpectedDate;
        this.duration=duration;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobTitle;
    private String jobDescription;
    private LocalDateTime jobExpectedDate;
    private Integer duration;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    private boolean finished=false;
}
