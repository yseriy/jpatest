package ru.nic.wh.jpatest.web.command;

import ru.nic.wh.jpatest.domain.FarmIP;
import ru.nic.wh.jpatest.web.Command;
import ru.nic.wh.jpatest.web.assembler.FarmIPAssembler;
import ru.nic.wh.jpatest.web.dto.FarmIPCreateDTO;

public class CreateFarmIP implements Command<FarmIP> {

	private final FarmIPAssembler farmIPAssembler;
	private final FarmIPCreateDTO farmIPCreateDTO;

	public CreateFarmIP(FarmIPAssembler farmIPAssembler, FarmIPCreateDTO farmIPCreateDTO) {
		this.farmIPAssembler = farmIPAssembler;
		this.farmIPCreateDTO = farmIPCreateDTO;
	}

	@Override
	public FarmIP execute() {
		return farmIPAssembler.createFarmIP(farmIPCreateDTO);
	}
}
