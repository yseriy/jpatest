package ru.nic.wh.jpatest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nic.wh.jpatest.domain.Farm;
import ru.nic.wh.jpatest.domain.FarmIP;
import ru.nic.wh.jpatest.repository.FarmRepository;
import ru.nic.wh.jpatest.web.Command;

@Service
public class FarmService {

	private final FarmRepository farmRepository;

	@Autowired
	public FarmService(FarmRepository farmRepository) {
		this.farmRepository = farmRepository;
	}

	public void save(Command<Farm> command) {
		farmRepository.save(command.execute());
	}

	public void addFarmIP(Command<FarmIP> command, String farmName) {
		Farm farm = farmRepository.findByName(farmName);
		farm.getFarmIP().add(command.execute());
		farmRepository.save(farm);
	}
}
