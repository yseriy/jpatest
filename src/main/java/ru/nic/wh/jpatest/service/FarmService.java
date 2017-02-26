package ru.nic.wh.jpatest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nic.wh.jpatest.domain.Farm;
import ru.nic.wh.jpatest.domain.FarmType;
import ru.nic.wh.jpatest.domain.Location;
import ru.nic.wh.jpatest.dto.FarmDTO;
import ru.nic.wh.jpatest.dto.FarmIPDTO;
import ru.nic.wh.jpatest.exception.ObjectNotFoundException;
import ru.nic.wh.jpatest.repository.FarmRepository;
import ru.nic.wh.jpatest.repository.FarmTypeRepository;
import ru.nic.wh.jpatest.repository.LocationRepository;

import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FarmService {

	private final FarmRepository farmRepository;
	private final LocationRepository locationRepository;
	private final FarmTypeRepository farmTypeRepository;
	private final FarmIPService farmIPService;

	@Autowired
	public FarmService(FarmRepository farmRepository, LocationRepository locationRepository,
					   FarmTypeRepository farmTypeRepository, FarmIPService farmIPService) {

		this.farmRepository = farmRepository;
		this.locationRepository = locationRepository;
		this.farmTypeRepository = farmTypeRepository;
		this.farmIPService = farmIPService;
	}

	public List<FarmDTO> list() {
		PersistenceUtil persistenceUtil = Persistence.getPersistenceUtil();
		List<Farm> farmList = farmRepository.findAllWithLocationAndFarmType();
		List<FarmDTO> farmDTOList = new ArrayList<>();

		for (Farm farm : farmList) {

			FarmDTO farmDTO = new FarmDTO();
			farmDTO.setName(farm.getName());
			farmDTO.setCapacity(farm.getCapacity());

			if (persistenceUtil.isLoaded(farm.getLocation()) && farm.getLocation() != null) {
				farmDTO.setLocationName(farm.getLocation().getName());
			}

			if (persistenceUtil.isLoaded(farm.getFarmType()) && farm.getFarmType() != null) {
				farmDTO.setFarmtypeName(farm.getFarmType().getName());
			}

			farmDTOList.add(farmDTO);
		}

		return farmDTOList;
	}

	public FarmDTO listByName(String farmName) {
		PersistenceUtil persistenceUtil = Persistence.getPersistenceUtil();
		Farm farm = farmRepository.findWithLocationAndFarmTypeByName(farmName);

		if (farm == null) {
			throw new ObjectNotFoundException("Farm '" + farmName + "' not found");
		}

		FarmDTO farmDTO = new FarmDTO();
		farmDTO.setName(farm.getName());
		farmDTO.setCapacity(farm.getCapacity());

		if (persistenceUtil.isLoaded(farm.getLocation()) && farm.getLocation() != null) {
			farmDTO.setLocationName(farm.getLocation().getName());
		}

		if (persistenceUtil.isLoaded(farm.getFarmType()) && farm.getFarmType() != null) {
			farmDTO.setFarmtypeName(farm.getFarmType().getName());
		}

		return farmDTO;
	}

	public void create(FarmDTO farmDTO) {
		Location location = locationRepository.findByName(farmDTO.getLocationName());

		if (location == null) {
			throw new ObjectNotFoundException("Location '" + farmDTO.getLocationName() + "' not found");
		}

		FarmType farmType = farmTypeRepository.findByName(farmDTO.getFarmtypeName());

		if (farmType == null) {
			throw new ObjectNotFoundException("FarmType '" + farmDTO.getFarmtypeName() + "' not found");
		}

		Farm farm = new Farm(farmDTO.getName(), farmDTO.getCapacity(), location, farmType);
		farmRepository.save(farm);

		for (FarmIPDTO farmIPDTO : farmDTO.getFarmipList()) {
			farmIPService.create(farmIPDTO, farm);
		}
	}

	public void update(FarmDTO farmDTO, String farmName) {
		Farm farm = farmRepository.findWithLocationAndFarmTypeByName(farmName);

		if (farm == null) {
			throw new ObjectNotFoundException("Farm '" + farmName + "' not found");
		}

		if (farmDTO.getLocationName() != null) {
			Location location = locationRepository.findByName(farmDTO.getLocationName());

			if (location == null) {
				throw new ObjectNotFoundException("Location '" + farmDTO.getLocationName() + "' not found");
			}

			farm.setLocation(location);
		}

		if (farmDTO.getFarmtypeName() != null) {
			FarmType farmType = farmTypeRepository.findByName(farmDTO.getFarmtypeName());

			if (farmType == null) {
				throw new ObjectNotFoundException("FarmType '" + farmDTO.getFarmtypeName() + "' not found");
			}

			farm.setFarmType(farmType);
		}

		if (farmDTO.getName() != null) {
			farm.setName(farmDTO.getName());
		}

		if (farmDTO.getCapacity() != null) {
			farm.setCapacity(farmDTO.getCapacity());
		}

		farmRepository.save(farm);
	}
}
