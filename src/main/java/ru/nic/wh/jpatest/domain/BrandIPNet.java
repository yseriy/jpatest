package ru.nic.wh.jpatest.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
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

    public BrandIPNet(Brand brand, IPNet ipNet) {
        this.brand = brand;
        this.ipNet = ipNet;
    }

    @Override
    public String toString() {
        System.out.println("P31");
        return "BrandIPNet{" + "id=" + id + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BrandIPNet that = (BrandIPNet) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
