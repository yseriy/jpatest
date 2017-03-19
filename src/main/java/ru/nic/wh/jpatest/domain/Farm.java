package ru.nic.wh.jpatest.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
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

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
    private List<FarmIP> farmIPList;

    protected Farm() {
    }

    public Farm(String name, Integer capacity, Location location, FarmType farmType) {
        this.name = name;
        this.capacity = capacity;
        this.location = location;
        this.farmType = farmType;
    }
}
