package ru.nic.wh.jpatest.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "farmip_type")
public class FarmIPType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "farmip_type_gen")
    @SequenceGenerator(name = "farmip_type_gen", sequenceName = "farmip_type_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    private Boolean required;

    protected FarmIPType() {
    }

    public FarmIPType(String name, Boolean required) {
        this.name = name;
        this.required = required;
    }

    @Override
    public String toString() {
        return "FarmIPType{" + "name='" + name + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FarmIPType that = (FarmIPType) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
