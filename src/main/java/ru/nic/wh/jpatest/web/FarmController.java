package ru.nic.wh.jpatest.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.nic.wh.jpatest.dto.FarmDTO;
import ru.nic.wh.jpatest.dto.FarmIPDTO;
import ru.nic.wh.jpatest.miscellaneous.validationgroups.NotNullGroup;
import ru.nic.wh.jpatest.service.FarmIPService;
import ru.nic.wh.jpatest.service.FarmService;

import java.util.List;

@RestController
public class FarmController {

	private final FarmService farmService;
	private final FarmIPService farmIPService;

	@Autowired
	public FarmController(FarmService farmService, FarmIPService farmIPService) {
		this.farmService = farmService;
		this.farmIPService = farmIPService;
	}

	@GetMapping("/farms")
	public List<FarmDTO> listFarm() {
		return farmService.list();
	}

	@GetMapping("/farms/{farmName}")
	public FarmDTO listFarmByName(@PathVariable String farmName) {
		return farmService.listByName(farmName);
	}

	@PostMapping("/farms")
	@ResponseStatus(HttpStatus.CREATED)
	public void addFarm(@Validated(NotNullGroup.class) @RequestBody FarmDTO farmDTO) {
		farmService.create(farmDTO);
	}

	@PutMapping("/farms/{farmName}")
	@ResponseStatus(HttpStatus.CREATED)
	public void updateFarm(@Validated @RequestBody FarmDTO farmDTO, @PathVariable String farmName) {
		farmService.update(farmDTO, farmName);
	}

	@GetMapping("/farms/{farmName}/ip")
	public List<FarmIPDTO> listFarmIp(@PathVariable String farmName) {
		return farmIPService.listByFarmName(farmName);
	}

	@PostMapping("/farms/{farmName}/ip")
	@ResponseStatus(HttpStatus.CREATED)
	public void addIP(@Validated(NotNullGroup.class) @RequestBody FarmIPDTO farmIPDTO, @PathVariable String farmName) {
		farmIPService.create(farmIPDTO, farmName);
	}

	@DeleteMapping("/farms/{farmName}/ip/{ipAddress:.+}")
	public void removeIP(@PathVariable String farmName, @PathVariable String ipAddress) {
		farmIPService.delete(ipAddress);
	}
}
