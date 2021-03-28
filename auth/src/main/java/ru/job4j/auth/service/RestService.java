package ru.job4j.auth.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class RestService {

    @Bean
    public RestTemplate getTemplate() {
        return new RestTemplate();
    }

}
