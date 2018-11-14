package com.zyc.baselibs.entities;

import java.sql.JDBCType;
import java.util.Date;
import java.util.UUID;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.EnumMapping;
import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.data.DataStatus;

public abstract class BaseEntity implements BaseEntityLabelable {
	
	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(pk = true, jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String id;
	
	@FieldRule(required = true)
	@EnumMapping(enumClazz = DataStatus.class)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 64)
	private String datastatus;
	
	@FieldRule(externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.TIMESTAMP)
	private Date createdat;
	
	@FieldRule(externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.TIMESTAMP)
	private Date updatedat;

	@FieldRule(externalUneditable = true)
	@DatabaseColumn(version = true, jdbcType = JDBCType.INTEGER)
	private Integer version = null;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getDatastatus() {
		return datastatus;
	}

	public void setDatastatus(String datastatus) {
		this.datastatus = datastatus;
	}

	public Date getCreatedat() {
		return createdat;
	}

	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}

	public Date getUpdatedat() {
		return updatedat;
	}

	public void setUpdatedat(Date updatedat) {
		this.updatedat = updatedat;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public BaseEntity clean() {
		this.id = null;
		this.createdat = null;
		this.updatedat = null;
		this.version = null;
		return this;
	}
	
	public void init() {
		this.setDatastatus(DataStatus.ENABLED.getValue());
		this.setCreatedat(new Date());
		this.setUpdatedat(this.getCreatedat());
		this.setVersion(0);
	}
	
	public void update() {
		this.setUpdatedat(new Date());
		this.setVersion(this.getVersion() + 1);
	}
	
	/**
	 * 当id没有的话就创建一个id
	 */
	public void createIdWhenNot() {
		if(StringUtils.isBlank(this.getId())) {
			this.setId(UUID.randomUUID().toString());
		}
	}
	
	public String getDatastatuslabel() {
		return this.getDatastatus() == null ? null : Enum.valueOf(DataStatus.class, this.getDatastatus().toUpperCase()).getText();
	}
	
	public String getCreatedatlabel() {
		return StringUtils.fromDate(this.getCreatedat());
	}
	
	public String getUpdatedatlabel() {
		return StringUtils.fromDate(this.getUpdatedat());
	}
}
