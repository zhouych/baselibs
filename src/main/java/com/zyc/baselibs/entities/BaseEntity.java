package com.zyc.baselibs.entities;

import java.util.Date;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.annotation.EnumMapping;

public abstract class BaseEntity {
	
	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(pk = true)
	private String id;
	
	@FieldRule(required = true)
	@EnumMapping(enumClazz = DataStatus.class)
	private String datastatus;
	
	@FieldRule(externalUneditable = true)
	private Date createdat;
	
	@FieldRule(externalUneditable = true)
	private Date updatedat;

	@FieldRule(externalUneditable = true)
	@DatabaseColumn(version = true)
	private Integer version = 0;
	
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
		this.setDatastatus(DataStatus.ENABLED.toString());
		this.setCreatedat(new Date());
		this.setUpdatedat(this.getCreatedat());
		this.setVersion(0);
	}
	
	public void update() {
		this.setUpdatedat(new Date());
		this.setVersion(this.getVersion() + 1);
	}
}
