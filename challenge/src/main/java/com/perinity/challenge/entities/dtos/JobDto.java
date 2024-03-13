package com.perinity.challenge.entities.dtos;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {
    private  Long user_id;
    private String jobTitle;
    private String jobDescription;
    private LocalDateTime jobExpectedDate;
    private Integer duration;
    private boolean finished;
    private Long department_id;

    public JobDto( Long departmentId, String jobTitle, String jobDescription, LocalDateTime jobExpectedDate, Integer duration, boolean finished) {
        this.department_id=departmentId;
        this.jobTitle=jobTitle;
        this.jobDescription=jobDescription;
        this.jobExpectedDate=jobExpectedDate;
        this.duration=duration;
        this.finished=finished;
    }
}
