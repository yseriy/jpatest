package ru.nic.wh.jpatest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nic.wh.jpatest.domain.Brand;
import ru.nic.wh.jpatest.domain.Farm;
import ru.nic.wh.jpatest.domain.FarmIP;
import ru.nic.wh.jpatest.domain.FarmIPType;
import ru.nic.wh.jpatest.dto.BrandDTO;
import ru.nic.wh.jpatest.dto.FarmIPDTO;
import ru.nic.wh.jpatest.exception.ObjectNotFoundException;
import ru.nic.wh.jpatest.miscellaneous.usertype.Inet;
import ru.nic.wh.jpatest.repository.BrandRepository;
import ru.nic.wh.jpatest.repository.FarmIPRepository;
import ru.nic.wh.jpatest.repository.FarmIPTypeRepository;
import ru.nic.wh.jpatest.repository.FarmRepository;

import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FarmIPService {

    private final FarmIPRepository farmipRepository;
    private final FarmIPTypeRepository farmIPTypeRepository;
    private final FarmRepository farmRepository;
    private final BrandRepository brandRepository;

    public FarmIPService(FarmIPRepository farmipRepository, FarmIPTypeRepository farmIPTypeRepository,
                         FarmRepository farmRepository, BrandRepository brandRepository) {

        this.farmipRepository = farmipRepository;
        this.farmIPTypeRepository = farmIPTypeRepository;
        this.farmRepository = farmRepository;
        this.brandRepository = brandRepository;
    }

    public List<FarmIPDTO> listByFarmName(String farmName) {
        PersistenceUtil persistenceUtil = Persistence.getPersistenceUtil();
        Farm farm = farmRepository.findByName(farmName);

        if (farm == null) {
            throw new ObjectNotFoundException("Farm '" + farmName + "' not found");
        }

        List<FarmIPDTO> farmIPDTOList = new ArrayList<>();
        List<FarmIP> farmIPList = farmipRepository.findWithFarmIPTypeByFarmId(farm.getId());

        for (FarmIP farmIP : farmIPList) {
            FarmIPDTO farmIPDTO = new FarmIPDTO();
            farmIPDTO.setIp(farmIP.getIp().getAddress());

            if (persistenceUtil.isLoaded(farmIP.getFarmipType()) && farmIP.getFarmipType() != null) {
                farmIPDTO.setFarmIpTypeName(farmIP.getFarmipType().getName());
            }

            if (persistenceUtil.isLoaded(farmIP.getBrandList()) && farmIP.getBrandList() != null) {
                farmIPDTO.setBrandList(setBrandDTOList(farmIP.getBrandList()));
            }

            farmIPDTOList.add(farmIPDTO);
        }

        return farmIPDTOList;
    }

    private List<BrandDTO> setBrandDTOList(List<Brand> brandList) {
        List<BrandDTO> brandDTOList = new ArrayList<>();

        for (Brand brand : brandList) {
            BrandDTO brandDTO = new BrandDTO();
            brandDTO.setName(brand.getName());
            brandDTOList.add(brandDTO);
        }

        return brandDTOList;
    }

    public void create(FarmIPDTO farmIPDTO, String farmName) {
        Farm farm = farmRepository.findByName(farmName);

        if (farm == null) {
            throw new ObjectNotFoundException("Farm '" + farmName + "' not found");
        }

        create(farmIPDTO, farm);
    }

    void create(FarmIPDTO farmIPDTO, Farm farm) {

        FarmIPType farmIPType = farmIPTypeRepository.findByName(farmIPDTO.getFarmIpTypeName());

        if (farmIPType == null) {
            throw new ObjectNotFoundException("FarmType '" + farmIPDTO.getFarmIpTypeName() + "' not found");
        }

        FarmIP farmIP = new FarmIP(farmIPDTO.getIp(), farmIPType, farm);
        setBrand(farmIP, farmIPDTO.getBrandList());

        farmipRepository.save(farmIP);
    }

    private void setBrand(FarmIP farmIP, List<BrandDTO> brandDTOList) {
        for (BrandDTO brandDTO : brandDTOList) {
            Brand brand = brandRepository.findByName(brandDTO.getName());

            if (brand == null) {
                throw new ObjectNotFoundException("Brand '" + brandDTO.getName() + "' not found");
            }

            farmIP.addBrand(brand);
        }
    }

    public void update(FarmIPDTO farmIPDTO, String ipAddress) {
        FarmIP farmIP = farmipRepository.findByIp(new Inet(ipAddress));

        if (farmIP == null) {
            throw new ObjectNotFoundException("FarmIP '" + ipAddress + "' not found");
        }

        if (farmIPDTO.getFarmIpTypeName() != null) {
            FarmIPType farmIPType = farmIPTypeRepository.findByName(farmIPDTO.getFarmIpTypeName());

            if (farmIPType == null) {
                throw new ObjectNotFoundException("FarmType '" + farmIPDTO.getFarmIpTypeName() + "' not found");
            }

            farmIP.setFarmipType(farmIPType);
        }

        if (farmIPDTO.getIp() != null) {
            farmIP.setIp(new Inet(farmIPDTO.getIp()));
        }

        farmipRepository.save(farmIP);
    }

    public void delete(String ipAddress) {
        FarmIP farmIP = farmipRepository.findByIp(new Inet(ipAddress));

        if (farmIP == null) {
            throw new ObjectNotFoundException("FarmIP '" + ipAddress + "' not found");
        }

        farmipRepository.delete(farmIP);
    }
}
