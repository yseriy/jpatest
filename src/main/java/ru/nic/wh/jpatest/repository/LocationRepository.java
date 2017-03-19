package ru.nic.wh.jpatest.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nic.wh.jpatest.domain.Location;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

    @Query("select l from Location l where l.name = :name")
    Location findByName(@Param("name") String name);
}
