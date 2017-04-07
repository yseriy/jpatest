package ru.nic.wh.jpatest.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "farm")
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "farm_gen")
    @SequenceGenerator(name = "farm_gen", sequenceName = "farm_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    private Integer capacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_type_id")
    private FarmType farmType;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.PERSIST)
    private Set<FarmIP> farmIPList = new HashSet<>();

    protected Farm() {
    }

    public Farm(String name, Integer capacity, Location location, FarmType farmType) {
        this.name = name;
        this.capacity = capacity;
        this.location = location;
        this.farmType = farmType;
    }

    @Override
    public String toString() {
        return "Farm{" + "name='" + name + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Farm farm = (Farm) o;

        return name.equals(farm.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
