package ru.nic.wh.jpatest.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "farm_type")
public class FarmType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "farm_type_gen")
    @SequenceGenerator(name = "farm_type_gen", sequenceName = "farm_type_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    protected FarmType() {
    }

    public FarmType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FarmType{" + "name='" + name + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FarmType farmType = (FarmType) o;

        return name.equals(farmType.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
