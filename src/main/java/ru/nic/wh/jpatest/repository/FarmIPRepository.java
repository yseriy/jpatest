package ru.nic.wh.jpatest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.nic.wh.jpatest.domain.FarmIP;

@Repository
public interface FarmIPRepository extends CrudRepository<FarmIP, Long> {
}
