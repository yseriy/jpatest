package ru.nic.wh.jpatest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.nic.wh.jpatest.domain.Brand;
import ru.nic.wh.jpatest.domain.FarmIP;
import ru.nic.wh.jpatest.domain.FarmIPType;
import ru.nic.wh.jpatest.repository.BrandRepository;
import ru.nic.wh.jpatest.repository.FarmIPRepository;
import ru.nic.wh.jpatest.repository.FarmIPTypeRepository;
import ru.nic.wh.jpatest.repository.Inet;
import ru.nic.wh.jpatest.web.dto.FarmIPCreateDTO;
import ru.nic.wh.jpatest.web.dto.FarmIPDTO;

@Service
@Transactional
public class FarmIPService {

	private final FarmIPRepository farmipRepository;

	private final FarmIPTypeRepository farmipTypeRepository;

	private final BrandRepository brandRepository;

	@Autowired
	public FarmIPService(FarmIPRepository farmipRepository, FarmIPTypeRepository farmipTypeRepository,
						 BrandRepository brandRepository) {
		this.farmipRepository = farmipRepository;
		this.farmipTypeRepository = farmipTypeRepository;
		this.brandRepository = brandRepository;
	}

	public List<FarmIP> list() {
		List<FarmIP> farmipList = new ArrayList<>();

		for (FarmIP farmip : farmipRepository.findAll())
			farmipList.add(farmip);

		return farmipList;
	}

	public void save(FarmIPCreateDTO farmipDTO) {
		FarmIPType farmIPType = farmipTypeRepository.findByName(farmipDTO.getFarmIpTypeName());
		FarmIP farmip = new FarmIP(farmipDTO.getIp(), farmIPType);

		for (String brandName : farmipDTO.getBrandNameList()) {
			Brand brand = brandRepository.findByName(brandName);
			if (brand != null)
				farmip.addBrand(brand);
		}

		farmipRepository.save(farmip);
	}

	public void update(FarmIPDTO farmIPDTO, String ipAddress) {
		FarmIP farmIP = farmipRepository.findByIp(new Inet(ipAddress));
		farmIP.setIp(new Inet(farmIPDTO.getIp()));
		farmipRepository.save(farmIP);
	}
}
