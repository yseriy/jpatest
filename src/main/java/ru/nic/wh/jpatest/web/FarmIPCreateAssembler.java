package ru.nic.wh.jpatest.web;

import ru.nic.wh.jpatest.domain.FarmIP;
import ru.nic.wh.jpatest.domain.FarmIPType;
import ru.nic.wh.jpatest.exception.ObjectNotFoundException;
import ru.nic.wh.jpatest.repository.FarmIPTypeRepository;
import ru.nic.wh.jpatest.web.dto.FarmIPCreateDTO;

public class FarmIPCreateAssembler implements Assembler<FarmIP> {

	private final FarmIPCreateDTO farmIPCreateDTO;

	private final FarmIPTypeRepository farmIPTypeRepository;

	public FarmIPCreateAssembler(FarmIPCreateDTO farmIPCreateDTO, FarmIPTypeRepository farmIPTypeRepository) {
		this.farmIPCreateDTO = farmIPCreateDTO;
		this.farmIPTypeRepository = farmIPTypeRepository;
	}

	@Override
	public FarmIP getEntity() {

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

	private FarmIPType getFarmIPType(String farmIPTypeName) {
		FarmIPType farmIPType = farmIPTypeRepository.findByName(farmIPTypeName);

		if (farmIPType == null) {
			throw new ObjectNotFoundException("FarmIPType '" + farmIPTypeName + "' not found");
		}

		return farmIPType;
	}
}
