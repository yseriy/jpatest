package ru.nic.wh.jpatest.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "brand_farmip")
public class BrandFarmIP {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_farmip_gen")
    @SequenceGenerator(name = "brand_farmip_gen", sequenceName = "brand_farmip_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    private FarmIP farmIP;

    protected BrandFarmIP() {
    }

    BrandFarmIP(Brand brand, FarmIP farmIP) {
        this.brand = brand;
        this.farmIP = farmIP;
    }
}
