package com.learning.banking.utils;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * CustomNamingStrategy
 *
 * @author bryan
 * @date Mar 6, 2022-7:51:13 PM
 */
public class CustomNamingStrategy extends PhysicalNamingStrategyStandardImpl {
	private static final long serialVersionUID = 1L;

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		String newTableName = name.getText().concat("_tbl");
		return Identifier.toIdentifier(newTableName);
	}
}
