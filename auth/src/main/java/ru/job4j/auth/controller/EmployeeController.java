package ru.job4j.auth.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.domain.Employee;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    private final RestTemplate restTemplate;


    public EmployeeController(EmployeeRepository employeeRepository, RestTemplate restTemplate) {
        this.employeeRepository = employeeRepository;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public List<Employee> findAll() {
        return StreamSupport.stream(
                this.employeeRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersons(@PathVariable int id, @RequestBody List<Integer> person_ids) {
        Employee employee = this.employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            var personsList = employee.getAccounts();
            List<Person> newList = new ArrayList<>();
            for (int i = 0; i < personsList.size(); i++) {
                if (!person_ids.contains(personsList.get(i).getId())) {
                    newList.add(personsList.get(i));
                }
            }
            employee.setAccounts(newList);
            this.employeeRepository.save(employee);
        }
        return ResponseEntity.ok().build();
    }




    @PostMapping("/{id}")
    public ResponseEntity<Employee> addPersons(@PathVariable int id, @RequestBody List<Integer> person_ids) {

        List<Person> personList = new ArrayList<>();
        for (Integer p_id : person_ids) {
            Person person = restTemplate.getForObject(
                    "http://localhost:8085/person/{id}",
                    Person.class, p_id);
            if (person != null) {
                personList.add(person);
            }
        }



        Employee employee = this.employeeRepository.findById(id).orElse(null);

        if (!personList.isEmpty() && employee != null) {
            Set<Person> accounts = new HashSet<>(employee.getAccounts());
            accounts.addAll(personList);
            employee.setAccounts(new ArrayList<>(accounts));
            employeeRepository.save(employee);
            return new ResponseEntity<>(
                    employee,
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new Employee(),
                    HttpStatus.NOT_FOUND
            );
        }
    }



}
