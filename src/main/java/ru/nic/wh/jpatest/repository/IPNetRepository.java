package ru.nic.wh.jpatest.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.nic.wh.jpatest.domain.IPNet;

@Repository
public interface IPNetRepository extends PagingAndSortingRepository<IPNet, Long> {
}
