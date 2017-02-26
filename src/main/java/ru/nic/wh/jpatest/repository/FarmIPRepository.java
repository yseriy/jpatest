package ru.nic.wh.jpatest.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nic.wh.jpatest.domain.FarmIP;
import ru.nic.wh.jpatest.miscellaneous.usertype.Inet;

import java.util.List;

@Repository
public interface FarmIPRepository extends CrudRepository<FarmIP, Long> {

	@Query("select f from FarmIP f where f.ip = :ip")
	FarmIP findByIp(@Param("ip") Inet ip);

	@Query("select f from FarmIP f left join fetch f.farmipType where f.farm.id = :farmId")
	List<FarmIP> findWithFarmIPTypeByFarmId(@Param("farmId") Long farmId);
}
