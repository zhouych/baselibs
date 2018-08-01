package com.zyc.baselibs.entities;

import java.util.Date;

import com.zyc.baselibs.annotation.EntityField;
import com.zyc.baselibs.annotation.EnumMapping;

public abstract class BaseEntity {
	
	@EntityField(required = true)
	@EnumMapping(enumClazz = DataStatus.class)
	private String datastatus;
	
	@EntityField(uneditable = true)
	private Date createdat;
	
	@EntityField(uneditable = true)
	private Date updatedat;
	
	@EntityField(uneditable = true)
	private int version = 0;
	
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

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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
