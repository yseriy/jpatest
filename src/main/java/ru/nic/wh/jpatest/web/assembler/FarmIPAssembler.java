package ru.nic.wh.jpatest.web.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nic.wh.jpatest.domain.Farm;
import ru.nic.wh.jpatest.domain.FarmIP;
import ru.nic.wh.jpatest.domain.FarmIPType;
import ru.nic.wh.jpatest.exception.ObjectNotFoundException;
import ru.nic.wh.jpatest.repository.FarmIPRepository;
import ru.nic.wh.jpatest.repository.FarmIPTypeRepository;
import ru.nic.wh.jpatest.repository.FarmRepository;
import ru.nic.wh.jpatest.repository.usertype.Inet;
import ru.nic.wh.jpatest.web.dto.FarmIPCreateDTO;
import ru.nic.wh.jpatest.web.dto.FarmIPDTO;

@Component
public class FarmIPAssembler {

	private final FarmIPRepository farmIPRepository;
	private final FarmIPTypeRepository farmIPTypeRepository;

	@Autowired
	public FarmIPAssembler(FarmIPRepository farmIPRepository, FarmIPTypeRepository farmIPTypeRepository) {
		this.farmIPRepository = farmIPRepository;
		this.farmIPTypeRepository = farmIPTypeRepository;
	}

	public FarmIP createFarmIP(FarmIPCreateDTO farmIPDTO) {
		return new FarmIP(farmIPDTO.getIp(), farmIPTypeRepository.findByName(farmIPDTO.getFarmIpTypeName()));
	}

	public FarmIP updateFarmIP(FarmIPDTO farmIPDTO, String ipAddress) {
		FarmIP farmIP = farmIPRepository.findByIp(new Inet(ipAddress));

		if (farmIP == null) {
			throw new ObjectNotFoundException("FarmIP '" + ipAddress + "' not found");
		}

		farmIP.setFarmipType(farmIPTypeRepository.findByName(farmIPDTO.getFarmIpTypeName()));
		farmIP.setIp(new Inet(farmIPDTO.getIp()));

		return farmIP;
	}
}
