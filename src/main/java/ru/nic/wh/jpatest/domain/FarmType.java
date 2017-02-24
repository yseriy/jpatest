package ru.nic.wh.jpatest.domain;

import javax.persistence.*;

@Entity
@Table(name = "farm_type")
public class FarmType {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "farm_type_gen")
	@SequenceGenerator(name = "farm_type_gen", sequenceName = "farm_type_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "name")
	private String name;

	protected FarmType() {
	}

	public FarmType(String name) {
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
