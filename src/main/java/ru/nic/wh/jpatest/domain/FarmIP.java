package ru.nic.wh.jpatest.domain;

import org.hibernate.annotations.Type;
import ru.nic.wh.jpatest.repository.usertype.Inet;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "farmip")
public class FarmIP {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "farmip_gen")
	@SequenceGenerator(name = "farmip_gen", sequenceName = "farmip_id_seq", allocationSize = 1)
	private Long id;

	@Type(type = "ru.nic.wh.jpatest.repository.usertype.InetType")
	@Column(name = "ip")
	private Inet ip;

	@ManyToOne
	@JoinColumn(name = "farmip_type_id", foreignKey = @ForeignKey(name = "farmip_farmip_type_id_fkey"))
	private FarmIPType farmipType;

	@ManyToOne
	@JoinColumn(name = "farm_id", foreignKey = @ForeignKey(name = "farmip_farm_id_fkey"))
	private Farm farm;

	@ManyToMany
	@JoinTable(name = "brand_farmip_link", joinColumns = @JoinColumn(name = "farmip_id"),
			inverseJoinColumns = @JoinColumn(name = "brand_id"))
	private List<Brand> brandList;

	protected FarmIP() {
	}

	public FarmIP(String ip, FarmIPType farmIPType) {
		this.ip = new Inet(ip);
		this.farmipType = farmIPType;
		this.brandList = new ArrayList<>();
	}

	public FarmIP(String ip, FarmIPType farmipType, Farm farm) {
		this(ip, farmipType);
		this.farm = farm;
	}

	public void addBrand(Brand brand) {
		this.brandList.add(brand);
	}

	public Long getId() {
		return id;
	}

	public Inet getIp() {
		return ip;
	}

	public void setIp(Inet ip) {
		this.ip = ip;
	}

	public FarmIPType getFarmipType() {
		return farmipType;
	}

	public void setFarmipType(FarmIPType farmipType) {
		this.farmipType = farmipType;
	}

	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}

	public List<Brand> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<Brand> brandList) {
		this.brandList = brandList;
	}
}
