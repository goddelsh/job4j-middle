package ru.job4j.auth.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.AuthApplication;
import ru.job4j.auth.domain.Employee;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.EmployeeRepository;
import ru.job4j.auth.repository.PersonRepository;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AuthApplication.class)
@AutoConfigureMockMvc
public class EmployeeControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @MockBean
    RestTemplate restTemplate;

    @MockBean
    PersonRepository personRepository;

    @MockBean
    EmployeeRepository employeeRepository;

    private List<Person> personList;
    private List<Employee> employeeList;

    @BeforeEach
    void setUp() {
        this.personList = List.of(new Person(1, "test1", "123"),
                new Person(2, "test2", "456"),
                new Person(3, "test3", "789"));
        this.employeeList = List.of(new Employee(1, "Adam", "123456", new Date()),
                new Employee(2, "Eve", "DFDDAF", new Date()));

    }


    @Test
    public void findAll() throws Exception {
        when(employeeRepository.findAll()).thenReturn(this.employeeList);
        mockMvc.perform(get("/employee/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(this.employeeList.size()));
    }
}