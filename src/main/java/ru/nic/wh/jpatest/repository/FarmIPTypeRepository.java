package ru.nic.wh.jpatest.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nic.wh.jpatest.domain.FarmIPType;

@Repository
public interface FarmIPTypeRepository extends CrudRepository<FarmIPType, Long> {

    @Query("select f from FarmIPType f where f.name = :typename")
    FarmIPType findByName(@Param("typename") String name);
}
