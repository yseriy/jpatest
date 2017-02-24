package ru.nic.wh.jpatest.web.command;

import ru.nic.wh.jpatest.domain.Farm;
import ru.nic.wh.jpatest.web.Command;
import ru.nic.wh.jpatest.web.assembler.FarmAssembler;
import ru.nic.wh.jpatest.web.dto.FarmDTO;

public class UpdateFarm implements Command<Farm> {

	private final FarmAssembler farmAssembler;
	private final FarmDTO farmDTO;
	private final String farmName;

	public UpdateFarm(FarmAssembler farmAssembler, FarmDTO farmDTO, String farmName) {
		this.farmAssembler = farmAssembler;
		this.farmDTO = farmDTO;
		this.farmName = farmName;
	}

	@Override
	public Farm execute() {
		return farmAssembler.updateFarm(farmDTO, farmName);
	}
}
