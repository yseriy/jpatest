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
import ru.nic.wh.jpatest.repository.BrandFarmIPRepository;
import ru.nic.wh.jpatest.repository.BrandRepository;
import ru.nic.wh.jpatest.repository.FarmIPRepository;
import ru.nic.wh.jpatest.repository.FarmIPTypeRepository;

import java.util.List;

@Service
@Transactional
public class FarmIPService {

    private final FarmIPRepository farmipRepository;
    private final FarmIPTypeRepository farmIPTypeRepository;
    private final BrandFarmIPRepository brandFarmIPRepository;
    private final BrandRepository brandRepository;

    public FarmIPService(FarmIPRepository farmipRepository, FarmIPTypeRepository farmIPTypeRepository,
                         BrandFarmIPRepository brandFarmIPRepository, BrandRepository brandRepository) {

        this.farmipRepository = farmipRepository;
        this.farmIPTypeRepository = farmIPTypeRepository;
        this.brandFarmIPRepository = brandFarmIPRepository;
        this.brandRepository = brandRepository;
    }

//    public List<FarmIPDTO> listByFarmName(String farmName) {
//        PersistenceUtil persistenceUtil = Persistence.getPersistenceUtil();
//        Farm farm = farmRepository.findByName(farmName);
//
//        if (farm == null) {
//            throw new ObjectNotFoundException("Farm '" + farmName + "' not found");
//        }
//
//        List<FarmIPDTO> farmIPDTOList = new ArrayList<>();
//        List<FarmIP> farmIPList = farmipRepository.findWithFarmIPTypeByFarmId(farm.getId());
//
//        for (FarmIP farmIP : farmIPList) {
//            FarmIPDTO farmIPDTO = new FarmIPDTO();
//            farmIPDTO.setIp(farmIP.getIp().getAddress());
//
//            if (persistenceUtil.isLoaded(farmIP.getFarmipType()) && farmIP.getFarmipType() != null) {
//                farmIPDTO.setFarmIpTypeName(farmIP.getFarmipType().getName());
//            }
//
////            if (persistenceUtil.isLoaded(farmIP.getBrandList()) && farmIP.getBrandList() != null) {
////                farmIPDTO.setBrandList(setBrandDTOList(farmIP.getBrandList()));
////            }
//
//            farmIPDTOList.add(farmIPDTO);
//        }
//
//        return farmIPDTOList;
//    }
//
//    private List<BrandDTO> setBrandDTOList(List<Brand> brandList) {
//        List<BrandDTO> brandDTOList = new ArrayList<>();
//
//        for (Brand brand : brandList) {
//            BrandDTO brandDTO = new BrandDTO();
//            brandDTO.setName(brand.getName());
//            brandDTOList.add(brandDTO);
//        }
//
//        return brandDTOList;
//    }

    void create(FarmIPDTO farmIPDTO, Farm farm) {
        FarmIPType farmIPType = farmIPTypeRepository.findByName(farmIPDTO.getFarmIpTypeName());

        if (farmIPType == null) {
            throw new ObjectNotFoundException("FarmType '" + farmIPDTO.getFarmIpTypeName() + "' not found");
        }

        FarmIP farmIP = new FarmIP(farmIPDTO.getIp(), farmIPType, farm);
        farmipRepository.save(farmIP);
        setBrand(farmIP, farmIPDTO.getBrandList());
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

    void update(FarmIPDTO farmIPDTO, String ipAddress) {
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
    }

    void delete(String ipAddress) {
        FarmIP farmIP = farmipRepository.findByIp(new Inet(ipAddress));

        if (farmIP == null) {
            throw new ObjectNotFoundException("FarmIP '" + ipAddress + "' not found");
        }

        brandFarmIPRepository.deleteBrandFarmIPLink(farmIP);
        farmipRepository.delete(farmIP);
    }
}
