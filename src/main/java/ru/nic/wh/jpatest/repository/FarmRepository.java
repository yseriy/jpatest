package ru.nic.wh.jpatest.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nic.wh.jpatest.domain.Farm;

import java.util.List;

@Repository
public interface FarmRepository extends CrudRepository<Farm, Long> {

	@Query("select f from Farm f where f.name = :name")
	Farm findByName(@Param("name") String name);

	@Query("select f from Farm f left join fetch f.location l left join fetch f.farmType ft")
	List<Farm> findAllWithLocationAndFarmType();

	@Query("select f from Farm f left join fetch f.location l left join fetch f.farmType ft where f.name = :name")
	Farm findWithLocationAndFarmTypeByName(@Param("name") String name);
}
