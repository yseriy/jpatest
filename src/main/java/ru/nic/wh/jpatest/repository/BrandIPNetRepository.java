package ru.nic.wh.jpatest.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nic.wh.jpatest.domain.Brand;
import ru.nic.wh.jpatest.domain.BrandIPNet;
import ru.nic.wh.jpatest.domain.IPNet;

@Repository
public interface BrandIPNetRepository extends CrudRepository<BrandIPNet, Long> {

    @Modifying
    @Query("delete from BrandIPNet bi where bi.brand = :brand and bi.ipNet = :ipNet")
    void deleteBrandIPNetLink(@Param("brand") Brand brand, @Param("ipNet") IPNet ipNet);
}
