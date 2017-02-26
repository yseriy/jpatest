package ru.nic.wh.jpatest.domain;

import lombok.Data;
import org.hibernate.annotations.Type;
import ru.nic.wh.jpatest.miscellaneous.usertype.Inet;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "farmip")
public class FarmIP {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "farmip_gen")
	@SequenceGenerator(name = "farmip_gen", sequenceName = "farmip_id_seq", allocationSize = 1)
	private Long id;

	@Type(type = "ru.nic.wh.jpatest.miscellaneous.usertype.InetType")
	@Column(name = "ip")
	private Inet ip;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "farmip_type_id", foreignKey = @ForeignKey(name = "farmip_farmip_type_id_fkey"))
	private FarmIPType farmipType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "farm_id", foreignKey = @ForeignKey(name = "farmip_farm_id_fkey"))
	private Farm farm;

	@ManyToMany
	@JoinTable(name = "brand_farmip_link", joinColumns = @JoinColumn(name = "farmip_id"),
			inverseJoinColumns = @JoinColumn(name = "brand_id"))
	private List<Brand> brandList;

	protected FarmIP() {
	}

	private FarmIP(String ip, FarmIPType farmIPType) {
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
}
