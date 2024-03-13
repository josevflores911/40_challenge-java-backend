package com.perinity.challenge.entities.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class UserDto {
    private String name;
    private Long department_id;
}
