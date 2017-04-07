package ru.nic.wh.jpatest.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ipnet_type")
public class IPNetType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ipnet_type_gen")
    @SequenceGenerator(name = "ipnet_type_gen", sequenceName = "ipnet_type_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    private Boolean required;

    protected IPNetType() {
    }

    public IPNetType(String name, Boolean required) {
        this.name = name;
        this.required = required;
    }

    @Override
    public String toString() {
        return "IPNetType{" + "name='" + name + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IPNetType ipNetType = (IPNetType) o;

        return name.equals(ipNetType.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
