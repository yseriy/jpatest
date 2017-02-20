package ru.nic.wh.jpatest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}
}
