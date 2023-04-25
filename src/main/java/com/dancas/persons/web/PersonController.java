package com.dancas.persons.web;

import com.dancas.persons.exception.PersonNotFoundException;
import com.dancas.persons.model.Person;
import com.dancas.persons.repository.PersonRepository;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class PersonController {

    private final PersonRepository repository;

    PersonController(PersonRepository repository){
        this.repository = repository;
    }

    @GetMapping("/persons")
    List<Person> all(){
        return repository.findAll();
    }

    @GetMapping("/persons/{id}")
    Person one(@PathVariable Long id){

        return repository.findById(id)
            .orElseThrow(() -> new PersonNotFoundException(id));
    }

    @PostMapping("/persons")
    Person newPerson(@RequestBody Person newPerson){
        return repository.save(newPerson);
    }


    @PutMapping("/persons/{id}")
    Person replacePerson(@RequestBody Person newPerson, @PathVariable Long id){

        return repository.findById(id)
            .map(person -> {
                person.setName(newPerson.getName());
                person.setRole(newPerson.getRole());
                return repository.save(person);
            })
            .orElseGet(() -> {
                newPerson.setId(id);
                return repository.save(newPerson);
            });
    }

    @DeleteMapping("/persons/{id}")
    void deletePerson(@PathVariable Long id){
        repository.deleteById(id);
    }



}
