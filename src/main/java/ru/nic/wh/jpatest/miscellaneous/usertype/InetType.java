package ru.nic.wh.jpatest.miscellaneous.usertype;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class InetType implements UserType {
	@Override
	public int[] sqlTypes() {
		return new int[]{Types.OTHER};
	}

	@Override
	public Class returnedClass() {
		return Inet.class;
	}

	@Override
	public boolean equals(Object o, Object o1) throws HibernateException {
		return o.equals(o1);
	}

	@Override
	public int hashCode(Object o) throws HibernateException {
		return o.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet resultSet, String[] strings, SessionImplementor sessionImplementor, Object o)
			throws HibernateException, SQLException {
		String address = resultSet.getString(strings[0]);
		if (address != null) {
			return new Inet(address);
		}
		return null;
	}

	@Override
	public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SessionImplementor sessionImplementor)
			throws HibernateException, SQLException {
		if (o == null) {
			preparedStatement.setNull(i, Types.OTHER);
		} else {
			PGobject pGobject = new PGobject();
			pGobject.setValue(((Inet) o).getAddress());
			pGobject.setType("inet");
			preparedStatement.setObject(i, pGobject);
		}
	}

	@Override
	public Object deepCopy(Object o) throws HibernateException {
		return o;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Object o) throws HibernateException {
		return (Serializable) o;
	}

	@Override
	public Object assemble(Serializable serializable, Object o) throws HibernateException {
		return serializable;
	}

	@Override
	public Object replace(Object o, Object o1, Object o2) throws HibernateException {
		return o;
	}
}
