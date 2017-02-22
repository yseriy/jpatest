package ru.nic.wh.jpatest.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ru.nic.wh.jpatest.domain.FarmIP;
import ru.nic.wh.jpatest.service.FarmIPService;
import ru.nic.wh.jpatest.web.assembler.FarmIPAssembler;
import ru.nic.wh.jpatest.web.command.CreateFarmIP;
import ru.nic.wh.jpatest.web.command.UpdateFarmIP;
import ru.nic.wh.jpatest.web.dto.FarmIPCreate;
import ru.nic.wh.jpatest.web.dto.FarmIPCreateDTO;
import ru.nic.wh.jpatest.web.dto.FarmIPDTO;

import javax.validation.groups.Default;

@RestController
public class FarmIPController {

	private final FarmIPService farmipService;

	private final FarmIPAssembler farmIPAssembler;

	@Autowired
	public FarmIPController(FarmIPService farmipService, FarmIPAssembler farmIPAssembler) {
		this.farmipService = farmipService;
		this.farmIPAssembler = farmIPAssembler;
	}

	@GetMapping("/farmips")
	public List<FarmIP> list() {
		return farmipService.list();
	}

	@PostMapping("/farmips")
	@ResponseStatus(HttpStatus.CREATED)
	public void save(@Validated(value = {Default.class, FarmIPCreate.class}) @RequestBody FarmIPCreateDTO farmIPDTO) {
		farmipService.save(new CreateFarmIP(farmIPAssembler, farmIPDTO));
	}

	@PutMapping("/farmips/{ipAddress:.+}")
	@ResponseStatus(HttpStatus.CREATED)
	public void update(@Validated @RequestBody FarmIPDTO farmIPDTO, @PathVariable("ipAddress") String ipAddress) {
		farmipService.save(new UpdateFarmIP(farmIPAssembler, farmIPDTO, ipAddress));
	}
}
