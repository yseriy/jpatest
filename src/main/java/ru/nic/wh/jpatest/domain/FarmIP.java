package ru.nic.wh.jpatest.domain;

import org.hibernate.annotations.Type;
import ru.nic.wh.jpatest.repository.Inet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "farmip")
@SequenceGenerator(name = "farmip_gen", sequenceName = "farmip_id_seq")
public class FarmIP {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "farmip_gen")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "farmip_type_id", foreignKey = @ForeignKey(name = "farmip_farmip_type_id_fkey"))
	private FarmIPType farmipType;

	@Type(type = "ru.nic.wh.jpatest.repository.InetType")
	@Column(name = "ip")
	private Inet ip;

	protected FarmIP() {
	}

	public FarmIP(String ip, FarmIPType farmipType) {
		this.ip = new Inet(ip);
		this.farmipType = farmipType;
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
}
