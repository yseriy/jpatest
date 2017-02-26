package ru.nic.wh.jpatest.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "farmip_type")
public class FarmIPType {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "farmip_type_gen")
	@SequenceGenerator(name = "farmip_type_gen", sequenceName = "farmip_type_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "required")
	private Boolean required;

	protected FarmIPType() {
	}

	public FarmIPType(String name, Boolean required) {
		this.name = name;
		this.required = required;
	}
}
