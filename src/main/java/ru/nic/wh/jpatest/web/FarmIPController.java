package ru.nic.wh.jpatest.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ru.nic.wh.jpatest.domain.FarmIP;
import ru.nic.wh.jpatest.service.FarmIPService;
import ru.nic.wh.jpatest.web.dto.FarmIPDTO;

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
	public void save(@RequestBody FarmIPDTO farmip) {
		farmipService.save(farmip);
	}
}
