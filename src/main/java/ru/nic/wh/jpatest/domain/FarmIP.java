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

	@ManyToOne
	@JoinColumn(name = "farmip_type_id", foreignKey = @ForeignKey(name = "farmip_farmip_type_id_fkey"))
	private FarmIPType farmipType;

	@Type(type = "ru.nic.wh.jpatest.repository.usertype.InetType")
	@Column(name = "ip")
	private Inet ip;

	@ManyToMany
	@JoinTable(name = "brand_farmip_link", joinColumns = @JoinColumn(name = "farmip_id"),
			inverseJoinColumns = @JoinColumn(name = "brand_id"))
	private List<Brand> brandList;

	protected FarmIP() {
	}

	public FarmIP(String ip, FarmIPType farmipType) {
		this.ip = new Inet(ip);
		this.farmipType = farmipType;
		this.brandList = new ArrayList<>();
	}

	public void addBrand(Brand brand) {
		this.brandList.add(brand);
	}

	public Long getId() {
		return id;
	}

	public FarmIPType getFarmipType() {
		return farmipType;
	}

	public void setFarmipType(FarmIPType farmipType) {
		this.farmipType = farmipType;
	}

	public Inet getIp() {
		return ip;
	}

	public void setIp(Inet ip) {
		this.ip = ip;
	}

	public List<Brand> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<Brand> brandList) {
		this.brandList = brandList;
	}
}
