package ru.nic.wh.jpatest.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "brand_ipnet")
public class BrandIPNet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_ipnet_gen")
    @SequenceGenerator(name = "brand_ipnet_gen", sequenceName = "brand_ipnet_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ipnet_id")
    private IPNet ipNet;

    protected BrandIPNet() {
    }

    BrandIPNet(Brand brand, IPNet ipNet) {
        this.brand = brand;
        this.ipNet = ipNet;
    }
}
