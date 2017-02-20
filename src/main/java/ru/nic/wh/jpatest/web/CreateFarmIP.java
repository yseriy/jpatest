package ru.nic.wh.jpatest.web;

import ru.nic.wh.jpatest.domain.FarmIP;
import ru.nic.wh.jpatest.web.dto.FarmIPCreateDTO;

public class CreateFarmIP implements Command<FarmIP> {

	private final FarmIPCreateDTO farmIPCreateDTO;

	private final FarmIPAssembler farmIPAssembler;

	public CreateFarmIP(FarmIPCreateDTO farmIPCreateDTO, FarmIPAssembler farmIPAssembler) {
		this.farmIPCreateDTO = farmIPCreateDTO;
		this.farmIPAssembler = farmIPAssembler;
	}

	@Override
	public FarmIP proceed() {
		return farmIPAssembler.createFarmIP(farmIPCreateDTO);
	}
}
