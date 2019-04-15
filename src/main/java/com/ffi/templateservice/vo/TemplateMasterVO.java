package com.ffi.templateservice.vo;

import java.sql.Date;
import java.util.UUID;

public class TemplateMasterVO {

	private UUID id;
	private String templateName;
	private String templateVersion;
	private String templateType;
	private int defaultNoOfYear;
	private int maxNoOfYear;
	private int minNoOfYear;
	private UUID createdBy;
	private UUID approvedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private boolean isExpired;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateVersion() {
		return templateVersion;
	}

	public void setTemplateVersion(String templateVersion) {
		this.templateVersion = templateVersion;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public int getDefaultNoOfYear() {
		return defaultNoOfYear;
	}

	public void setDefaultNoOfYear(int defaultNoOfYear) {
		this.defaultNoOfYear = defaultNoOfYear;
	}

	public int getMaxNoOfYear() {
		return maxNoOfYear;
	}

	public void setMaxNoOfYear(int maxNoOfYear) {
		this.maxNoOfYear = maxNoOfYear;
	}

	public int getMinNoOfYear() {
		return minNoOfYear;
	}

	public void setMinNoOfYear(int minNoOfYear) {
		this.minNoOfYear = minNoOfYear;
	}

	public UUID getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UUID createdBy) {
		this.createdBy = createdBy;
	}

	public UUID getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(UUID approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

	@Override
	public String toString() {
		return "TemplateMasterVO [id=" + id + ", templateName=" + templateName + ", templateVersion=" + templateVersion
				+ ", templateType=" + templateType + ", defaultNoOfYear=" + defaultNoOfYear + ", maxNoOfYear="
				+ maxNoOfYear + ", minNoOfYear=" + minNoOfYear + ", createdBy=" + createdBy + ", approvedBy="
				+ approvedBy + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate
				+ ", isExpired=" + isExpired + "]";
	}

}
