package ru.nic.wh.jpatest.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nic.wh.jpatest.domain.Brand;
import ru.nic.wh.jpatest.domain.IPNet;
import ru.nic.wh.jpatest.domain.IPNetType;
import ru.nic.wh.jpatest.dto.BrandDTO;
import ru.nic.wh.jpatest.miscellaneous.usertype.Inet;
import ru.nic.wh.jpatest.repository.BrandRepository;
import ru.nic.wh.jpatest.repository.IPNetRepository;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IPNetServiceTest {

    @MockBean
    private IPNetRepository mockIPNetRepository;

    @MockBean
    private BrandRepository mockBrandRepository;

    @Autowired
    private IPNetService ipNetService;

    @Test
    public void testAddBrand() {
        Inet inet = new Inet("192.168.20.0");
        IPNetType ipNetType = new IPNetType("ipnettype1", true);
        IPNet ipNet = new IPNet("192.168.20.0", ipNetType);
        Brand brand = new Brand("brand1");
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setName("brand1");

        when(mockIPNetRepository.findByNet(inet)).thenReturn(ipNet);
        when(mockIPNetRepository.findByNet(not(eq(inet)))).thenReturn(null);

        when(mockBrandRepository.findByName("brand1")).thenReturn(brand);
        when(mockBrandRepository.findByName(not(eq("brand1")))).thenReturn(null);


        ipNetService.addBrand(brandDTO, "192.168.20.0");

        assertThat(ipNet.getBrandList(), hasSize(1));
        assertThat(ipNet.getBrandList(), hasItems(brand));
    }
}