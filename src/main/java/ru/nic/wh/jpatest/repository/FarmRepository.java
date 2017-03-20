package ru.nic.wh.jpatest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nic.wh.jpatest.domain.Farm;

@Repository
public interface FarmRepository extends PagingAndSortingRepository<Farm, Long> {

    @Query("select f from Farm f where f.name = :name")
    Farm findByName(@Param("name") String name);

    @Query(value = "select f from Farm f left join fetch f.location l left join fetch f.farmType ft",
            countQuery = "select count (f) from Farm f left join f.location l left join f.farmType ft")
    Page<Farm> findAllWithLocationAndFarmType(Pageable pageable);

    @Query("select f from Farm f " +
            "left join fetch f.location l " +
            "left join fetch f.farmType ft " +
            "left join fetch f.farmIPList fil " +
            "left join fetch fil.farmipType " +
            "left join fetch fil.brandFarmIPList bfl " +
            "left join fetch bfl.brand " +
            "where f.name = :name")
    Farm findByNameFullFetch(@Param("name") String name);
}
