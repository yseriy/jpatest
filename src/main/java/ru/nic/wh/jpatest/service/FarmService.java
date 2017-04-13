package ru.nic.wh.jpatest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nic.wh.jpatest.domain.*;
import ru.nic.wh.jpatest.dto.BrandDTO;
import ru.nic.wh.jpatest.dto.FarmDTO;
import ru.nic.wh.jpatest.dto.FarmIPDTO;
import ru.nic.wh.jpatest.dto.IPNetDTO;
import ru.nic.wh.jpatest.exception.ObjectNotFoundException;
import ru.nic.wh.jpatest.miscellaneous.usertype.Inet;
import ru.nic.wh.jpatest.repository.*;

import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class FarmService {

    private final FarmRepository farmRepository;
    private final LocationRepository locationRepository;
    private final FarmTypeRepository farmTypeRepository;
    private final IPNetRepository ipNetRepository;
    private final FarmIPNetRepository farmIPNetRepository;
    private final FarmIPService farmIPService;

    public FarmService(FarmRepository farmRepository, LocationRepository locationRepository,
                       FarmTypeRepository farmTypeRepository, IPNetRepository ipNetRepository,
                       FarmIPNetRepository farmIPNetRepository, FarmIPService farmIPService) {

        this.farmRepository = farmRepository;
        this.locationRepository = locationRepository;
        this.farmTypeRepository = farmTypeRepository;
        this.ipNetRepository = ipNetRepository;
        this.farmIPNetRepository = farmIPNetRepository;
        this.farmIPService = farmIPService;
    }

    public Page<FarmDTO> list(Pageable pageable) {
        Page<Farm> farmPage = farmRepository.findAllWithLocationAndFarmType(pageable);

        return new PageImpl<>(toDTO(farmPage.getContent()), pageable, farmPage.getTotalElements());
    }

    public FarmDTO listByName(String farmName) {
        Farm farm = farmRepository.findByNameFullFetch(farmName);

        if (farm == null) {
            throw new ObjectNotFoundException("Farm '" + farmName + "' not found");
        }

        return toDTO(farm);
    }

    private List<FarmDTO> toDTO(List<Farm> farmList) {
        List<FarmDTO> farmDTOList = new ArrayList<>();

        for (Farm farm : farmList) {
            farmDTOList.add(toDTO(farm));
        }

        return farmDTOList;
    }

    private FarmDTO toDTO(Farm farm) {
        PersistenceUtil persistenceUtil = Persistence.getPersistenceUtil();

        FarmDTO farmDTO = new FarmDTO();
        farmDTO.setName(farm.getName());
        farmDTO.setCapacity(farm.getCapacity());

        if (persistenceUtil.isLoaded(farm.getLocation())) {
            farmDTO.setLocationName(farm.getLocation().getName());
        }

        if (persistenceUtil.isLoaded(farm.getFarmType())) {
            farmDTO.setFarmtypeName(farm.getFarmType().getName());
        }

        if (persistenceUtil.isLoaded(farm.getFarmIPList())) {
            setFarmIPListToDTO(farmDTO, farm.getFarmIPList());
        }

        return farmDTO;
    }

    private void setFarmIPListToDTO(FarmDTO farmDTO, Set<FarmIP> farmIPList) {
        List<FarmIPDTO> farmIPDTOList = new ArrayList<>();

        for (FarmIP farmIP : farmIPList) {
            FarmIPDTO farmIPDTO = new FarmIPDTO();
            farmIPDTO.setIp(farmIP.getIp().getAddress());
            farmIPDTO.setFarmIpTypeName(farmIP.getFarmipType().getName());
            setBrandListToDTO(farmIPDTO, farmIP.getBrandFarmIPList());
            farmIPDTOList.add(farmIPDTO);
        }

        farmDTO.setFarmipList(farmIPDTOList);
    }

    private void setBrandListToDTO(FarmIPDTO farmIPDTO, Set<BrandFarmIP> brandFarmIPList) {
        List<BrandDTO> brandDTOList = new ArrayList<>();

        for (BrandFarmIP brandFarmIP : brandFarmIPList) {
            BrandDTO brandDTO = new BrandDTO();
            brandDTO.setName(brandFarmIP.getBrand().getName());
            brandDTOList.add(brandDTO);
        }

        farmIPDTO.setBrandList(brandDTOList);
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
        Farm farm = farmRepository.findByNameFullFetch(farmName);

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
    }

    public void addIP(FarmIPDTO farmIPDTO, String farmName) {
        Farm farm = farmRepository.findByName(farmName);

        if (farm == null) {
            throw new ObjectNotFoundException("Farm '" + farmName + "' not found");
        }

        farmIPService.create(farmIPDTO, farm);
    }

    public void removeIP(String ipAddress) {
        farmIPService.delete(ipAddress);
    }

    public void addIPNet(IPNetDTO ipNetDTO, String farmName) {
        Farm farm = farmRepository.findByName(farmName);

        if (farm == null) {
            throw new ObjectNotFoundException("Farm '" + farmName + "' not found");
        }

        IPNet ipNet = ipNetRepository.findByNet(new Inet(ipNetDTO.getNetAddress()));

        if (ipNet == null) {
            throw new ObjectNotFoundException("IPNet '" + ipNetDTO.getNetAddress() + "' not found");
        }

        farmIPNetRepository.save(new FarmIPNet(farm, ipNet));
    }

    public void removeIPNet(String netAddress, String farmName) {
        Farm farm = farmRepository.findByName(farmName);

        if (farm == null) {
            throw new ObjectNotFoundException("Farm '" + farmName + "' not found");
        }

        IPNet ipNet = ipNetRepository.findByNet(new Inet(netAddress));

        if (ipNet == null) {
            throw new ObjectNotFoundException("IPNet '" + netAddress + "' not found");
        }

        farmIPNetRepository.deleteByFarmAndIPNet(farm, ipNet);
    }
}
