package ru.nic.wh.jpatest.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nic.wh.jpatest.domain.FarmType;

@Repository
public interface FarmTypeRepository extends CrudRepository<FarmType, Long> {

	@Query("select ft from FarmType ft where ft.name = :name")
	FarmType findByName(@Param("name") String name);
}
