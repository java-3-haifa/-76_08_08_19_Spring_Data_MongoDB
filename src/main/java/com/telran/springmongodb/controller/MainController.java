package com.telran.springmongodb.controller;

import com.telran.springmongodb.controller.dto.CatalogCarDto;
import com.telran.springmongodb.controller.dto.PersonDto;
import com.telran.springmongodb.data.CarCatalogRepository;
import com.telran.springmongodb.data.PersonRepository;
import com.telran.springmongodb.data.document.CatalogCar;
import com.telran.springmongodb.data.document.Person;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@RestController
public class MainController {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    CarCatalogRepository carRepository;

    @PostMapping("person")
    public void addPerson(@RequestBody PersonDto personDto) {
        if (personRepository.existsById(personDto.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Person with id: " + personDto.getId() + " already exists");
        }
        Person person = Person.builder()
                .id(personDto.getId())
                .name(personDto.getName())
                .email(personDto.getEmail())
                .phone(personDto.getPhone())
                .build();
        personRepository.save(person);
    }

    @GetMapping("person/all")
    public List<PersonDto> getAllPersons() {
        List<Person> list = personRepository.findAll();
        return list.stream()
                .map(person -> PersonDto.builder()
                        .id(person.getId())
                        .name(person.getName())
                        .email(person.getEmail())
                        .phone(person.getPhone())
                        .build())
                .collect(toList());
    }

    @GetMapping("person/{id}")
    public PersonDto getPersonById(@PathVariable("id") Integer id) {
        return personRepository.findById(id)
                .map(p -> PersonDto.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .email(p.getEmail())
                        .phone(p.getPhone())
                        .build())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person with id:" + id + " does not exists"));
    }

    @PostMapping("catalog/car")
    public String addCar(@RequestBody CatalogCarDto dto) {
        if (dto.getId() != null && carRepository.existsById(new ObjectId(dto.getId()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        return carRepository.save(CatalogCar.builder()
                .colors(dto.getColors())
                .price(dto.getPrice().doubleValue())
                .model(dto.getModel())
                .build())
                .getId().toHexString();
    }

    @GetMapping("catalog/all")
    public List<CatalogCarDto> getAllCars() {
        return carRepository.findAll().stream()
                .map(car -> CatalogCarDto.builder()
                        .id(car.getId().toHexString())
                        .model(car.getModel())
                        .colors(car.getColors())
                        .price(BigDecimal.valueOf(car.getPrice()))
                        .build())
                .collect(toList());
    }
}
