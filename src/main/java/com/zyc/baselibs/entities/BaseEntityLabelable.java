package com.zyc.baselibs.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface BaseEntityLabelable {
	
	String getDatastatuslabel();
	
	String getCreatedatlabel();
	
	String getUpdatedatlabel();
	
}
