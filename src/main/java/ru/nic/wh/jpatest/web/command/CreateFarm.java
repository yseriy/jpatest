package ru.nic.wh.jpatest.web.command;

import ru.nic.wh.jpatest.domain.Farm;
import ru.nic.wh.jpatest.web.Command;
import ru.nic.wh.jpatest.web.assembler.FarmAssembler;
import ru.nic.wh.jpatest.web.dto.FarmCreateDTO;

public class CreateFarm implements Command<Farm> {

	private final FarmAssembler farmAssembler;
	private final FarmCreateDTO farmCreateDTO;

	public CreateFarm(FarmAssembler farmAssembler, FarmCreateDTO farmCreateDTO) {
		this.farmAssembler = farmAssembler;
		this.farmCreateDTO = farmCreateDTO;
	}

	@Override
	public Farm execute() {
		return farmAssembler.createFarm(farmCreateDTO);
	}
}
