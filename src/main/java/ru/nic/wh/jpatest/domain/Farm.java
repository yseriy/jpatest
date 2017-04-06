package ru.nic.wh.jpatest.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
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
        return "Farm{id=" + id + ", name='" + name + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Farm farm = (Farm) o;

        if (!id.equals(farm.id)) return false;
        return name.equals(farm.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
