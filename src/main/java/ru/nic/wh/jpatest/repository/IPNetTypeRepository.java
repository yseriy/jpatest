package ru.nic.wh.jpatest.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nic.wh.jpatest.domain.IPNetType;

@Repository
public interface IPNetTypeRepository extends CrudRepository<IPNetType, Long> {

    @Query("select ipt from IPNetType ipt where ipt.name = :name")
    IPNetType findByName(@Param("name") String name);
}
