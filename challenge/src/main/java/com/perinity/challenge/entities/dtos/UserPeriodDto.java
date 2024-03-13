package com.perinity.challenge.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserPeriodDto {
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
