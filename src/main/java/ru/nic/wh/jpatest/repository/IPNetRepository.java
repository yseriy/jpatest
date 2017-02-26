package ru.nic.wh.jpatest.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nic.wh.jpatest.domain.IPNet;
import ru.nic.wh.jpatest.miscellaneous.usertype.Inet;

@Repository
public interface IPNetRepository extends PagingAndSortingRepository<IPNet, Long> {

	@Query("select i from IPNet i where i.net = :net")
	IPNet findByNet(@Param("net") Inet net);
}
