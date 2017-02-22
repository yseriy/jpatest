package ru.nic.wh.jpatest.web.command;

import ru.nic.wh.jpatest.domain.FarmIP;
import ru.nic.wh.jpatest.web.Command;
import ru.nic.wh.jpatest.web.assembler.FarmIPAssembler;
import ru.nic.wh.jpatest.web.dto.FarmIPDTO;

public class UpdateFarmIP implements Command<FarmIP> {

	private final FarmIPAssembler farmIPAssembler;

	private final FarmIPDTO farmIPDTO;

	private final String ipAddress;

	public UpdateFarmIP(FarmIPAssembler farmIPAssembler, FarmIPDTO farmIPDTO, String ipAddress) {
		this.farmIPAssembler = farmIPAssembler;
		this.farmIPDTO = farmIPDTO;
		this.ipAddress = ipAddress;
	}

	@Override
	public FarmIP execute() {
		return farmIPAssembler.updateFarmIP(farmIPDTO, ipAddress);
	}
}
