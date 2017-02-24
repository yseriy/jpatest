package ru.nic.wh.jpatest.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.nic.wh.jpatest.service.FarmService;
import ru.nic.wh.jpatest.web.assembler.FarmAssembler;
import ru.nic.wh.jpatest.web.assembler.FarmIPAssembler;
import ru.nic.wh.jpatest.web.command.CreateFarm;
import ru.nic.wh.jpatest.web.command.CreateFarmIP;
import ru.nic.wh.jpatest.web.command.UpdateFarm;
import ru.nic.wh.jpatest.web.dto.FarmCreateDTO;
import ru.nic.wh.jpatest.web.dto.FarmDTO;
import ru.nic.wh.jpatest.web.dto.FarmIPCreateDTO;

@RestController
public class FarmController {

	private final FarmService farmService;
	private final FarmAssembler farmAssembler;
	private final FarmIPAssembler farmIPAssembler;

	@Autowired
	public FarmController(FarmService farmService, FarmAssembler farmAssembler, FarmIPAssembler farmIPAssembler) {
		this.farmService = farmService;
		this.farmAssembler = farmAssembler;
		this.farmIPAssembler = farmIPAssembler;
	}

	@PostMapping("/farms")
	@ResponseStatus(HttpStatus.CREATED)
	public void addFarm(@RequestBody FarmCreateDTO farmDTO) {
		farmService.save(new CreateFarm(farmAssembler, farmDTO));
	}

	@PutMapping("/farms/{farmName}")
	public void updateFarm(@RequestBody FarmDTO farmDTO, @PathVariable String farmName) {
		farmService.save(new UpdateFarm(farmAssembler, farmDTO, farmName));
	}

	@PostMapping("/farms/{farmName}/ip")
	@ResponseStatus(HttpStatus.CREATED)
	public void addIP(@RequestBody FarmIPCreateDTO farmIPDTO, @PathVariable String farmName) {
		farmService.addFarmIP(new CreateFarmIP(farmIPAssembler, farmIPDTO), farmName);
	}
}
