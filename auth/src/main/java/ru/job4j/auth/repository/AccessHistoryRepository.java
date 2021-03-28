package ru.job4j.auth.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.auth.domain.AccessHistory;


public interface AccessHistoryRepository extends CrudRepository<AccessHistory, Integer> {
}
