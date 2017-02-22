package ru.nic.wh.jpatest.domain;

import javax.persistence.*;

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

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
