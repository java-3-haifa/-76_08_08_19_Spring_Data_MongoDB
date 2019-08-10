package com.telran.springmongodb.data.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Document(collection = "persons")
public class Person {
    @Id
    private int id;
    private String name;
    private String email;
    private String phone;
}
