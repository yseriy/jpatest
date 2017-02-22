package ru.nic.wh.jpatest.domain;

import javax.persistence.*;
import java.util.List;

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

	@ManyToOne
	@JoinColumn(name = "location_id", foreignKey = @ForeignKey(name = "farm_location_id_fkey"))
	private Location location;

	@ManyToOne
	@JoinColumn(name = "farm_type_id", foreignKey = @ForeignKey(name = "farm_farm_type_id_fkey"))
	private FarmType farmType;

	@OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FarmIP> farmIP;

	protected Farm() {
	}

	public Farm(String name, Integer capacity, Location location, FarmType farmType) {
		this.name = name;
		this.capacity = capacity;
		this.location = location;
		this.farmType = farmType;
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

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public FarmType getFarmType() {
		return farmType;
	}

	public void setFarmType(FarmType farmType) {
		this.farmType = farmType;
	}

	public List<FarmIP> getFarmIP() {
		return farmIP;
	}

	public void setFarmIP(List<FarmIP> farmIP) {
		this.farmIP = farmIP;
	}
}
