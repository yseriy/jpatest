package ru.nic.wh.jpatest.domain;

import lombok.Data;
import org.hibernate.annotations.Type;
import ru.nic.wh.jpatest.miscellaneous.usertype.Inet;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "ipnet")
public class IPNet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ipnet_gen")
    @SequenceGenerator(name = "ipnet_gen", sequenceName = "ipnet_id_seq", allocationSize = 1)
    private Long id;

    @Type(type = "ru.nic.wh.jpatest.miscellaneous.usertype.InetType")
    @Column(name = "net")
    private Inet net;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ipnet_type_id")
    private IPNetType ipNetType;

    @OneToMany(mappedBy = "ipNet", cascade = CascadeType.PERSIST)
    private List<BrandIPNet> brandIPNetList = new ArrayList<>();

    protected IPNet() {
    }

    public IPNet(String netAddress, IPNetType ipNetType) {
        this.net = new Inet(netAddress);
        this.ipNetType = ipNetType;
    }

    public void addBrand(Brand brand) {
        BrandIPNet brandIPNet = new BrandIPNet(brand, this);
        brandIPNetList.add(brandIPNet);
    }
}
