package com.perinity.challenge.entities.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode
@Getter
public class JobDto {
    private  Long user_id;
    private String jobTitle;
    private String jobDescription;
    private LocalDateTime jobExpectedDate;
    private Integer duration;
    private boolean finished;
    private Long department_id;

}
