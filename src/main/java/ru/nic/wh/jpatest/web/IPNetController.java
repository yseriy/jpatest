package ru.nic.wh.jpatest.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.nic.wh.jpatest.dto.BrandDTO;
import ru.nic.wh.jpatest.dto.IPNetDTO;
import ru.nic.wh.jpatest.miscellaneous.validationgroups.NotNullGroup;
import ru.nic.wh.jpatest.service.IPNetService;

@RestController
public class IPNetController {

    private final IPNetService ipNetService;

    @Autowired
    public IPNetController(IPNetService ipNetService) {
        this.ipNetService = ipNetService;
    }

    @GetMapping("/ipnets")
    public Page<IPNetDTO> listAll(Pageable pageable) {
        return ipNetService.listAll(pageable);
    }

    @GetMapping("/ipnets/{netAddress:.+}")
    public IPNetDTO listByNet(@PathVariable String netAddress) {
        return ipNetService.listByName(netAddress);
    }

    @PostMapping("/ipnets")
    @ResponseStatus(HttpStatus.CREATED)
    public void addIPNet(@Validated(NotNullGroup.class) @RequestBody IPNetDTO ipNetDTO) {
        ipNetService.create(ipNetDTO);
    }

    @PutMapping("/ipnets/{netAddress:.+}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateIPNet(@Validated @RequestBody IPNetDTO ipNetDTO, @PathVariable String netAddress) {
        ipNetService.update(ipNetDTO, netAddress);
    }

    @PostMapping("/ipnets/{netAddress:.+}/brands")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBrand(@Validated(NotNullGroup.class) @RequestBody BrandDTO brandDTO, @PathVariable String netAddress) {
        ipNetService.addBrand(brandDTO, netAddress);
    }

    @DeleteMapping("/ipnets/{netAddress:.+}/brands/{brandName}")
    public void removeBrand(@PathVariable String netAddress, @PathVariable String brandName) {
        ipNetService.removeBrand(netAddress, brandName);
    }
}
