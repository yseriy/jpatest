package ru.nic.wh.jpatest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nic.wh.jpatest.domain.IPNetType;

@Repository
public interface IPNetTypeRepository extends CrudRepository<IPNetType, Long> {
}
