package com.telran.springmongodb.controller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class PersonDto {
    private int id;
    private String name;
    private String email;
    private String phone;
}
