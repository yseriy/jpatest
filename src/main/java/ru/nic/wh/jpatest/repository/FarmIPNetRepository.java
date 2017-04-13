package ru.nic.wh.jpatest.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nic.wh.jpatest.domain.Farm;
import ru.nic.wh.jpatest.domain.FarmIPNet;
import ru.nic.wh.jpatest.domain.IPNet;

@Repository
public interface FarmIPNetRepository extends CrudRepository<FarmIPNet, Long> {

    @Modifying
    @Query("delete from FarmIPNet fi where fi.farm = :farm and fi.ipNet = :ipNet")
    void deleteByFarmAndIPNet(@Param("farm") Farm farm, @Param("ipNet") IPNet ipNet);
}
