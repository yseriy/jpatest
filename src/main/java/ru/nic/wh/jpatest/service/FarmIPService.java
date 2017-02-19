package ru.nic.wh.jpatest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.nic.wh.jpatest.domain.FarmIP;
import ru.nic.wh.jpatest.domain.FarmIPType;
import ru.nic.wh.jpatest.repository.FarmIPRepository;
import ru.nic.wh.jpatest.repository.FarmIPTypeRepository;
import ru.nic.wh.jpatest.web.dto.FarmIPDTO;

@Service
@Transactional
public class FarmIPService {

	private final FarmIPRepository farmipRepository;

	private final FarmIPTypeRepository farmipTypeRepository;

	@Autowired
	public FarmIPService(FarmIPRepository farmipRepository, FarmIPTypeRepository farmipTypeRepository) {
		this.farmipRepository = farmipRepository;
		this.farmipTypeRepository = farmipTypeRepository;
	}

	public List<FarmIP> list() {
		List<FarmIP> farmipList = new ArrayList<>();

		for (FarmIP farmip : farmipRepository.findAll())
			farmipList.add(farmip);

		return farmipList;
	}

	public void save(FarmIPDTO farmipDTO) {
		FarmIPType farmIPType = farmipTypeRepository.findByName(farmipDTO.getFarmIpTypeName());
		FarmIP farmip = new FarmIP(farmipDTO.getIp(), farmIPType);
		farmipRepository.save(farmip);
	}
}
