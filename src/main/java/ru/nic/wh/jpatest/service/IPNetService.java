package ru.nic.wh.jpatest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nic.wh.jpatest.domain.Brand;
import ru.nic.wh.jpatest.domain.IPNet;
import ru.nic.wh.jpatest.domain.IPNetType;
import ru.nic.wh.jpatest.dto.BrandDTO;
import ru.nic.wh.jpatest.dto.IPNetDTO;
import ru.nic.wh.jpatest.exception.ObjectNotFoundException;
import ru.nic.wh.jpatest.miscellaneous.usertype.Inet;
import ru.nic.wh.jpatest.repository.BrandRepository;
import ru.nic.wh.jpatest.repository.IPNetRepository;
import ru.nic.wh.jpatest.repository.IPNetTypeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class IPNetService {

	private final IPNetRepository ipNetRepository;
	private final IPNetTypeRepository ipNetTypeRepository;
	private final BrandRepository brandRepository;

	@Autowired
	public IPNetService(IPNetRepository ipNetRepository, IPNetTypeRepository ipNetTypeRepository,
						BrandRepository brandRepository) {

		this.ipNetRepository = ipNetRepository;
		this.ipNetTypeRepository = ipNetTypeRepository;
		this.brandRepository = brandRepository;
	}

	public void create(IPNetDTO ipNetDTO) {
		IPNetType ipNetType = ipNetTypeRepository.findByName(ipNetDTO.getIpnetTypeName());

		if (ipNetType == null) {
			throw new ObjectNotFoundException("IPNetType '" + ipNetDTO.getIpnetTypeName() + "' not found");
		}

		IPNet ipNet = new IPNet(ipNetDTO.getNetAddress(), ipNetType);

		if (ipNetDTO.getBrandList() != null) {
			setBrand(ipNet, ipNetDTO.getBrandList());
		}

		ipNetRepository.save(ipNet);
	}

	private void setBrand(IPNet ipNet, List<BrandDTO> brandDTOList) {
		List<Brand> brandList = new ArrayList<>();

		for (BrandDTO brandDTO : brandDTOList) {
			Brand brand = brandRepository.findByName(brandDTO.getName());

			if (brand == null) {
				throw new ObjectNotFoundException("Brand '" + brandDTO.getName() + "' not found");
			}

			brandList.add(brand);
		}

		ipNet.setBrandList(brandList);
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

		ipNetRepository.save(ipNet);
	}
}
