package ru.nic.wh.jpatest.repository.usertype;

import java.io.Serializable;

public class Inet implements Serializable {

	private final String address;

	public Inet(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return address;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Inet inet1 = (Inet) o;

		return address != null ? address.equals(inet1.address) : inet1.address == null;
	}

	@Override
	public int hashCode() {
		return address != null ? address.hashCode() : 0;
	}
}
