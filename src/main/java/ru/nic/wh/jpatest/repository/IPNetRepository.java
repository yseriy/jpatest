package ru.nic.wh.jpatest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nic.wh.jpatest.domain.IPNet;
import ru.nic.wh.jpatest.miscellaneous.usertype.Inet;

@Repository
public interface IPNetRepository extends PagingAndSortingRepository<IPNet, Long> {

    @Query(value = "select i from IPNet i left join fetch i.ipNetType",
            countQuery = "select i from IPNet i left join i.ipNetType")
    Page<IPNet> findAllWithType(Pageable pageable);

    @Query("select i from IPNet i where i.net = :net")
    IPNet findByNet(@Param("net") Inet net);

    @Query("select i from IPNet i left join fetch i.ipNetType left join fetch i.brandIPNetList bl left join fetch bl.brand where i.net = :net")
    IPNet findByNetWithTypeAndBrand(@Param("net") Inet inet);
}
