package ru.nic.wh.jpatest.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import ru.nic.wh.jpatest.miscellaneous.usertype.Inet;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "ipnet")
public class IPNet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ipnet_gen")
    @SequenceGenerator(name = "ipnet_gen", sequenceName = "ipnet_id_seq", allocationSize = 1)
    private Long id;

    @Type(type = "ru.nic.wh.jpatest.miscellaneous.usertype.InetType")
    private Inet net;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ipnet_type_id")
    private IPNetType ipNetType;

    @OneToMany(mappedBy = "ipNet", cascade = CascadeType.ALL)
    private Set<BrandIPNet> brandIPNetList = new HashSet<>();

    protected IPNet() {
    }

    public IPNet(String netAddress, IPNetType ipNetType) {
        this.net = new Inet(netAddress);
        this.ipNetType = ipNetType;
    }

    @Override
    public String toString() {
        return "IPNet{" + "net=" + net + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IPNet ipNet = (IPNet) o;

        return net.equals(ipNet.net);
    }

    @Override
    public int hashCode() {
        return net.hashCode();
    }
}
