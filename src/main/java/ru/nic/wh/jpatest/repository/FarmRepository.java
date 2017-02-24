package ru.nic.wh.jpatest.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nic.wh.jpatest.domain.Farm;

@Repository
public interface FarmRepository extends CrudRepository<Farm, Long> {

	@Query("select f from Farm f where f.name = :name")
	Farm findByName(@Param("name") String name);
}
