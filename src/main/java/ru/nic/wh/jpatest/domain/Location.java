package ru.nic.wh.jpatest.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_gen")
    @SequenceGenerator(name = "location_gen", sequenceName = "location_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    protected Location() {
    }

    public Location(String name) {
        this.name = name;
    }
}
