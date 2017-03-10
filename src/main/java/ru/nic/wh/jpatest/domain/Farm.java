package ru.nic.wh.jpatest.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "farm")
public class Farm {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "farm_gen")
	@SequenceGenerator(name = "farm_gen", sequenceName = "farm_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "capacity")
	private Integer capacity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id", foreignKey = @ForeignKey(name = "farm_location_id_fkey"))
	private Location location;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "farm_type_id", foreignKey = @ForeignKey(name = "farm_farm_type_id_fkey"))
	private FarmType farmType;

	@OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<FarmIP> farmIPSet;

	@ManyToMany
	@JoinTable(name = "farm_ipnet_link", inverseJoinColumns = @JoinColumn(name = "ipnet_id"))
	private Set<IPNet> ipNetSet;

	protected Farm() {
	}

	public Farm(String name, Integer capacity, Location location, FarmType farmType) {
		this.name = name;
		this.capacity = capacity;
		this.location = location;
		this.farmType = farmType;
	}
}
