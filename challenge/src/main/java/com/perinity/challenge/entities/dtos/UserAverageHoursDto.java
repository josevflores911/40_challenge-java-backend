package com.perinity.challenge.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAverageHoursDto {

    private String name;
    private Double averageHoursSpent;
}