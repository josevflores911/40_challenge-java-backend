package com.perinity.challenge.entities.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {

    private String name;
    private String department;
    private Long totalHoursSpent;

}