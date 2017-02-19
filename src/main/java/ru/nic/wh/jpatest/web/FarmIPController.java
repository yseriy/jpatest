package ru.nic.wh.jpatest.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ru.nic.wh.jpatest.domain.FarmIP;
import ru.nic.wh.jpatest.service.FarmIPService;
import ru.nic.wh.jpatest.web.dto.FarmIPCreate;
import ru.nic.wh.jpatest.web.dto.FarmIPCreateDTO;
import ru.nic.wh.jpatest.web.dto.FarmIPDTO;

import javax.validation.groups.Default;

@RestController
public class FarmIPController {

	private final FarmIPService farmipService;

	@Autowired
	public FarmIPController(FarmIPService farmipService) {
		this.farmipService = farmipService;
	}

	@GetMapping("/farmips")
	public List<FarmIP> list() {
		return farmipService.list();
	}

	@PostMapping("/farmips")
	@ResponseStatus(HttpStatus.CREATED)
	public void save(@Validated(value = {Default.class, FarmIPCreate.class}) @RequestBody FarmIPCreateDTO farmip) {
		farmipService.save(farmip);
	}

	@PutMapping("/farmips/{ipAddress:.+}")
	@ResponseStatus(HttpStatus.CREATED)
	public void update(@Validated @RequestBody FarmIPDTO farmip, @PathVariable("ipAddress") String ipAddress) {
		farmipService.update(farmip, ipAddress);
	}
}
