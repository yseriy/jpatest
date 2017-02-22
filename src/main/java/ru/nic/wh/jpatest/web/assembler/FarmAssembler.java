package ru.nic.wh.jpatest.web.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nic.wh.jpatest.domain.*;
import ru.nic.wh.jpatest.exception.ObjectNotFoundException;
import ru.nic.wh.jpatest.repository.*;
import ru.nic.wh.jpatest.web.dto.FarmCreateDTO;
import ru.nic.wh.jpatest.web.dto.FarmDTO;
import ru.nic.wh.jpatest.web.dto.FarmIPCreateDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class FarmAssembler {

	private final FarmRepository farmRepository;
	private final LocationRepository locationRepository;
	private final FarmTypeRepository farmTypeRepository;
	private final FarmIPTypeRepository farmIPTypeRepository;
	private final BrandRepository brandRepository;

	@Autowired
	public FarmAssembler(FarmRepository farmRepository, LocationRepository locationRepository,
						 FarmTypeRepository farmTypeRepository, FarmIPTypeRepository farmIPTypeRepository,
						 BrandRepository brandRepository) {
		this.farmRepository = farmRepository;
		this.locationRepository = locationRepository;
		this.farmTypeRepository = farmTypeRepository;
		this.farmIPTypeRepository = farmIPTypeRepository;
		this.brandRepository = brandRepository;
	}

	public Farm createFarm(FarmCreateDTO farmCreateDTO) {
		Location location = locationRepository.findByName(farmCreateDTO.getLocation());
		FarmType farmType = farmTypeRepository.findByName(farmCreateDTO.getFarmtypeName());
		Farm farm = new Farm(farmCreateDTO.getName(), farmCreateDTO.getCapacity(), location, farmType);

		List<FarmIP> farmIPList = new ArrayList<>();
		for (FarmIPCreateDTO farmIPCreateDTO : farmCreateDTO.getFarmipList()) {
			String ipAddress = farmIPCreateDTO.getIp();
			FarmIPType farmIPType = farmIPTypeRepository.findByName(farmIPCreateDTO.getFarmIpTypeName());

			FarmIP farmIP = new FarmIP(ipAddress, farmIPType, farm);
			setBrand(farmIPCreateDTO.getBrandNameList(), farmIP);
			farmIPList.add(farmIP);
		}

		farm.setFarmIP(farmIPList);
		return farm;
	}

	private void setBrand(List<String> brandNameList, FarmIP farmIP) {
		for (String brandName : brandNameList) {
			farmIP.addBrand(brandRepository.findByName(brandName));
		}
	}

	public Farm updateFarm(FarmDTO farmDTO, String farmName) {
		Farm farm = farmRepository.findByName(farmName);

		if (farm == null) {
			throw new ObjectNotFoundException("Farm '" + farmName + "' not found");
		}

		Location location = locationRepository.findByName(farmDTO.getLocation());
		FarmType farmType = farmTypeRepository.findByName(farmDTO.getFarmtypeName());

		farm.setName(farmDTO.getName());
		farm.setCapacity(farmDTO.getCapacity());
		farm.setLocation(location);
		farm.setFarmType(farmType);

		return farm;
	}
}
