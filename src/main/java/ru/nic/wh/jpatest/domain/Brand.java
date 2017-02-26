package ru.nic.wh.jpatest.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "brand")
public class Brand {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_gen")
	@SequenceGenerator(name = "brand_gen", sequenceName = "brand_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "name")
	private String name;

	protected Brand() {
	}

	public Brand(String name) {
		this.name = name;
	}
}
