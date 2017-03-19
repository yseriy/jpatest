package ru.nic.wh.jpatest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nic.wh.jpatest.domain.Brand;
import ru.nic.wh.jpatest.domain.BrandIPNet;
import ru.nic.wh.jpatest.domain.IPNet;
import ru.nic.wh.jpatest.domain.IPNetType;
import ru.nic.wh.jpatest.dto.BrandDTO;
import ru.nic.wh.jpatest.dto.IPNetDTO;
import ru.nic.wh.jpatest.exception.ObjectNotFoundException;
import ru.nic.wh.jpatest.miscellaneous.usertype.Inet;
import ru.nic.wh.jpatest.repository.BrandIPNetRepository;
import ru.nic.wh.jpatest.repository.BrandRepository;
import ru.nic.wh.jpatest.repository.IPNetRepository;
import ru.nic.wh.jpatest.repository.IPNetTypeRepository;

import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class IPNetService {

    private final IPNetRepository ipNetRepository;
    private final IPNetTypeRepository ipNetTypeRepository;
    private final BrandRepository brandRepository;
    private final BrandIPNetRepository brandIPNetRepository;

    public IPNetService(IPNetRepository ipNetRepository, IPNetTypeRepository ipNetTypeRepository,
                        BrandRepository brandRepository, BrandIPNetRepository brandIPNetRepository) {

        this.ipNetRepository = ipNetRepository;
        this.ipNetTypeRepository = ipNetTypeRepository;
        this.brandRepository = brandRepository;
        this.brandIPNetRepository = brandIPNetRepository;
    }

    public Page<IPNetDTO> listAll(Pageable pageable) {
        Page<IPNet> ipNetPage = ipNetRepository.findAllWithType(pageable);

        return new PageImpl<>(toDTO(ipNetPage.getContent()), pageable, ipNetPage.getTotalElements());
    }

    public IPNetDTO listByName(String netAddress) {
        IPNet ipNet = ipNetRepository.findByNetWithTypeAndBrand(new Inet(netAddress));

        if (ipNet == null) {
            throw new ObjectNotFoundException("IPNet '" + netAddress + "' not found");
        }

        return toDTO(ipNet);
    }

    private List<IPNetDTO> toDTO(List<IPNet> ipNetList) {
        List<IPNetDTO> ipNetDTOList = new ArrayList<>();

        for (IPNet ipNet : ipNetList) {
            ipNetDTOList.add(toDTO(ipNet));
        }

        return ipNetDTOList;
    }

    private IPNetDTO toDTO(IPNet ipNet) {
        PersistenceUtil persistenceUtil = Persistence.getPersistenceUtil();

        IPNetDTO ipNetDTO = new IPNetDTO();
        ipNetDTO.setNetAddress(ipNet.getNet().getAddress());

        if (persistenceUtil.isLoaded(ipNet.getIpNetType())) {
            ipNetDTO.setIpnetTypeName(ipNet.getIpNetType().getName());
        }

        if (persistenceUtil.isLoaded(ipNet.getBrandIPNetList())) {
            setBrandListToDTO(ipNetDTO, ipNet.getBrandIPNetList());
        }

        return ipNetDTO;
    }

    private void setBrandListToDTO(IPNetDTO ipNetDTO, List<BrandIPNet> brandIPNetList) {
        List<BrandDTO> brandDTOList = new ArrayList<>();

        for (BrandIPNet brandIPNet : brandIPNetList) {
            BrandDTO brandDTO = new BrandDTO();
            brandDTO.setName(brandIPNet.getBrand().getName());
            brandDTOList.add(brandDTO);
        }

        ipNetDTO.setBrandList(brandDTOList);
    }

    public void create(IPNetDTO ipNetDTO) {
        IPNetType ipNetType = ipNetTypeRepository.findByName(ipNetDTO.getIpnetTypeName());

        if (ipNetType == null) {
            throw new ObjectNotFoundException("IPNetType '" + ipNetDTO.getIpnetTypeName() + "' not found");
        }

        IPNet ipNet = new IPNet(ipNetDTO.getNetAddress(), ipNetType);
        ipNetRepository.save(ipNet);

        if (ipNetDTO.getBrandList() != null) {
            getBrandListFromDTO(ipNet, ipNetDTO.getBrandList());
        }
    }

    private void getBrandListFromDTO(IPNet ipNet, List<BrandDTO> brandDTOList) {
        for (BrandDTO brandDTO : brandDTOList) {
            Brand brand = brandRepository.findByName(brandDTO.getName());

            if (brand == null) {
                throw new ObjectNotFoundException("Brand '" + brandDTO.getName() + "' not found");
            }

            ipNet.addBrand(brand);
        }
    }

    public void update(IPNetDTO ipNetDTO, String netAddress) {
        IPNet ipNet = ipNetRepository.findByNet(new Inet(netAddress));

        if (ipNet == null) {
            throw new ObjectNotFoundException("IPNet '" + netAddress + "' not found");
        }

        if (ipNetDTO.getNetAddress() != null) {
            ipNet.setNet(new Inet(ipNetDTO.getNetAddress()));
        }

        if (ipNetDTO.getIpnetTypeName() != null) {
            IPNetType ipNetType = ipNetTypeRepository.findByName(ipNetDTO.getIpnetTypeName());

            if (ipNetType == null) {
                throw new ObjectNotFoundException("IPNetType '" + ipNetDTO.getIpnetTypeName() + "' not found");
            }

            ipNet.setIpNetType(ipNetType);
        }
    }

    public void addBrand(BrandDTO brandDTO, String netAddress) {
        IPNet ipNet = ipNetRepository.findByNet(new Inet(netAddress));

        if (ipNet == null) {
            throw new ObjectNotFoundException("IPNet '" + netAddress + "' not found");
        }

        Brand brand = brandRepository.findByName(brandDTO.getName());

        if (brand == null) {
            throw new ObjectNotFoundException("Brand '" + brandDTO.getName() + "' not found");
        }

        ipNet.addBrand(brand);
    }

    public void removeBrand(String netAddress, String brandName) {
        IPNet ipNet = ipNetRepository.findByNet(new Inet(netAddress));

        if (ipNet == null) {
            throw new ObjectNotFoundException("IPNet '" + netAddress + "' not found");
        }

        Brand brand = brandRepository.findByName(brandName);

        if (brand == null) {
            throw new ObjectNotFoundException("Brand '" + brandName + "' not found");
        }

        brandIPNetRepository.DeleteBrandIPNetLink(brand, ipNet);
    }
}
