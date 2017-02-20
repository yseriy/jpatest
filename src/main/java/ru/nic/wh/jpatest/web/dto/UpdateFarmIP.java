package ru.nic.wh.jpatest.web.dto;

import ru.nic.wh.jpatest.domain.FarmIP;
import ru.nic.wh.jpatest.web.Command;
import ru.nic.wh.jpatest.web.FarmIPAssembler;

public class UpdateFarmIP implements Command<FarmIP> {

	private final String ipAddress;

	private final FarmIPDTO farmIPDTO;

	private final FarmIPAssembler farmIPAssembler;

	public UpdateFarmIP(String ipAddress, FarmIPDTO farmIPDTO, FarmIPAssembler farmIPAssembler) {
		this.ipAddress = ipAddress;
		this.farmIPDTO = farmIPDTO;
		this.farmIPAssembler = farmIPAssembler;
	}

	@Override
	public FarmIP proceed() {
		return farmIPAssembler.updateFarmIP(farmIPDTO, ipAddress);
	}
}
