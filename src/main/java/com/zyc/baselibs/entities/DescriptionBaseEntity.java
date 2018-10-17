package com.zyc.baselibs.entities;

import java.sql.JDBCType;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.FieldRule;

public abstract class DescriptionBaseEntity extends BaseEntity {

	@FieldRule(required = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 512)
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public DescriptionBaseEntity clean() {
		super.clean();
		this.description = null;
		return this;
	}
}
