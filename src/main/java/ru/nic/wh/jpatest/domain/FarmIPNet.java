package ru.nic.wh.jpatest.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "farm_ipnet")
public class FarmIPNet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "farm_ipnet_gen")
    @SequenceGenerator(name = "farm_ipnet_gen", sequenceName = "farm_ipnet_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Farm farm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ipnet_id")
    private IPNet ipNet;

    protected FarmIPNet() {
    }

    public FarmIPNet(Farm farm, IPNet ipNet) {
        this.farm = farm;
        this.ipNet = ipNet;
    }

    @Override
    public String toString() {
        return "FarmIPNet{" + "id=" + id + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FarmIPNet farmIPNet = (FarmIPNet) o;

        return id != null ? id.equals(farmIPNet.id) : farmIPNet.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
