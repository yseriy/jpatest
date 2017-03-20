package ru.nic.wh.jpatest.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nic.wh.jpatest.domain.BrandFarmIP;
import ru.nic.wh.jpatest.domain.FarmIP;

@Repository
public interface BrandFarmIPRepository extends CrudRepository<BrandFarmIP, Long> {

    @Modifying
    @Query("delete from BrandFarmIP bf where bf.farmIP = :farmIP")
    void deleteBrandFarmIPLink(@Param("farmIP") FarmIP farmIP);
}
