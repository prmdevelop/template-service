package com.ffi.templateservice.vo;

import java.sql.Date;
import java.util.UUID;

public class TemplateMasterVO {
	
	public UUID id;
	public String templateName;
	public String templateVersion;
	public String templateType;
	public int defaultNoOfYear;
	public int maxNoOfYear;
	public int minNoOfYear;
	public UUID createdBy;
	public UUID approvedBy;
	public Date createdDate;
	public Date lastModifiedDate;
	public boolean isExpired;

	@Override
	public String toString() {
		return "TemplateMasterVO [id=" + id + ", templateName=" + templateName + ", templateVersion=" + templateVersion
				+ ", templateType=" + templateType + ", defaultNoOfYear=" + defaultNoOfYear + ", maxNoOfYear="
				+ maxNoOfYear + ", minNoOfYear=" + minNoOfYear + ", createdBy=" + createdBy + ", approvedBy="
				+ approvedBy + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate
				+ ", isExpired=" + isExpired + "]";
	}

}
