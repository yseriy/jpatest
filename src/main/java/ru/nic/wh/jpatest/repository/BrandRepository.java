package ru.nic.wh.jpatest.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nic.wh.jpatest.domain.Brand;

@Repository
public interface BrandRepository extends CrudRepository<Brand, Long> {

	@Query("select b from Brand b where b.name = :name")
	Brand findByName(@Param("name") String name);
}
