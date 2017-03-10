package ru.nic.wh.jpatest.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ipnet_type")
public class IPNetType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ipnet_type_gen")
    @SequenceGenerator(name = "ipnet_type_gen", sequenceName = "ipnet_type_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "required")
    private Boolean required;

    protected IPNetType() {
    }

    public IPNetType(String name, Boolean required) {
        this.name = name;
        this.required = required;
    }
}
