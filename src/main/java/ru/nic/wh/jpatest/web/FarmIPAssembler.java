package ru.nic.wh.jpatest.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nic.wh.jpatest.domain.FarmIP;
import ru.nic.wh.jpatest.domain.FarmIPType;
import ru.nic.wh.jpatest.exception.ObjectNotFoundException;
import ru.nic.wh.jpatest.repository.FarmIPRepository;
import ru.nic.wh.jpatest.repository.FarmIPTypeRepository;
import ru.nic.wh.jpatest.repository.Inet;
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

	public FarmIP createFarmIP(FarmIPCreateDTO farmIPCreateDTO) {
		FarmIPType farmIPType = null;
		String ipAddress = null;

		if (farmIPCreateDTO.getFarmIpTypeName() != null) {
			farmIPType = getFarmIPType(farmIPCreateDTO.getFarmIpTypeName());
		}

		if (farmIPCreateDTO.getIp() != null) {
			ipAddress = farmIPCreateDTO.getIp();
		}

		return new FarmIP(ipAddress, farmIPType);
	}

	public FarmIP updateFarmIP(FarmIPDTO farmIPDTO, String ipAddress) {
		FarmIP farmIP = farmIPRepository.findByIp(new Inet(ipAddress));

		if (farmIP == null) {
			throw new ObjectNotFoundException("FarmIP '" + ipAddress + "' not found");
		}

		if (farmIPDTO.getFarmIpTypeName() != null) {
			farmIP.setFarmipType(getFarmIPType(farmIPDTO.getFarmIpTypeName()));
		}

		if (farmIPDTO.getIp() != null) {
			farmIP.setIp(new Inet(farmIPDTO.getIp()));
		}

		return farmIP;

	}

	private FarmIPType getFarmIPType(String farmIPTypeName) {
		FarmIPType farmIPType = farmIPTypeRepository.findByName(farmIPTypeName);

		if (farmIPType == null) {
			throw new ObjectNotFoundException("FarmIPType '" + farmIPTypeName + "' not found");
		}

		return farmIPType;
	}
}
