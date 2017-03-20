package ru.nic.wh.jpatest.domain;

import lombok.Data;
import org.hibernate.annotations.Type;
import ru.nic.wh.jpatest.miscellaneous.usertype.Inet;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "farmip")
public class FarmIP {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "farmip_gen")
    @SequenceGenerator(name = "farmip_gen", sequenceName = "farmip_id_seq", allocationSize = 1)
    private Long id;

    @Type(type = "ru.nic.wh.jpatest.miscellaneous.usertype.InetType")
    @Column(name = "ip")
    private Inet ip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmip_type_id")
    private FarmIPType farmipType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @OneToMany(mappedBy = "farmIP", cascade = CascadeType.PERSIST)
    private Set<BrandFarmIP> brandFarmIPList = new HashSet<>();

    protected FarmIP() {
    }

    public FarmIP(String ip, FarmIPType farmipType, Farm farm) {
        this.ip = new Inet(ip);
        this.farmipType = farmipType;
        this.farm = farm;
    }

    public void addBrand(Brand brand) {
        BrandFarmIP brandFarmIP = new BrandFarmIP(brand, this);
        brandFarmIPList.add(brandFarmIP);
    }

    @Override
    public String toString() {
        return "FarmIP{id=" + id + ", ip=" + ip.getAddress() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FarmIP farmIP = (FarmIP) o;

        if (!id.equals(farmIP.id)) return false;
        return ip.equals(farmIP.ip);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + ip.hashCode();
        return result;
    }
}
