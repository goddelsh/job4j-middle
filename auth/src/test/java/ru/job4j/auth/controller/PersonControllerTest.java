package ru.job4j.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.job4j.auth.AuthApplication;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = AuthApplication.class)
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PersonRepository personRepository;

    private List<Person> personList;

    @BeforeEach
    void setUp() {
        this.personList = List.of(new Person(1, "test1", "123"),
                new Person(2, "test2", "456"),
                new Person(3, "test3", "789"));
    }


    @Test
    void findAll() throws Exception {
        when(personRepository.findAll()).thenReturn(this.personList);
        mockMvc.perform(get("/person/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(this.personList.size()));
    }

    @Test
    void findById() throws Exception {
        when(personRepository.findById(2)).thenReturn(java.util.Optional.ofNullable(this.personList.get(2)));
        mockMvc.perform(get("/person/{id}", 2)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(personList.get(2).getId()))
                .andExpect(jsonPath("$.login").value(personList.get(2).getLogin()));
    }

    @Test
    void create() throws Exception {
        Person person = new Person();
        person.setId(1);
        person.setLogin("login");
        person.setPassword("123");
        when(personRepository.save(person)).thenReturn(person);
        mockMvc.perform(MockMvcRequestBuilders.post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.login").value(person.getLogin()));
    }


    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/person/{id}", 2))
                .andExpect(status().isOk());
    }
}